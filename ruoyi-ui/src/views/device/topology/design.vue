<template>
  <div>
    <div id="app">       <!--绑定按下事件-->
      <el-button type="success" @click="save">保存图形</el-button>
    </div>
    <div style="width: 100%; display: flex; justify-content: space-between">
      <div id="myPaletteDiv" style="width: 90px; margin-right: 2px; background-color: #282c34;" />
      <div id="myDiagramDiv" style="flex-grow: 1; height: 870px; background-color: #282c34;" />
    </div>
    <el-dialog
      title="选择设备"
      :visible.sync="dialogVisible"
      width="70%"
    >
      <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
        <el-form-item label="设备名称" prop="deviceName">
          <el-input
            v-model="queryParams.deviceName"
            placeholder="请输入设备名称"
            clearable
            size="small"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table
        ref="singleTable"
        v-loading="loading"
        :data="infoList"
        size="mini"
        highlight-current-row
        @current-change="handleCurrentChange"
      >
        <el-table-column label="归属部门" align="center" prop="dept.deptName" />
        <el-table-column label="设备名称" align="center" prop="deviceName">
          <template slot-scope="scope">
            <el-button type="text" @click="handleView(scope.row)">{{ scope.row.deviceName }}</el-button>
          </template>
        </el-table-column>
        <el-table-column label="设备类型" align="center" prop="deviceType" :formatter="deviceTypeFormat" />
        <el-table-column label="设备型号" align="center" prop="deviceModel" :formatter="deviceModelFormat" />
        <el-table-column label="设备级别" align="center" prop="deviceLevel" :formatter="deviceLevelFormat" />
        <el-table-column label="设备IP1" align="center" prop="deviceIp1" />
        <el-table-column label="设备IP2" align="center" prop="deviceIp2" />
        <el-table-column label="安装日期" align="center" prop="installDate" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.installDate, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="安装位置" align="center" prop="location" />
      </el-table>

      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="bindingNodeDeviceInfo">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import go from 'gojs'
import { getTopology } from '@/api/device/topology'
import { updateTopology } from '@/api/device/topology'
import { listInfo } from '@/api/device/info'

