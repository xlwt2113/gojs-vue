<template>
  <el-dialog
    title="设备信息详情"
    :visible.sync="dialogVisible"
    width="600">
    <span>
            <el-row>
        <el-col :span="21">
          <el-form ref="form" label-width="150px">
            <el-form-item label="设备名称" prop="deviceName">
              {{deviceInfo.deviceName}}
            </el-form-item>
          </el-form>
        </el-col>
            </el-row>
    </span>
  </el-dialog>
</template>

<script>
  import {getInfo} from "@/api/device/info";

  export default {
    name: "DeviceInfoView",
    data() {
      return {
        deviceInfo: {},
        deviceStatus: {},
        dialogVisible: false
      };
    },
    methods: {
      show(row) {
        console.log(row.id);
        getInfo(row.id).then(response => {
          this.deviceInfo = response.data;
        })
        this.dialogVisible = true;
      },
    }
  }
</script>

<style scoped>

</style>
