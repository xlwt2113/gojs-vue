package com.ruoyi.device.service;

import java.util.List;
import com.ruoyi.device.domain.DeviceStatus;

/**
 * 设备状态表Service接口
 *
 * @author 王涛
 * @date 2021-05-09
 */
public interface IDeviceStatusService
{
    /**
     * 查询设备状态表
     *
     * @param id 设备状态表ID
     * @return 设备状态表
     */
    public DeviceStatus selectDeviceStatusById(Long id);

    /**
     * 查询设备状态表列表
     *
     * @param deviceStatus 设备状态表
     * @return 设备状态表集合
     */
    public List<DeviceStatus> selectDeviceStatusList(DeviceStatus deviceStatus);

    /**
     * 新增设备状态表
     *
     * @param deviceStatus 设备状态表
     * @return 结果
     */
    public int insertDeviceStatus(DeviceStatus deviceStatus);

    /**
     * 修改设备状态表
     *
     * @param deviceStatus 设备状态表
     * @return 结果
     */
    public int updateDeviceStatus(DeviceStatus deviceStatus);

    /**
     * 批量删除设备状态表
     *
     * @param ids 需要删除的设备状态表ID
     * @return 结果
     */
    public int deleteDeviceStatusByIds(Long[] ids);

    /**
     * 删除设备状态表信息
     *
     * @param id 设备状态表ID
     * @return 结果
     */
    public int deleteDeviceStatusById(Long id);

    /**
     * 根据设备id获取设备状态信息
     * @param deviceIds
     * @return
     */
    public List<DeviceStatus> selectDeviceStatusListByDeviceIds(String deviceIds);
}
