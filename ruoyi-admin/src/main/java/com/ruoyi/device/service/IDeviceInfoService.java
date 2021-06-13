package com.ruoyi.device.service;

import java.util.List;
import com.ruoyi.device.domain.DeviceInfo;

/**
 * deviceInfoService接口
 *
 * @author 王涛
 * @date 2021-05-07
 */
public interface IDeviceInfoService
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
     * 批量删除deviceInfo
     *
     * @param ids 需要删除的deviceInfoID
     * @return 结果
     */
    public int deleteDeviceInfoByIds(Integer[] ids);

    /**
     * 删除deviceInfo信息
     *
     * @param id deviceInfoID
     * @return 结果
     */
    public int deleteDeviceInfoById(Long id);

    /**
     * 更新设备信息
     */
    public void synDevice(Long maxDeviceId,Long maxDepId);
}
