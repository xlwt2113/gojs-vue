package com.ruoyi.device.controller;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.SysViewCount;
import com.ruoyi.system.service.ISysViewCountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.device.domain.DeviceTopology;
import com.ruoyi.device.service.IDeviceTopologyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 拓扑图维护Controller
 *
 * @author 王涛
 * @date 2021-05-09
 */
@RestController
@RequestMapping("/device/topology")
public class DeviceTopologyController extends BaseController
{
    @Autowired
    private IDeviceTopologyService deviceTopologyService;

    @Autowired
    private ISysViewCountService sysViewCountService;

    /**
     * 查询拓扑图维护列表
     */
    @PreAuthorize("@ss.hasPermi('device:topology:list')")
    @GetMapping("/list")
    public TableDataInfo list(DeviceTopology deviceTopology)
    {
        startPage();
        List<DeviceTopology> list = deviceTopologyService.selectDeviceTopologyList(deviceTopology);
        return getDataTable(list);
    }

    /**
     * 导出拓扑图维护列表
     */
    @PreAuthorize("@ss.hasPermi('device:topology:export')")
    @Log(title = "拓扑图维护", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DeviceTopology deviceTopology)
    {
        List<DeviceTopology> list = deviceTopologyService.selectDeviceTopologyList(deviceTopology);
        ExcelUtil<DeviceTopology> util = new ExcelUtil<DeviceTopology>(DeviceTopology.class);
        return util.exportExcel(list, "拓扑图维护数据");
    }

    /**
     * 获取拓扑图维护详细信息
     */
    @PreAuthorize("@ss.hasPermi('device:topology:query')")
    @GetMapping(value = "/{id}")
    @Log(title = "拓扑图查看", businessType = BusinessType.OTHER)
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        // 记录访问次数
        SysViewCount viewCount = new SysViewCount();
        viewCount.setSysUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        viewCount.setTopologyId(id);
        sysViewCountService.insertSysViewCount(viewCount);
        return AjaxResult.success(deviceTopologyService.selectDeviceTopologyById(id));
    }

    /**
     * 新增拓扑图维护
     */
    @PreAuthorize("@ss.hasPermi('device:topology:add')")
    @Log(title = "拓扑图维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DeviceTopology deviceTopology)
    {
        return toAjax(deviceTopologyService.insertDeviceTopology(deviceTopology));
    }

    /**
     * 修改拓扑图维护
     */
    @PreAuthorize("@ss.hasPermi('device:topology:edit')")
    @Log(title = "拓扑图维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DeviceTopology deviceTopology)
    {
        return toAjax(deviceTopologyService.updateDeviceTopology(deviceTopology));
    }

    /**
     * 删除拓扑图维护
     */
    @PreAuthorize("@ss.hasPermi('device:topology:remove')")
    @Log(title = "拓扑图维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(deviceTopologyService.deleteDeviceTopologyByIds(ids));
    }
}
