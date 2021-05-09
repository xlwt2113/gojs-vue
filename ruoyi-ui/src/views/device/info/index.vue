<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备名称" prop="deviceName">
        <el-input
          v-model="queryParams.deviceName"
          placeholder="请输入设备名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备类型" prop="deviceType">
        <el-select v-model="queryParams.deviceType" placeholder="请选择设备类型" clearable size="small">
          <el-option
            v-for="dict in deviceTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="设备型号" prop="deviceModel">
        <el-select v-model="queryParams.deviceModel" placeholder="请选择设备型号" clearable size="small">
          <el-option
            v-for="dict in deviceModelOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="设备IP1" prop="deviceIp1">
        <el-input
          v-model="queryParams.deviceIp1"
          placeholder="请输入设备IP1"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="安装日期" prop="installDate">
        <el-date-picker clearable size="small"
                        v-model="queryParams.installDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择安装日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="安装位置" prop="location">
        <el-input
          v-model="queryParams.location"
          placeholder="请输入安装位置"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部门" prop="deptId">
        <treeselect v-model="queryParams.deptId" :options="deptOptions" :show-count="true" placeholder="请选择归属部门" style="width: 180px" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['device:info:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['device:info:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['device:info:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['device:info:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="归属部门" align="center" prop="dept.deptName"/>
      <el-table-column label="设备名称" align="center" prop="deviceName">
        <template slot-scope="scope">
          <el-button type="text">{{scope.row.deviceName}}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="设备类型" align="center" prop="deviceType" :formatter="deviceTypeFormat"/>
      <el-table-column label="设备型号" align="center" prop="deviceModel" :formatter="deviceModelFormat"/>
      <el-table-column label="制造商" align="center" prop="manufacturer"/>
      <el-table-column label="设备IP1" align="center" prop="deviceIp1"/>
      <el-table-column label="设备IP2" align="center" prop="deviceIp2"/>
<!--      <el-table-column label="设备IP3" align="center" prop="deviceIp3"/>-->
<!--      <el-table-column label="设备IP4" align="center" prop="deviceIp4"/>-->
<!--      <el-table-column label="设备IP5" align="center" prop="deviceIp5"/>-->
<!--      <el-table-column label="设备IP6" align="center" prop="deviceIp6"/>-->
<!--      <el-table-column label="设备IP7" align="center" prop="deviceIp7"/>-->
<!--      <el-table-column label="设备IP8" align="center" prop="deviceIp8"/>-->
      <el-table-column label="安装日期" align="center" prop="installDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.installDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="安装位置" align="center" prop="location"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['device:info:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['device:info:remove']"
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

    <!-- 添加或修改deviceInfo对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-row>
        <el-col :span="21">
          <el-form ref="form" :model="form" :rules="rules" label-width="150px">
            <el-form-item label="设备名称" prop="deviceName">
              <el-input v-model="form.deviceName" placeholder="请输入设备名称"/>
            </el-form-item>
            <el-form-item label="部门" prop="deptId">
              <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" placeholder="请选择归属部门" />
            </el-form-item>
            <el-form-item label="设备类型" prop="deviceType">
              <el-select v-model="form.deviceType" placeholder="请选择设备类型">
                <el-option
                  v-for="dict in deviceTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="设备型号" prop="deviceModel">
              <el-select v-model="form.deviceModel" placeholder="请选择设备型号">
                <el-option
                  v-for="dict in deviceModelOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="制造商" prop="manufacturer">
              <el-input v-model="form.manufacturer" placeholder="请输入制造商"/>
            </el-form-item>
            <el-form-item label="设备IP1" prop="deviceIp1">
              <el-input v-model="form.deviceIp1" placeholder="请输入设备IP1"/>
            </el-form-item>
            <el-form-item label="设备IP2" prop="deviceIp2">
              <el-input v-model="form.deviceIp2" placeholder="请输入设备IP2"/>
            </el-form-item>
            <el-form-item label="设备IP3" prop="deviceIp3">
              <el-input v-model="form.deviceIp3" placeholder="请输入设备IP3"/>
            </el-form-item>
            <el-form-item label="设备IP4" prop="deviceIp4">
              <el-input v-model="form.deviceIp4" placeholder="请输入设备IP4"/>
            </el-form-item>
            <el-form-item label="设备IP5" prop="deviceIp5">
              <el-input v-model="form.deviceIp5" placeholder="请输入设备IP5"/>
            </el-form-item>
            <el-form-item label="设备IP6" prop="deviceIp6">
              <el-input v-model="form.deviceIp6" placeholder="请输入设备IP6"/>
            </el-form-item>
            <el-form-item label="设备IP7" prop="deviceIp7">
              <el-input v-model="form.deviceIp7" placeholder="请输入设备IP7"/>
            </el-form-item>
            <el-form-item label="设备IP8" prop="deviceIp8">
              <el-input v-model="form.deviceIp8" placeholder="请输入设备IP8"/>
            </el-form-item>
            <el-form-item label="安装日期" prop="installDate">
              <el-date-picker clearable size="small"
                              v-model="form.installDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择安装日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="安装位置" prop="location">
              <el-input v-model="form.location" placeholder="请输入安装位置"/>
            </el-form-item>
            <el-form-item label="备注" prop="note">
              <el-input v-model="form.note" placeholder="请输入备注"/>
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
  import {listInfo, getInfo, delInfo, addInfo, updateInfo, exportInfo} from "@/api/device/info";
  import { treeselect } from "@/api/system/dept";
  import Treeselect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";

  export default {
    name: "Info",
    components: { Treeselect },
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
        // deviceInfo表格数据
        infoList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 设备类型字典
        deviceTypeOptions: [],
        // 设备型号字典
        deviceModelOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          deviceName: null,
          deviceType: null,
          deviceModel: null,
          manufacturer: null,
          deviceIp1: null,
          deviceIp2: null,
          deviceIp3: null,
          deviceIp4: null,
          deviceIp5: null,
          deviceIp6: null,
          deviceIp7: null,
          deviceIp8: null,
          installDate: null,
          location: null,
          note: null,
          deptId: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          deviceName: [
            {required: true, message: "设备名称不能为空", trigger: "blur"}
          ],
          deviceType: [
            {required: true, message: "设备类型不能为空", trigger: "change"}
          ],
          deviceModel: [
            {required: true, message: "设备型号不能为空", trigger: "change"}
          ],
          deviceIp1: [
            {required: true, message: "设备IP1不能为空", trigger: "blur"}
          ],
        },
        // 部门树选项
        deptOptions: undefined,
      };
    },
    created() {
      this.getList();
      this.getTreeselect();
      this.getDicts("device_info_type").then(response => {
        this.deviceTypeOptions = response.data;
      });
      this.getDicts("device_info_model").then(response => {
        this.deviceModelOptions = response.data;
      });
    },
    methods: {
      /** 查询部门下拉树结构 */
      getTreeselect() {
        treeselect().then(response => {
          this.deptOptions = response.data;
        });
      },
      /** 查询deviceInfo列表 */
      getList() {
        this.loading = true;
        listInfo(this.queryParams).then(response => {
          this.infoList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 设备类型字典翻译
      deviceTypeFormat(row, column) {
        return this.selectDictLabel(this.deviceTypeOptions, row.deviceType);
      },
      // 设备型号字典翻译
      deviceModelFormat(row, column) {
        return this.selectDictLabel(this.deviceModelOptions, row.deviceModel);
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          deviceName: null,
          deviceType: null,
          deviceModel: null,
          manufacturer: null,
          deviceIp1: null,
          deviceIp2: null,
          deviceIp3: null,
          deviceIp4: null,
          deviceIp5: null,
          deviceIp6: null,
          deviceIp7: null,
          deviceIp8: null,
          installDate: null,
          location: null,
          note: null,
          miningArea: null,
          company: null,
          deptId: null
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加设备信息";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getInfo(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改设备信息";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateInfo(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addInfo(this.form).then(response => {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除deviceInfo编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delInfo(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有deviceInfo数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportInfo(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
      }
    }
  };
</script>