export default {
  name: 'TopologyDesign',
  data() {
    return {
      myDiagram: undefined,
      topologyId: undefined,
      myPalette: undefined,
      topology: {},
      positionX: 0,
      positionY: 0,
      // 选中的节点数据
      selectedNode: {},
      // 选中的设备数据
      selectedDevice: {},
      // 对话框显示状态
      dialogVisible: false,
      deviceTypeOptions: [],
      // 设备型号字典
      deviceModelOptions: [],
      deviceLevelOptions: [],
      total: 0,
      // deviceInfo表格数据
      infoList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deviceName: null,
        deviceType: null,
        deviceModel: null,
        deptId: null
      },
      // 遮罩层
      loading: true
    }
  },
  mounted() {
    const that = this
    const GO = go.GraphObject.make
    this.myDiagram =
      GO(go.Diagram, 'myDiagramDiv',
        {
          'undoManager.isEnabled': true,
          'hoverDelay': 10
        })

    // 定义节点的连接线的端点
    // The "name" is used as the GraphObject.portId,
    // the "align" is used to determine where to position the port relative to the body of the node,
    // the "spot" is used to control how links connect with the port and whether the port
    // stretches along the side of the node,
    // and the boolean "output" and "input" arguments control whether the user can draw links from or to the port.
    function makePort(name, align, spot, output, input) {
      var horizontal = align.equals(go.Spot.Top) || align.equals(go.Spot.Bottom)
      return GO(go.Shape,
        {
          fill: 'transparent', // changed to a color in the mouseEnter event handler
          strokeWidth: 0, // no stroke
          width: horizontal ? 50 : 5, // if not stretching horizontally, just 8 wide
          height: !horizontal ? 50 : 5, // if not stretching vertically, just 8 tall
          alignment: align, // align the port on the main Shape
          stretch: (horizontal ? go.GraphObject.Horizontal : go.GraphObject.Vertical),
          portId: name, // declare this object to be a "port"
          fromSpot: spot, // declare where links may connect at this port
          fromLinkable: output, // declare whether the user may draw links from here
          toSpot: spot, // declare where links may connect at this port
          toLinkable: input, // declare whether the user may draw links to here
          cursor: 'pointer', // show a different cursor to indicate potential link point
          mouseEnter: function(e, port) { // the PORT argument will be this Shape
            if (!e.diagram.isReadOnly) port.fill = 'rgba(255,0,255,0.5)'
          },
          mouseLeave: function(e, port) {
            port.fill = 'transparent'
          }
        })
    }

    // 双击节点事件
    this.myDiagram.addDiagramListener('ObjectDoubleClicked', function(e) {
      that.clickNode(e)
    })

    // 交换机
    this.myDiagram.nodeTemplateMap.add('switch',
      GO(go.Node, 'Auto', that.nodeStyle(),
        GO(go.Shape, 'RoundedRectangle', { 'fill': '#282c34', strokeWidth: 0 }),
        GO(go.Panel, 'Table',
          { defaultAlignment: go.Spot.Center },
          GO(go.Picture, {
            row: 0,
            column: 0,
            columnSpan: 2,
            width: 32,
            height: 32,
            source: '/img/devType/1.jpeg',
            toSpot: go.Spot.Top,
            toLinkable: true,
            fromLinkable: true
          }, new go.Binding('source', 'imgUrl')),
          GO(go.TextBlock, {
            row: 1, column: 0, columnSpan: 2, stroke: 'white', toSpot: go.Spot.Bottom,
            toLinkable: true, fromLinkable: true
          },
          new go.Binding('text', 'deviceName'), { margin: 5 })
        ),
        // four named ports, one on each side:
        makePort('T', go.Spot.Top, go.Spot.TopSide, true, true),
        makePort('L', go.Spot.Left, go.Spot.LeftSide, true, true),
        makePort('R', go.Spot.Right, go.Spot.RightSide, true, true),
        makePort('B', go.Spot.Bottom, go.Spot.BottomSide, true, true)
      )
    )

    // 路由器
    this.myDiagram.nodeTemplateMap.add('router',
      GO(go.Node, 'Auto', that.nodeStyle(),
        GO(go.Shape, 'RoundedRectangle', { 'fill': '#282c34', strokeWidth: 0 }),
        GO(go.Panel, 'Table',
          { defaultAlignment: go.Spot.Center },
          GO(go.Picture, {
            row: 0,
            column: 0,
            columnSpan: 2,
            width: 32,
            height: 32,
            source: '/img/devType/2.jpeg',
            toSpot: go.Spot.Top,
            toLinkable: true,
            fromLinkable: true
          }, new go.Binding('source', 'imgUrl')),
          GO(go.TextBlock, {
            row: 1, column: 0, columnSpan: 2, stroke: 'white', toSpot: go.Spot.Bottom,
            toLinkable: true, fromLinkable: true
          },
          new go.Binding('text', 'deviceName'), { margin: 5 })
        ),
        // four named ports, one on each side:
        makePort('T', go.Spot.Top, go.Spot.TopSide, true, true),
        makePort('L', go.Spot.Left, go.Spot.LeftSide, true, true),
        makePort('R', go.Spot.Right, go.Spot.RightSide, true, true),
        makePort('B', go.Spot.Bottom, go.Spot.BottomSide, true, true)
      )
    )

    // 电力分站
    this.myDiagram.nodeTemplateMap.add('powerSubstation',
      GO(go.Node, 'Auto', that.nodeStyle(),
        GO(go.Shape, 'RoundedRectangle', { 'fill': '#282c34', strokeWidth: 0 }),
        GO(go.Panel, 'Table',
          { defaultAlignment: go.Spot.Center },
          GO(go.Picture, {
            row: 0,
            column: 0,
            columnSpan: 2,
            width: 32,
            height: 32,
            source: '/img/devType/3.jpeg',
            toSpot: go.Spot.Top,
            toLinkable: true,
            fromLinkable: true
          }, new go.Binding('source', 'imgUrl')),
          GO(go.TextBlock, {
            row: 1, column: 0, columnSpan: 2, stroke: 'white', toSpot: go.Spot.Bottom,
            toLinkable: true, fromLinkable: true
          },
          new go.Binding('text', 'deviceName'), { margin: 5 })
        ),
        // four named ports, one on each side:
        makePort('T', go.Spot.Top, go.Spot.TopSide, true, true),
        makePort('L', go.Spot.Left, go.Spot.LeftSide, true, true),
        makePort('R', go.Spot.Right, go.Spot.RightSide, true, true),
        makePort('B', go.Spot.Bottom, go.Spot.BottomSide, true, true)
      )
    )

    // 无线基站
    this.myDiagram.nodeTemplateMap.add('wifi',
      GO(go.Node, 'Auto', that.nodeStyle(),
        GO(go.Shape, 'RoundedRectangle', { 'fill': '#282c34', strokeWidth: 0 }),
        GO(go.Panel, 'Table',
          { defaultAlignment: go.Spot.Center },
          GO(go.Picture, {
            row: 0,
            column: 0,
            columnSpan: 2,
            width: 32,
            height: 32,
            source: '/img/devType/4.jpeg',
            toSpot: go.Spot.Top,
            toLinkable: true,
            fromLinkable: true
          }, new go.Binding('source', 'imgUrl')),
          GO(go.TextBlock, {
            row: 1, column: 0, columnSpan: 2, stroke: 'white', toSpot: go.Spot.Bottom,
            toLinkable: true, fromLinkable: true
          },
          new go.Binding('text', 'deviceName'), { margin: 5 })
        ),
        // four named ports, one on each side:
        makePort('T', go.Spot.Top, go.Spot.TopSide, true, true),
        makePort('L', go.Spot.Left, go.Spot.LeftSide, true, true),
        makePort('R', go.Spot.Right, go.Spot.RightSide, true, true),
        makePort('B', go.Spot.Bottom, go.Spot.BottomSide, true, true)
      )
    )

    // 定义节点连接线样式
    this.myDiagram.linkTemplate =
      GO(go.Link, // the whole link panel
        {
          routing: go.Link.AvoidsNodes,
          curve: go.Link.JumpOver,
          corner: 5, toShortLength: 4,
          relinkableFrom: true,
          relinkableTo: true,
          reshapable: true,
          resegmentable: true,
          // mouse-overs subtly highlight links:
          mouseEnter: function(e, link) {
            link.findObject('HIGHLIGHT').stroke = 'rgba(30,144,255,0.2)'
          },
          mouseLeave: function(e, link) {
            link.findObject('HIGHLIGHT').stroke = 'transparent'
          },
          selectionAdorned: false
        },
        new go.Binding('points').makeTwoWay(),
        GO(go.Shape, // the highlight shape, normally transparent
          { isPanelMain: true, strokeWidth: 8, stroke: 'transparent', name: 'HIGHLIGHT' }),
        GO(go.Shape, // the link path shape
          { isPanelMain: true, stroke: 'gray', strokeWidth: 2 },
          new go.Binding('stroke', 'isSelected', function(sel) {
            return sel ? 'dodgerblue' : 'gray'
          }).ofObject()),
        GO(go.Panel, 'Auto', // the link label, normally not visible
          { visible: false, name: 'LABEL', segmentIndex: 2, segmentFraction: 0.5 },
          new go.Binding('visible', 'visible').makeTwoWay(),
          GO(go.Shape, 'RoundedRectangle', // the label shape
            { fill: '#F8F8F8', strokeWidth: 0 }),
          GO(go.TextBlock, 'Yes', // the label
            {
              textAlign: 'center',
              font: '10pt helvetica, arial, sans-serif',
              stroke: '#333333',
              editable: true
            },
            new go.Binding('text').makeTwoWay())
        )
      )

    // initialize the Palette that is on the left side of the page
    this.myPalette =
      GO(go.Palette, 'myPaletteDiv', // must name or refer to the DIV HTML element
        {
          // Instead of the default animation, use a custom fade-down
          'animationManager.initialAnimationStyle': go.AnimationManager.None,
          // 'InitialAnimationStarting': animateFadeDown, // Instead, animate with this function

          nodeTemplateMap: this.myDiagram.nodeTemplateMap, // share the templates used by this.myDiagram
          model: new go.GraphLinksModel([ // specify the contents of the Palette
            {
              category: 'switch',
              deviceName: '交换机',
              deviceModel: '',
              deviceType: '',
              ip: '',
              imgUrl: '/img/devType/1.jpeg'
            },
            {
              category: 'router',
              deviceName: '路由器',
              deviceModel: '',
              deviceType: '',
              ip: '',
              imgUrl: '/img/devType/2.jpeg'
            },
            {
              category: 'powerSubstation',
              deviceName: '电力分站',
              deviceModel: '',
              deviceType: '',
              ip: '',
              imgUrl: '/img/devType/3.jpeg'
            },
            {
              category: 'wifi',
              deviceName: '无线基站',
              deviceModel: '',
              deviceType: '',
              ip: '',
              imgUrl: '/img/devType/4.jpeg'
            }
          ])
        })
  },
  created() {
    this.topologyId = this.$route.params && this.$route.params.id
    this.load() // load an initial diagram from some JSON text
    this.getDicts('device_info_type').then(response => {
      this.deviceTypeOptions = response.data
    })
    this.getDicts('device_info_model').then(response => {
      this.deviceModelOptions = response.data
    })
    this.getDicts('device_info_level').then(response => {
      this.deviceLevelOptions = response.data
    })
    this.getList()
  },
  methods: {
    // 载入拓扑数据
    load() {
      getTopology(this.topologyId).then(response => {
        this.topology = response.data
        this.myDiagram.model = go.Model.fromJson(response.data.imageData)
      })
    },

    // 保存拓扑图
    save() {
      this.topology.imageData = this.myDiagram.model.toJson()
      updateTopology(this.topology).then(() => {
        this.$message({
          message: '拓扑图保存成功！',
          type: 'success'
        })
      })
    },

    // 通用的节点样式，增加定位属性
    nodeStyle() {
      return [
        new go.Binding('location', 'loc', go.Point.parse).makeTwoWay(go.Point.stringify),
        {
          locationSpot: go.Spot.Center
        }
      ]
    },

    // 点击节点
    clickNode(e) {
      var part = e.subject.part
      console.log('点击了' + part.data.key)
      this.selectedNode = part.data
      this.dialogVisible = true
      this.selectedDevice = undefined
      this.$refs.singleTable.setCurrentRow(null)
    },
    handleCurrentChange(val) {
      console.log(val)
      this.$message({
        message: '选中设备：' + val.deviceName,
        type: 'success'
      })
      this.selectedDevice = val
    },
    /** 查询deviceInfo列表 */
    getList() {
      this.loading = true
      listInfo(this.queryParams).then(response => {
        this.infoList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 设备类型字典翻译
    deviceTypeFormat(row, column) {
      return this.selectDictLabel(this.deviceTypeOptions, row.deviceType)
    },
    // 设备型号字典翻译
    deviceModelFormat(row, column) {
      return this.selectDictLabel(this.deviceModelOptions, row.deviceModel)
    },
    // 设备型号字典翻译
    deviceLevelFormat(row, column) {
      return this.selectDictLabel(this.deviceLevelOptions, row.deviceLevel)
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 将设备绑定到节点
    bindingNodeDeviceInfo() {
      if (this.selectedDevice) {
        this.$message({
          message: '设备绑定成功！',
          type: 'success'
        })
        var model = this.myDiagram.model
        var arr = model.nodeDataArray

        // 判断是否存在重复添加
        for (var i = 0; i < arr.length; i++) {
          if (arr[i].key === this.selectedDevice.id) {
            return this.$message.error('不能重复绑定该设备！')
          }
        }

        // 循环节点匹配，更新设备状态
        for (i = 0; i < arr.length; i++) {
          var nodeData = arr[i]
          if (nodeData.key === this.selectedNode.key) {
            nodeData.key = this.selectedDevice.id
            nodeData.deviceName = this.selectedDevice.deviceName
            nodeData.deviceName = this.selectedDevice.deviceName
            nodeData.deviceType = this.selectedDevice.deviceType
            nodeData.deviceModel = this.selectedDevice.deviceModel
            nodeData.deviceLevel = this.selectedDevice.deviceLevel
            nodeData.ip = this.selectedDevice.deviceIp1
            model.updateTargetBindings(nodeData)
            break
          }
        }
        this.myDiagram.model = go.Model.fromJson(this.myDiagram.model.toJson())

        this.dialogVisible = false
      } else {
        this.$message.error('请选择设备！')
      }
    }
  }
}
</script>

<style scoped>
  #app{
    position: relative;     /*定位*/
    top: 10px;
    left: 100px;
    width: 0px;
    height: 0px;
    background: #666;       /*设置一下背景*/
    z-index: 99;
  }
</style>
