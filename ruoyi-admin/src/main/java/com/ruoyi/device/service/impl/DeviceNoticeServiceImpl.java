package com.ruoyi.device.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.device.domain.DeviceNotice;
import com.ruoyi.device.mapper.DeviceNoticeMapper;
import com.ruoyi.device.service.IDeviceNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备信息通知Service业务层处理
 *
 * @author 王涛
 * @date 2021-04-21
 */
@Service
public class DeviceNoticeServiceImpl implements IDeviceNoticeService
{
    @Autowired
    private DeviceNoticeMapper deviceNoticeMapper;

    /**
     * 查询设备信息通知
     *
     * @param id 设备信息通知ID
     * @return 设备信息通知
     */
    @Override
    public DeviceNotice selectDeviceNoticeById(Long id)
    {
        return deviceNoticeMapper.selectDeviceNoticeById(id);
    }

    /**
     * 查询设备信息通知列表
     *
     * @param deviceNotice 设备信息通知
     * @return 设备信息通知
     */
    @Override
    public List<DeviceNotice> selectDeviceNoticeList(DeviceNotice deviceNotice)
    {
        return deviceNoticeMapper.selectDeviceNoticeList(deviceNotice);
    }

    /**
     * 新增设备信息通知
     *
     * @param deviceNotice 设备信息通知
     * @return 结果
     */
    @Override
    public int insertDeviceNotice(DeviceNotice deviceNotice)
    {
        deviceNotice.setCreateTime(DateUtils.getNowDate());
        return deviceNoticeMapper.insertDeviceNotice(deviceNotice);
    }

    /**
     * 修改设备信息通知
     *
     * @param deviceNotice 设备信息通知
     * @return 结果
     */
    @Override
    public int updateDeviceNotice(DeviceNotice deviceNotice)
    {
        deviceNotice.setUpdateTime(DateUtils.getNowDate());
        return deviceNoticeMapper.updateDeviceNotice(deviceNotice);
    }

    /**
     * 删除设备信息通知对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDeviceNoticeByIds(String ids)
    {
        return deviceNoticeMapper.deleteDeviceNoticeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除设备信息通知信息
     *
     * @param id 设备信息通知ID
     * @return 结果
     */
    @Override
    public int deleteDeviceNoticeById(Long id)
    {
        return deviceNoticeMapper.deleteDeviceNoticeById(id);
    }
}
