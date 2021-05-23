<template>
  <el-dialog
    title="查看设备信息"
    :visible.sync="dialogVisible"
    width="80%"
  >
    <span>
      <el-row :gutter="24">
        <el-col :span="12">
          <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>设备信息</span>
        </div>
        <div>
          <el-row>
            <el-form ref="form" label-width="150px" size="mini">
              <el-col :span="10">
                <el-form-item label="设备名称">
                  {{ deviceInfo.deviceName }}
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="归属部门">
                  <div v-if="deviceInfo.dept">{{ deviceInfo.dept.deptName }}</div>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="设备类型">
                  <div v-if="deviceInfo">{{ selectDictLabel(deviceTypeOptions,deviceInfo.deviceType) }}</div>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="设备型号">
                  <div v-if="deviceInfo">{{ selectDictLabel(deviceModelOptions,deviceInfo.deviceModel) }}</div>
                </el-form-item>
              </el-col>
              <el-col :span="20">
                <el-form-item label="制造商">
                  {{ deviceInfo.manufacturer }}
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="设备IP1">
                  {{ deviceInfo.deviceIp1 }}
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="设备IP2">
                  {{ deviceInfo.deviceIp2 }}
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="设备IP3">
                  {{ deviceInfo.deviceIp3 }}
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="设备IP4">
                  {{ deviceInfo.deviceIp4 }}
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="设备IP5">
                  {{ deviceInfo.deviceIp5 }}
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="设备IP6">
                  {{ deviceInfo.deviceIp6 }}
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="设备IP7">
                  {{ deviceInfo.deviceIp7 }}
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="设备IP8">
                  {{ deviceInfo.deviceIp8 }}
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="安装日期">
                  {{ deviceInfo.installDate }}
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="安装位置">
                  {{ deviceInfo.location }}
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="安装备注">
                  {{ deviceInfo.note }}
                </el-form-item>
              </el-col>
            </el-form>
          </el-row>
        </div>
      </el-card>
        </el-col>
        <el-col :span="12">
          <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>状态信息</span>
        </div>
        <div>
          <el-row>
            <el-form ref="form" label-width="150px">
              <el-col :span="10">
                <el-form-item label="实时状态">
                  <el-tag v-if="deviceStatus.deviceStatus === 1" type="success" effect="dark"><div v-if="deviceStatus">{{ selectDictLabel(deviceStatusOptions,deviceStatus.deviceStatus) }}</div></el-tag>
                  <el-tag v-if="deviceStatus.deviceStatus === 2" type="warning" effect="dark"><div v-if="deviceStatus">{{ selectDictLabel(deviceStatusOptions,deviceStatus.deviceStatus) }}</div></el-tag>
                  <el-tag v-if="deviceStatus.deviceStatus === 3" type="danger" effect="dark"><div v-if="deviceStatus">{{ selectDictLabel(deviceStatusOptions,deviceStatus.deviceStatus) }}</div></el-tag>
                  <el-tag v-if="deviceStatus.deviceStatus === 4" type="info" effect="dark"><div v-if="deviceStatus">{{ selectDictLabel(deviceStatusOptions,deviceStatus.deviceStatus) }}</div></el-tag>
                  <el-tag v-if="deviceStatus.deviceStatus === 5" type="info" effect="dark"><div v-if="deviceStatus">{{ selectDictLabel(deviceStatusOptions,deviceStatus.deviceStatus) }}</div></el-tag>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="丢包率">
                  {{ deviceStatus.pingLoss }}
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="更新时间">
                  {{ deviceStatus.updateTime }}
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="Ping平均延时">
                  {{ deviceStatus.pingAvg }}
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="最大延时">
                  {{ deviceStatus.pingMax }}
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="最小延时">
                  {{ deviceStatus.pingMin }}
                </el-form-item>
              </el-col>
            </el-form>
          </el-row>
        </div>
      </el-card>
        </el-col>
      </el-row>
      <el-table :data="eventList" height="250" style="padding-top: 20px">
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
      </el-table>
    </span>
  </el-dialog>
</template>
<script>
import { getInfo } from '@/api/device/info'
import { getStatus } from '@/api/device/status'
import { listEventByDeviceId } from '@/api/device/event'
export default {
  name: 'DeviceInfoView',
  data() {
    return {
      deviceInfo: {},
      deviceStatus: {},
      dialogVisible: false,
      // 设备类型字典
      deviceTypeOptions: [],
      // 设备型号字典
      deviceModelOptions: [],
      // 设备状态字典表
      deviceStatusOptions: [],
      // 事件表
      eventList: []
    }
  },
  created() {
    this.getDicts('device_info_type').then(response => {
      this.deviceTypeOptions = response.data
    })
    this.getDicts('device_info_model').then(response => {
      this.deviceModelOptions = response.data
    })
    this.getDicts('device_status').then(response => {
      this.deviceStatusOptions = response.data
    })
    this.getDicts('device_event_warning_level').then(response => {
      this.warningLevelOptions = response.data
    })
  },
  methods: {
    show(row) {
      getInfo(row.id).then(response => {
        this.deviceInfo = response.data
      })
      getStatus(row.id).then(response => {
        this.deviceStatus = response.data
      })
      this.dialogVisible = true
      listEventByDeviceId(row.id).then(response => {
        this.eventList = response.data
      })
    },
    // 告警类型字典翻译
    warningLevelFormat(row, column) {
      return this.selectDictLabel(this.warningLevelOptions, row.warningLevel)
    }
  }
}
</script>
<style scoped>
</style>
