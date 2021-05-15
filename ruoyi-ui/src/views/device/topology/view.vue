<template>
  <div>
    <div style="width: 100%; display: flex; justify-content: space-between">
      <div id="myDiagramDiv" style="flex-grow: 1; height: 870px; background-color: #282c34;" />
    </div>
    <device-info-view ref="viewPage" />
  </div>
</template>

<script>
import go from 'gojs'
import { getTopology } from '@/api/device/topology'
import DeviceInfoView from '@/views/device/info/view'
import { getInfo } from '@/api/device/info'
import { getDeviceStatus } from '@/api/device/status'

export default {
  name: 'TopologyView',
  components: { DeviceInfoView },
  data() {
    return {
      myDiagram: undefined,
      topologyId: undefined,
      myPalette: undefined,
      topology: {},
      positionX: 0,
      positionY: 0,
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
      loading: true,
      // 定时器
      timer: undefined
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
      GO(go.Node, 'Auto', this.nodeStyle(),
        GO(go.Shape, 'RoundedRectangle', { 'fill': '#282c34', strokeWidth: 0 }),
        GO(go.Panel, 'Table',
          { defaultAlignment: go.Spot.Center },
          GO(go.Picture, { row: 0, column: 0, columnSpan: 2, width: 32, height: 32, source: '/img/devType/1.jpeg', toSpot: go.Spot.Top,
            toLinkable: true, fromLinkable: true }, new go.Binding('source', 'imgUrl')),
          GO(go.TextBlock, { row: 1, column: 0, stroke: 'white', toSpot: go.Spot.Bottom,
            toLinkable: true, fromLinkable: true },
          new go.Binding('text', 'deviceName'), { margin: 5 }),
          GO(go.Shape, 'Circle', { row: 1, column: 1 }, {
            segmentIndex: 0,
            width: 15,
            height: 15,
            fill: 'lightblue',
            strokeWidth: 0
          }, new go.Binding('fill', 'status'))
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
      GO(go.Node, 'Auto', this.nodeStyle(),
        GO(go.Shape, 'RoundedRectangle', { 'fill': '#282c34', strokeWidth: 0 }),
        GO(go.Panel, 'Table',
          { defaultAlignment: go.Spot.Center },
          GO(go.Picture, { row: 0, column: 0, columnSpan: 2, width: 32, height: 32, source: '/img/devType/2.jpeg', toSpot: go.Spot.Top,
            toLinkable: true, fromLinkable: true }, new go.Binding('source', 'imgUrl')),
          GO(go.TextBlock, { row: 1, column: 0, stroke: 'white', toSpot: go.Spot.Bottom,
            toLinkable: true, fromLinkable: true },
          new go.Binding('text', 'deviceName'), { margin: 5 }),
          GO(go.Shape, 'Circle', { row: 1, column: 1 }, {
            segmentIndex: 0,
            width: 15,
            height: 15,
            fill: 'lightblue',
            strokeWidth: 0
          }, new go.Binding('fill', 'status'))
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
      GO(go.Node, 'Auto', this.nodeStyle(),
        GO(go.Shape, 'RoundedRectangle', { 'fill': '#282c34', strokeWidth: 0 }),
        GO(go.Panel, 'Table',
          { defaultAlignment: go.Spot.Center },
          GO(go.Picture, { row: 0, column: 0, columnSpan: 2, width: 32, height: 32, source: '/img/devType/3.jpeg', toSpot: go.Spot.Top,
            toLinkable: true, fromLinkable: true }, new go.Binding('source', 'imgUrl')),
          GO(go.TextBlock, { row: 1, column: 0, stroke: 'white', toSpot: go.Spot.Bottom,
            toLinkable: true, fromLinkable: true },
          new go.Binding('text', 'deviceName'), { margin: 5 }),
          GO(go.Shape, 'Circle', { row: 1, column: 1 }, {
            segmentIndex: 0,
            width: 15,
            height: 15,
            fill: 'lightblue',
            strokeWidth: 0
          }, new go.Binding('fill', 'status'))
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
      GO(go.Node, 'Auto', this.nodeStyle(),
        GO(go.Shape, 'RoundedRectangle', { 'fill': '#282c34', strokeWidth: 0 }),
        GO(go.Panel, 'Table',
          { defaultAlignment: go.Spot.Center },
          GO(go.Picture, { row: 0, column: 0, columnSpan: 2, width: 32, height: 32, source: '/img/devType/4.jpeg', toSpot: go.Spot.Top,
            toLinkable: true, fromLinkable: true }, new go.Binding('source', 'imgUrl')),
          GO(go.TextBlock, { row: 1, column: 0, stroke: 'white', toSpot: go.Spot.Bottom,
            toLinkable: true, fromLinkable: true },
          new go.Binding('text', 'deviceName'), { margin: 5 }),
          GO(go.Shape, 'Circle', { row: 1, column: 1 }, {
            segmentIndex: 0,
            width: 15,
            height: 15,
            fill: 'lightblue',
            strokeWidth: 0
          }, new go.Binding('fill', 'status'))
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

    this.timer = setInterval(this.getDeviceStatus, 25000)
  },
  beforeDestroy() {
    // 销毁定时任务
    clearInterval(this.timer)
  },
  created() {
    this.topologyId = this.$route.params && this.$route.params.id
    this.getDicts('device_info_type').then(response => {
      this.deviceTypeOptions = response.data
    })
    this.getDicts('device_info_model').then(response => {
      this.deviceModelOptions = response.data
    })
    this.getDicts('device_info_level').then(response => {
      this.deviceLevelOptions = response.data
    })
    // 加载拓扑图形数据
    this.load()
  },
  methods: {
    // 载入拓扑数据
    load() {
      getTopology(this.topologyId).then(response => {
        this.topology = response.data
        this.myDiagram.model = go.Model.fromJson(response.data.imageData)
        this.getDeviceStatus()
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
      getInfo(part.data.key).then(response => {
        this.$refs.viewPage.show(response.data)
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
    getDeviceStatus() {
      console.log('开始更新设备状态')
      var model = this.myDiagram.model
      var arr = model.nodeDataArray
      var deviceIds = ''
      for (var i = 0; i < arr.length; i++) {
        var nodeData = arr[i]
        deviceIds = deviceIds + nodeData.key + ','
      }
      getDeviceStatus({ deviceIds: deviceIds + '0' }).then(response => {
        var model = this.myDiagram.model
        var arr = model.nodeDataArray
        for (var i = 0; i < arr.length; i++) {
          var nodeData = arr[i]
          response.data.forEach(item => {
            if (nodeData.key === item.deviceId) {
              if (item.deviceStatus === 1) {
                nodeData.status = 'green'
              } else if (item.deviceStatus === 2) {
                nodeData.status = 'yellow'
              } else if (item.deviceStatus === 3) {
                nodeData.status = 'red'
              } else {
                nodeData.status = 'grey'
              }
              model.updateTargetBindings(nodeData)
            }
          })
        }
      })

      // $.post('/device/status/deviceStatus', { deviceIds: deviceIds + '0' }, function(response) {
      //   var model = myDiagram.model
      //   var arr = model.nodeDataArray
      //   // 循环节点匹配，更新设备状态
      //   for (var i = 0; i < arr.length; i++) {
      //     var nodeData = arr[i]
      //     $.each(response.data, function(i, item) {
      //       if (nodeData.key == item.deviceId) {
      //         if (item.deviceStatus == '1') {
      //           nodeData.status = 'green'
      //         } else if (item.deviceStatus == '2') {
      //           nodeData.status = 'yellow'
      //         } else if (item.deviceStatus == '3') {
      //           nodeData.status = 'red'
      //         } else {
      //           nodeData.status = 'grey'
      //         }
      //         model.updateTargetBindings(nodeData)
      //       }
      //     })
      //   }
      //
      //   var goodCnt = 0
      //   var commonCnt = 0
      //   var badCnt = 0
      //   var otherCnt = 0
      //   $.each(response.data, function(i, item) {
      //     if (item.deviceStatus == 1) {
      //       goodCnt++
      //     }
      //     if (item.deviceStatus == 2) {
      //       commonCnt++
      //     }
      //     if (item.deviceStatus == 3) {
      //       badCnt++
      //     }
      //     if (item.deviceStatus > 3) {
      //       otherCnt++
      //     }
      //   })
      // })
    }
  }
}
</script>
