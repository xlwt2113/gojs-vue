package com.ruoyi.device.controller;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.device.domain.DeviceStatus;
import com.ruoyi.device.service.IDeviceStatusService;
import com.ruoyi.system.service.ISysDeptService;
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
import com.ruoyi.device.domain.DeviceInfo;
import com.ruoyi.device.service.IDeviceInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * deviceInfoController
 *
 * @author 王涛
 * @date 2021-05-07
 */
@RestController
@RequestMapping("/device/info")
public class DeviceInfoController extends BaseController
{
    @Autowired
    private IDeviceInfoService deviceInfoService;


    /**
     * 查询deviceInfo列表
     */
    @PreAuthorize("@ss.hasPermi('device:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(DeviceInfo deviceInfo)
    {
        startPage();
        List<DeviceInfo> list = deviceInfoService.selectDeviceInfoList(deviceInfo);
        return getDataTable(list);
    }

    /**
     * 导出deviceInfo列表
     */
    @PreAuthorize("@ss.hasPermi('device:info:export')")
    @Log(title = "设备信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DeviceInfo deviceInfo)
    {
        List<DeviceInfo> list = deviceInfoService.selectDeviceInfoList(deviceInfo);
        ExcelUtil<DeviceInfo> util = new ExcelUtil<DeviceInfo>(DeviceInfo.class);
        return util.exportExcel(list, "deviceInfo数据");
    }

    /**
     * 获取deviceInfo详细信息
     */
    @PreAuthorize("@ss.hasPermi('device:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(deviceInfoService.selectDeviceInfoById(id));
    }

    /**
     * 新增deviceInfo
     */
    @PreAuthorize("@ss.hasPermi('device:info:add')")
    @Log(title = "deviceInfo", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DeviceInfo deviceInfo)
    {
        return toAjax(deviceInfoService.insertDeviceInfo(deviceInfo));
    }

    /**
     * 修改deviceInfo
     */
    @PreAuthorize("@ss.hasPermi('device:info:edit')")
    @Log(title = "设备信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DeviceInfo deviceInfo)
    {
        return toAjax(deviceInfoService.updateDeviceInfo(deviceInfo));
    }

    /**
     * 删除deviceInfo
     */
    @PreAuthorize("@ss.hasPermi('device:info:remove')")
    @Log(title = "设备信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(deviceInfoService.deleteDeviceInfoByIds(ids));
    }
}
