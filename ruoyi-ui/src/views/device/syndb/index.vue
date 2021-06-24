<template>
  <el-row :gutter="24">
    <el-col :span="12" :offset="6" s-t-y-l-e="padding-top: 80px">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>数据同步操作</span>
        </div>
        <div>
          <el-form ref="form" :model="form" :rules="rules" label-width="180px">
            <el-form-item label="目标数据库机构节点ID" prop="rootParDeptId">
              <el-input v-model="form.rootParDeptId" />
            </el-form-item>
            <el-form-item label="目标数据最大机构ID" prop="maxDepId">
              <el-input v-model="form.maxDepId" />
            </el-form-item>
            <el-form-item label="目标数据库最大设备ID" prop="maxDeviceId">
              <el-input v-model="form.maxDeviceId" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="open">开始同步</el-button>
            </el-form-item>
          </el-form>
        </div>
        <div style="font-size: 12px">
          操作说明：
          <div style="padding: 15px">1 执行操作前请先备份远程（云端）及本地数据库，远程（云端）系统暂停运行，防止数据库内容变更导致同步失败;</div>
          <div style="padding: 15px">2 <span style="color: red">目标数据库机构节点ID</span>：为云端数据的机构的ID，即为本地数据库根机构的上级机构ID，例如本地数据库的根机构是集团1，需要挂载到云端数据的名字为全国机构下方，则该ID即为名字为全国的机构ID;</div>
          <div style="padding: 15px">3 <span style="color: red">目标数据最大机构ID</span>：即云端数据库中机构表的最大主键ID值，同步完毕后，本地机构的ID值会排在该ID后面;</div>
          <div style="padding: 15px">4 <span style="color: red">目标数据库最大设备ID</span>：即云端数据库中设备表的最大主键ID值，同步完毕后，本地设备表的ID值会排在该ID后面;</div>
          <div style="padding: 15px">5 执行完毕后，即可将本地数据库中的机构表，设备表及拓扑表(sys_dept，device_info，device_topology)中数据拷贝到远程（云端）数据库中，此时就完成了本地数据到云端的合并 ;</div>
          <div style="padding: 15px">注意： 同步sys_dept表只选择del_flag=0的数据（SELECT * FROM sys_dept where del_flag = 0）</div>
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
    },
    open() {
      this.$confirm('再次确定是否已备份数据库?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$refs['form'].validate(valid => {
          if (valid) {
            synDb(this.form).then(response => {
              this.msgSuccess('操作完成')
              this.open = false
              this.getList()
            })
          }
        })
      }).catch(() => {
      })
    }
  }
}
</script>

<style scoped>

</style>
