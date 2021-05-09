package com.ruoyi.device.mapper;

import java.util.List;
import com.ruoyi.device.domain.DeviceInfo;

/**
 * deviceInfoMapper接口
 * 
 * @author 王涛
 * @date 2021-05-07
 */
public interface DeviceInfoMapper 
{
    /**
     * 查询deviceInfo
     * 
     * @param id deviceInfoID
     * @return deviceInfo
     */
    public DeviceInfo selectDeviceInfoById(Integer id);

    /**
     * 查询deviceInfo列表
     * 
     * @param deviceInfo deviceInfo
     * @return deviceInfo集合
     */
    public List<DeviceInfo> selectDeviceInfoList(DeviceInfo deviceInfo);

    /**
     * 新增deviceInfo
     * 
     * @param deviceInfo deviceInfo
     * @return 结果
     */
    public int insertDeviceInfo(DeviceInfo deviceInfo);

    /**
     * 修改deviceInfo
     * 
     * @param deviceInfo deviceInfo
     * @return 结果
     */
    public int updateDeviceInfo(DeviceInfo deviceInfo);

    /**
     * 删除deviceInfo
     * 
     * @param id deviceInfoID
     * @return 结果
     */
    public int deleteDeviceInfoById(Integer id);

    /**
     * 批量删除deviceInfo
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDeviceInfoByIds(Integer[] ids);
}
