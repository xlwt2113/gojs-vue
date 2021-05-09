package com.ruoyi.device.controller;

import java.util.List;

import com.ruoyi.device.domain.DeviceInfo;
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
import com.ruoyi.device.domain.DeviceStatus;
import com.ruoyi.device.service.IDeviceStatusService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import javax.servlet.ServletRequest;

/**
 * 设备状态表Controller
 *
 * @author 王涛
 * @date 2021-05-09
 */
@RestController
@RequestMapping("/device/status")
public class DeviceStatusController extends BaseController
{
    @Autowired
    private IDeviceStatusService deviceStatusService;

    /**
     * 查询设备状态表列表
     */
    @PreAuthorize("@ss.hasPermi('device:status:list')")
    @GetMapping("/list")
    public TableDataInfo list(DeviceStatus deviceStatus, ServletRequest request)
    {
        if(request.getParameter("deviceName")!=null){
            DeviceInfo info = new DeviceInfo();
            info.setDeviceName(request.getParameter("deviceName"));
            deviceStatus.setDeviceInfo(info);
        }
        startPage();
        List<DeviceStatus> list = deviceStatusService.selectDeviceStatusList(deviceStatus);
        return getDataTable(list);
    }

    /**
     * 导出设备状态表列表
     */
    @PreAuthorize("@ss.hasPermi('device:status:export')")
    @Log(title = "设备状态表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DeviceStatus deviceStatus)
    {
        List<DeviceStatus> list = deviceStatusService.selectDeviceStatusList(deviceStatus);
        ExcelUtil<DeviceStatus> util = new ExcelUtil<DeviceStatus>(DeviceStatus.class);
        return util.exportExcel(list, "设备状态表数据");
    }

    /**
     * 获取设备状态表详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(deviceStatusService.selectDeviceStatusById(id));
    }

    /**
     * 新增设备状态表
     */
    @PreAuthorize("@ss.hasPermi('device:status:add')")
    @Log(title = "设备状态表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DeviceStatus deviceStatus)
    {
        return toAjax(deviceStatusService.insertDeviceStatus(deviceStatus));
    }

    /**
     * 修改设备状态表
     */
    @PreAuthorize("@ss.hasPermi('device:status:edit')")
    @Log(title = "设备状态表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DeviceStatus deviceStatus)
    {
        return toAjax(deviceStatusService.updateDeviceStatus(deviceStatus));
    }

    /**
     * 删除设备状态表
     */
    @PreAuthorize("@ss.hasPermi('device:status:remove')")
    @Log(title = "设备状态表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(deviceStatusService.deleteDeviceStatusByIds(ids));
    }
}
