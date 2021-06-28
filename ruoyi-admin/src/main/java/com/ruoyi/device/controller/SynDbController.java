package com.ruoyi.device.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.device.domain.DbSyn;
import com.ruoyi.device.service.IDeviceInfoService;
import com.ruoyi.device.service.IDeviceTopologyService;
import com.ruoyi.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/device/syndb")
public class SynDbController {

    @Autowired
    ISysDeptService deptService;

    @Autowired
    IDeviceInfoService deviceInfoService;

    @Autowired
    IDeviceTopologyService topologyService;

    /**
     * 同步数据库
     * @param synDbForm
     * @return
     */
    @PostMapping
    public AjaxResult listByDeviceId(@RequestBody DbSyn synDbForm){
        // 更新机构数据
        this.deptService.synDept(synDbForm.getRootParDeptId(),synDbForm.getMaxDepId());
        // 更新设备数据
        this.deviceInfoService.synDevice(synDbForm.getMaxDeviceId(),synDbForm.getMaxDepId());
        // 更新拓扑图数据
        this.topologyService.sysTopology(synDbForm.getMaxDeviceId());
        return AjaxResult.success();
    }


    /**
     * 同步数据库
     * @return
     */
    @GetMapping
    public AjaxResult updateDept(){
        List<SysDept> list = deptService.selectDeptList(new SysDept());
        for(SysDept dept:list){
            deptService.updateDept(dept);
        }
        return AjaxResult.success();
    }


}
