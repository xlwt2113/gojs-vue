<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
      <el-form-item label="设备名称" prop="deviceName">
        <el-input
          v-model="queryParams.deviceName"
          placeholder="请输入设备名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="实时状态" prop="deviceStatus">
        <el-select v-model="queryParams.deviceStatus" placeholder="请选择设备实时状态" clearable size="small">
          <el-option
            v-for="dict in deviceStatusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="更新日期">
        <el-date-picker
          v-model="queryParams.updateTimeBegin"
          clearable
          size="small"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择起始更新日期"
        /> -
        <el-date-picker
          v-model="queryParams.updateTimeEnd"
          clearable
          size="small"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择结束更新日期"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="primary"-->
      <!--          plain-->
      <!--          icon="el-icon-plus"-->
      <!--          size="mini"-->
      <!--          @click="handleAdd"-->
      <!--          v-hasPermi="['device:status:add']"-->
      <!--        >新增</el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="success"-->
      <!--          plain-->
      <!--          icon="el-icon-edit"-->
      <!--          size="mini"-->
      <!--          :disabled="single"-->
      <!--          @click="handleUpdate"-->
      <!--          v-hasPermi="['device:status:edit']"-->
      <!--        >修改</el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="danger"-->
      <!--          plain-->
      <!--          icon="el-icon-delete"-->
      <!--          size="mini"-->
      <!--          :disabled="multiple"-->
      <!--          @click="handleDelete"-->
      <!--          v-hasPermi="['device:status:remove']"-->
      <!--        >删除</el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="warning"-->
      <!--          plain-->
      <!--          icon="el-icon-download"-->
      <!--          size="mini"-->
      <!--          @click="handleExport"-->
      <!--          v-hasPermi="['device:status:export']"-->
      <!--        >导出</el-button>-->
      <!--      </el-col>-->
      <right-toolbar :show-search.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="statusList" @selection-change="handleSelectionChange">
      <!--      <el-table-column type="selection" width="55" align="center" />-->
      <!--      <el-table-column label="设备实时状态" align="center" prop="id" />-->
      <el-table-column label="设备名称" align="center" prop="deviceInfo.deviceName" />
      <el-table-column label="设备实时状态" align="center" prop="deviceStatus" :formatter="deviceStatusFormat">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.deviceStatus === 1" type="success" effect="dark">{{ deviceStatusFormat(scope.row) }}</el-tag>
          <el-tag v-if="scope.row.deviceStatus === 2" type="warning" effect="dark">{{ deviceStatusFormat(scope.row) }}</el-tag>
          <el-tag v-if="scope.row.deviceStatus === 3" type="danger" effect="dark">{{ deviceStatusFormat(scope.row) }}</el-tag>
          <el-tag v-if="scope.row.deviceStatus === 4" type="info" effect="dark">{{ deviceStatusFormat(scope.row) }}</el-tag>
          <el-tag v-if="scope.row.deviceStatus === 5" type="info" effect="dark">{{ deviceStatusFormat(scope.row) }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="ping平均时延" align="center" prop="pingAvg" />
      <el-table-column label="最小时延" align="center" prop="pingMin" />
      <el-table-column label="最大时延" align="center" prop="pingMax" />
      <el-table-column label="丢包率" align="center" prop="pingLoss" />
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d} {hh}:{mi}:{ss}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="note" />
      <!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
      <!--        <template slot-scope="scope">-->
      <!--          <el-button-->
      <!--            size="mini"-->
      <!--            type="text"-->
      <!--            icon="el-icon-edit"-->
      <!--            @click="handleUpdate(scope.row)"-->
      <!--            v-hasPermi="['device:status:edit']"-->
      <!--          >修改</el-button>-->
      <!--          <el-button-->
      <!--            size="mini"-->
      <!--            type="text"-->
      <!--            icon="el-icon-delete"-->
      <!--            @click="handleDelete(scope.row)"-->
      <!--            v-hasPermi="['device:status:remove']"-->
      <!--          >删除</el-button>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改设备状态表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-row>
        <el-col :span="21">
          <el-form ref="form" :model="form" :rules="rules" label-width="150px">
            <el-form-item label="设备名称" prop="deviceId">
              <el-input v-model="form.deviceId" placeholder="请输入设备名称" />
            </el-form-item>
            <el-form-item label="设备实时状态">
              <el-radio-group v-model="form.deviceStatus">
                <el-radio
                  v-for="dict in deviceStatusOptions"
                  :key="dict.dictValue"
                  :label="parseInt(dict.dictValue)"
                >{{ dict.dictLabel }}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="ping平均时延" prop="pingAvg">
              <el-input v-model="form.pingAvg" placeholder="请输入ping平均时延" />
            </el-form-item>
            <el-form-item label="最小时延" prop="pingMin">
              <el-input v-model="form.pingMin" placeholder="请输入最小时延" />
            </el-form-item>
            <el-form-item label="最大时延" prop="pingMax">
              <el-input v-model="form.pingMax" placeholder="请输入最大时延" />
            </el-form-item>
            <el-form-item label="丢包率" prop="pingLoss">
              <el-input v-model="form.pingLoss" placeholder="请输入丢包率" />
            </el-form-item>
            <el-form-item label="备注" prop="note">
              <el-input v-model="form.note" placeholder="请输入备注" />
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listStatus, getStatus, delStatus, addStatus, updateStatus, exportStatus } from '@/api/device/status'

export default {
  name: 'Status',
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 设备状态表表格数据
      statusList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 设备实时状态字典
      deviceStatusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deviceId: null,
        deviceStatus: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        deviceId: [
          { required: true, message: '设备名称不能为空', trigger: 'blur' }
        ],
        deviceStatus: [
          { required: true, message: '设备实时状态不能为空', trigger: 'blur' }
        ],
        updateTime: [
          { required: true, message: '状态更新时间不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getDicts('device_status').then(response => {
      this.deviceStatusOptions = response.data
    })
  },
  methods: {
    /** 查询设备状态表列表 */
    getList() {
      this.loading = true
      listStatus(this.queryParams).then(response => {
        this.statusList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 设备实时状态字典翻译
    deviceStatusFormat(row, column) {
      return this.selectDictLabel(this.deviceStatusOptions, row.deviceStatus)
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        deviceId: null,
        deviceStatus: 0,
        pingAvg: null,
        pingMin: null,
        pingMax: null,
        pingLoss: null,
        updateTime: null,
        note: null
      }
      this.resetForm('form')
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加设备状态表'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getStatus(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改设备状态表'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStatus(this.form).then(response => {
              this.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addStatus(this.form).then(response => {
              this.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm('是否确认删除设备状态表编号为"' + ids + '"的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delStatus(ids)
      }).then(() => {
        this.getList()
        this.msgSuccess('删除成功')
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams
      this.$confirm('是否确认导出所有设备状态表数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return exportStatus(queryParams)
      }).then(response => {
        this.download(response.msg)
      })
    }
  }
}
</script>
