package com.ruoyi.device.service;

import java.util.List;
import com.ruoyi.device.domain.DeviceEvent;

/**
 * 设备事件表Service接口
 * 
 * @author 王涛
 * @date 2021-05-09
 */
public interface IDeviceEventService 
{
    /**
     * 查询设备事件表
     * 
     * @param id 设备事件表ID
     * @return 设备事件表
     */
    public DeviceEvent selectDeviceEventById(Long id);

    /**
     * 查询设备事件表列表
     * 
     * @param deviceEvent 设备事件表
     * @return 设备事件表集合
     */
    public List<DeviceEvent> selectDeviceEventList(DeviceEvent deviceEvent);

    /**
     * 新增设备事件表
     * 
     * @param deviceEvent 设备事件表
     * @return 结果
     */
    public int insertDeviceEvent(DeviceEvent deviceEvent);

    /**
     * 修改设备事件表
     * 
     * @param deviceEvent 设备事件表
     * @return 结果
     */
    public int updateDeviceEvent(DeviceEvent deviceEvent);

    /**
     * 批量删除设备事件表
     * 
     * @param ids 需要删除的设备事件表ID
     * @return 结果
     */
    public int deleteDeviceEventByIds(Long[] ids);

    /**
     * 删除设备事件表信息
     * 
     * @param id 设备事件表ID
     * @return 结果
     */
    public int deleteDeviceEventById(Long id);
}
