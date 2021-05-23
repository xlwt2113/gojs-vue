package com.ruoyi.device.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.device.mapper.DeviceStatusMapper;
import com.ruoyi.device.domain.DeviceStatus;
import com.ruoyi.device.service.IDeviceStatusService;

/**
 * 设备状态表Service业务层处理
 *
 * @author 王涛
 * @date 2021-05-09
 */
@Service
public class DeviceStatusServiceImpl implements IDeviceStatusService
{
    @Autowired
    private DeviceStatusMapper deviceStatusMapper;

    /**
     * 查询设备状态表
     *
     * @param id 设备状态表ID
     * @return 设备状态表
     */
    @Override
    public DeviceStatus selectDeviceStatusById(Long id)
    {
        return deviceStatusMapper.selectDeviceStatusById(id);
    }

    /**
     * 查询设备状态表列表
     *
     * @param deviceStatus 设备状态表
     * @return 设备状态表
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<DeviceStatus> selectDeviceStatusList(DeviceStatus deviceStatus)
    {
        return deviceStatusMapper.selectDeviceStatusList(deviceStatus);
    }

    /**
     * 新增设备状态表
     *
     * @param deviceStatus 设备状态表
     * @return 结果
     */
    @Override
    public int insertDeviceStatus(DeviceStatus deviceStatus)
    {
        return deviceStatusMapper.insertDeviceStatus(deviceStatus);
    }

    /**
     * 修改设备状态表
     *
     * @param deviceStatus 设备状态表
     * @return 结果
     */
    @Override
    public int updateDeviceStatus(DeviceStatus deviceStatus)
    {
        deviceStatus.setUpdateTime(DateUtils.getNowDate());
        return deviceStatusMapper.updateDeviceStatus(deviceStatus);
    }

    /**
     * 批量删除设备状态表
     *
     * @param ids 需要删除的设备状态表ID
     * @return 结果
     */
    @Override
    public int deleteDeviceStatusByIds(Long[] ids)
    {
        return deviceStatusMapper.deleteDeviceStatusByIds(ids);
    }

    /**
     * 删除设备状态表信息
     *
     * @param id 设备状态表ID
     * @return 结果
     */
    @Override
    public int deleteDeviceStatusById(Long id)
    {
        return deviceStatusMapper.deleteDeviceStatusById(id);
    }

    /**
     * 根据设备id获取设备状态信息
     * @param deviceIds  设备id
     * @return
     */
    @Override
    public List<DeviceStatus> selectDeviceStatusListByDeviceIds(String deviceIds){
        return deviceStatusMapper.selectDeviceStatusListByDeviceIds(Convert.toStrArray(deviceIds));
    }
}
