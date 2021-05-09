package com.ruoyi.device.service;

import com.ruoyi.device.domain.DeviceNotice;

import java.util.List;

/**
 * 设备信息通知Service接口
 *
 * @author 王涛
 * @date 2021-04-21
 */
public interface IDeviceNoticeService
{
    /**
     * 查询设备信息通知
     *
     * @param id 设备信息通知ID
     * @return 设备信息通知
     */
    public DeviceNotice selectDeviceNoticeById(Long id);

    /**
     * 查询设备信息通知列表
     *
     * @param deviceNotice 设备信息通知
     * @return 设备信息通知集合
     */
    public List<DeviceNotice> selectDeviceNoticeList(DeviceNotice deviceNotice);

    /**
     * 新增设备信息通知
     *
     * @param deviceNotice 设备信息通知
     * @return 结果
     */
    public int insertDeviceNotice(DeviceNotice deviceNotice);

    /**
     * 修改设备信息通知
     *
     * @param deviceNotice 设备信息通知
     * @return 结果
     */
    public int updateDeviceNotice(DeviceNotice deviceNotice);

    /**
     * 批量删除设备信息通知
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDeviceNoticeByIds(String ids);

    /**
     * 删除设备信息通知信息
     *
     * @param id 设备信息通知ID
     * @return 结果
     */
    public int deleteDeviceNoticeById(Long id);
}
