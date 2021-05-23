package com.ruoyi.device.controller;

import java.util.List;

import com.ruoyi.device.domain.DeviceInfo;
import org.springframework.http.HttpRequest;
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
import com.ruoyi.device.domain.DeviceEvent;
import com.ruoyi.device.service.IDeviceEventService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import javax.servlet.ServletRequest;

/**
 * 设备事件表Controller
 *
 * @author 王涛
 * @date 2021-05-09
 */
@RestController
@RequestMapping("/device/event")
public class DeviceEventController extends BaseController
{
    @Autowired
    private IDeviceEventService deviceEventService;

    /**
     * 查询设备事件表列表
     */
    @PreAuthorize("@ss.hasPermi('device:event:list')")
    @GetMapping("/list")
    public TableDataInfo list(DeviceEvent deviceEvent, ServletRequest request)
    {
        if(request.getParameter("deviceName")!=null){
            DeviceInfo info = new DeviceInfo();
            info.setDeviceName(request.getParameter("deviceName"));
            System.out.println(request.getParameter("deviceName"));
            deviceEvent.setDeviceInfo(info);
        }
        startPage();
        List<DeviceEvent> list = deviceEventService.selectDeviceEventList(deviceEvent);
        return getDataTable(list);
    }

    @GetMapping(value = "listByDeviceId/{deviceId}")
    public AjaxResult listByDeviceId(@PathVariable("deviceId") Long deviceId){
        DeviceEvent param = new DeviceEvent();
        param.setDeviceId(deviceId);
        List<DeviceEvent> list = deviceEventService.selectDeviceEventList(param);
        return AjaxResult.success(list);
    }

    /**
     * 导出设备事件表列表
     */
    @PreAuthorize("@ss.hasPermi('device:event:export')")
    @Log(title = "设备事件表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DeviceEvent deviceEvent)
    {
        List<DeviceEvent> list = deviceEventService.selectDeviceEventList(deviceEvent);
        ExcelUtil<DeviceEvent> util = new ExcelUtil<DeviceEvent>(DeviceEvent.class);
        return util.exportExcel(list, "设备事件表数据");
    }

    /**
     * 获取设备事件表详细信息
     */
    @PreAuthorize("@ss.hasPermi('device:event:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(deviceEventService.selectDeviceEventById(id));
    }

    /**
     * 新增设备事件表
     */
    @PreAuthorize("@ss.hasPermi('device:event:add')")
    @Log(title = "设备事件表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DeviceEvent deviceEvent)
    {
        return toAjax(deviceEventService.insertDeviceEvent(deviceEvent));
    }

    /**
     * 修改设备事件表
     */
    @PreAuthorize("@ss.hasPermi('device:event:edit')")
    @Log(title = "设备事件表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DeviceEvent deviceEvent)
    {
        return toAjax(deviceEventService.updateDeviceEvent(deviceEvent));
    }

    /**
     * 删除设备事件表
     */
    @PreAuthorize("@ss.hasPermi('device:event:remove')")
    @Log(title = "设备事件表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(deviceEventService.deleteDeviceEventByIds(ids));
    }
}
