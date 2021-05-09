package com.ruoyi.device.mapper;

import java.util.List;
import com.ruoyi.device.domain.DeviceStatus;

/**
 * 设备状态表Mapper接口
 * 
 * @author 王涛
 * @date 2021-05-09
 */
public interface DeviceStatusMapper 
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
     * 删除设备状态表
     * 
     * @param id 设备状态表ID
     * @return 结果
     */
    public int deleteDeviceStatusById(Long id);

    /**
     * 批量删除设备状态表
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDeviceStatusByIds(Long[] ids);
}
