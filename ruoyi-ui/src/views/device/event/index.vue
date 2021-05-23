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
      <el-form-item label="告警类型" prop="warningLevel">
        <el-select v-model="queryParams.warningLevel" placeholder="请选择告警类型" clearable size="small">
          <el-option
            v-for="dict in warningLevelOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="告警日期">
        <el-date-picker
          v-model="queryParams.updateTimeBegin"
          clearable
          size="small"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择起始日期"
        /> -
        <el-date-picker
          v-model="queryParams.updateTimeEnd"
          clearable
          size="small"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择结束日期"
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
      <!--          v-hasPermi="['device:event:add']"-->
      <!--        >新增-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="success"-->
      <!--          plain-->
      <!--          icon="el-icon-edit"-->
      <!--          size="mini"-->
      <!--          :disabled="single"-->
      <!--          @click="handleUpdate"-->
      <!--          v-hasPermi="['device:event:edit']"-->
      <!--        >修改-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['device:event:remove']"
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除
        </el-button>
      </el-col>
      <right-toolbar :show-search.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="eventList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" width="40" />
      <el-table-column label="设备名称" align="center" prop="deviceInfo.deviceName" />
      <el-table-column label="告警类型" align="center" prop="warningLevel" :formatter="warningLevelFormat" />
      <el-table-column label="告警内容" align="center" prop="warningContent" />
      <el-table-column label="告警时间" align="center" prop="warningTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.warningTime, '{y}-{m}-{d} {hh}:{mi}:{ss}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!--                <el-button-->
          <!--                  size="mini"-->
          <!--                  type="text"-->
          <!--                  icon="el-icon-edit"-->
          <!--                  @click="handleUpdate(scope.row)"-->
          <!--                  v-hasPermi="['device:event:edit']"-->
          <!--                >修改-->
          <!--                </el-button>-->
          <el-button
            v-hasPermi="['device:event:remove']"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改设备事件表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-row>
        <el-col :span="21">
          <el-form ref="form" :model="form" :rules="rules" label-width="150px">
            <el-form-item label="设备名称" prop="deviceId">
              <el-input v-model="form.deviceId" placeholder="请输入设备名称" />
            </el-form-item>
            <el-form-item label="告警类型" prop="warningLevel">
              <el-select v-model="form.warningLevel" placeholder="请选择告警类型">
                <el-option
                  v-for="dict in warningLevelOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="告警内容">
              <editor v-model="form.warningContent" :min-height="192" />
            </el-form-item>
            <el-form-item label="告警时间" prop="warningTime">
              <el-date-picker
                v-model="form.warningTime"
                clearable
                size="small"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择告警时间"
              />
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
import { listEvent, getEvent, delEvent, addEvent, updateEvent, exportEvent } from '@/api/device/event'
import Editor from '@/components/Editor'

export default {
  name: 'Event',
  components: {
    Editor
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
      // 设备事件表表格数据
      eventList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 告警类型字典
      warningLevelOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deviceId: null,
        warningLevel: null,
        warningContent: null,
        warningTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    }
  },
  created() {
    this.getList()
    this.getDicts('device_event_warning_level').then(response => {
      this.warningLevelOptions = response.data
    })
  },
  methods: {
    /** 查询设备事件表列表 */
    getList() {
      this.loading = true
      listEvent(this.queryParams).then(response => {
        this.eventList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 告警类型字典翻译
    warningLevelFormat(row, column) {
      return this.selectDictLabel(this.warningLevelOptions, row.warningLevel)
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
        warningLevel: null,
        warningContent: null,
        warningTime: null
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
      this.queryParams.updateTimeBegin = undefined
      this.queryParams.updateTimeEnd = undefined
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
      this.title = '添加设备事件表'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getEvent(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改设备事件表'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateEvent(this.form).then(response => {
              this.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addEvent(this.form).then(response => {
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
      this.$confirm('是否确认删除设备事件表编号为"' + ids + '"的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delEvent(ids)
      }).then(() => {
        this.getList()
        this.msgSuccess('删除成功')
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams
      this.$confirm('是否确认导出所有设备事件表数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return exportEvent(queryParams)
      }).then(response => {
        this.download(response.msg)
      })
    }
  }
}
</script>
