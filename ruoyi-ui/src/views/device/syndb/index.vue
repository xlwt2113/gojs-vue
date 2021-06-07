<template>
  <el-row :gutter="24">
    <el-col :span="12" :offset="6" s-t-y-l-e="padding-top: 80px">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>数据同步操作</span>
        </div>
        <div>
          <el-form ref="form" :model="form" :rules="rules" label-width="180px">
            <el-form-item label="目标数据库节点ID" prop="rootParDeptId">
              <el-input v-model="form.rootParDeptId" />
            </el-form-item>
            <el-form-item label="目标数据最大机构ID" prop="maxDepId">
              <el-input v-model="form.maxDepId" />
            </el-form-item>
            <el-form-item label="目标数据库最大设备ID" prop="maxDeviceId">
              <el-input v-model="form.maxDeviceId" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm">开始同步</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
import { synDb } from '@/api/device/syndb'

export default {
  name: 'Syndb',
  data() {
    return {
      form: {
        rootParDeptId: '',
        maxDepId: '',
        maxDeviceId: ''
      },
      rules: {
        rootParDeptId: [
          { required: true, message: '目标数据库节点ID不能为空', trigger: 'blur' }
        ],
        maxDepId: [
          { required: true, message: '目标数据最大机构ID不能为空', trigger: 'blur' }
        ],
        maxDeviceId: [
          { required: true, message: '目标数据库最大设备ID不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          synDb(this.form).then(response => {
            this.msgSuccess('新增成功')
            this.open = false
            this.getList()
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
