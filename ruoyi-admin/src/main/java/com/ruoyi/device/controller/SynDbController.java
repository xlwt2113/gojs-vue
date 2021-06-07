package com.ruoyi.device.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.device.domain.DbSyn;
import com.ruoyi.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/device/syndb")
public class SynDbController {

    @Autowired
    ISysDeptService deptService;

    /**
     * 同步数据库
     * @param synDbForm
     * @return
     */
    @PostMapping
    public AjaxResult listByDeviceId(@RequestBody DbSyn synDbForm){
        // 更新机构数据
        List<SysDept> list = deptService.selectDeptList(new SysDept());
        for(SysDept dept : list){
            dept.setDeptId(synDbForm.getRootParDeptId()+dept.getDeptId());
            dept.setParentId(dept.getParentId()+synDbForm.getRootParDeptId());
            this.deptService.updateDept(dept);
        }

        return AjaxResult.success();
    }
}
