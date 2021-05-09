package com.ruoyi.device.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.device.mapper.DeviceInfoMapper;
import com.ruoyi.device.domain.DeviceInfo;
import com.ruoyi.device.service.IDeviceInfoService;

/**
 * deviceInfoService业务层处理
 * 
 * @author 王涛
 * @date 2021-05-07
 */
@Service
public class DeviceInfoServiceImpl implements IDeviceInfoService 
{
    @Autowired
    private DeviceInfoMapper deviceInfoMapper;

    /**
     * 查询deviceInfo
     * 
     * @param id deviceInfoID
     * @return deviceInfo
     */
    @Override
    public DeviceInfo selectDeviceInfoById(Integer id)
    {
        return deviceInfoMapper.selectDeviceInfoById(id);
    }

    /**
     * 查询deviceInfo列表
     * 
     * @param deviceInfo deviceInfo
     * @return deviceInfo
     */
    @Override
    public List<DeviceInfo> selectDeviceInfoList(DeviceInfo deviceInfo)
    {
        return deviceInfoMapper.selectDeviceInfoList(deviceInfo);
    }

    /**
     * 新增deviceInfo
     * 
     * @param deviceInfo deviceInfo
     * @return 结果
     */
    @Override
    public int insertDeviceInfo(DeviceInfo deviceInfo)
    {
        return deviceInfoMapper.insertDeviceInfo(deviceInfo);
    }

    /**
     * 修改deviceInfo
     * 
     * @param deviceInfo deviceInfo
     * @return 结果
     */
    @Override
    public int updateDeviceInfo(DeviceInfo deviceInfo)
    {
        return deviceInfoMapper.updateDeviceInfo(deviceInfo);
    }

    /**
     * 批量删除deviceInfo
     * 
     * @param ids 需要删除的deviceInfoID
     * @return 结果
     */
    @Override
    public int deleteDeviceInfoByIds(Integer[] ids)
    {
        return deviceInfoMapper.deleteDeviceInfoByIds(ids);
    }

    /**
     * 删除deviceInfo信息
     * 
     * @param id deviceInfoID
     * @return 结果
     */
    @Override
    public int deleteDeviceInfoById(Integer id)
    {
        return deviceInfoMapper.deleteDeviceInfoById(id);
    }
}
