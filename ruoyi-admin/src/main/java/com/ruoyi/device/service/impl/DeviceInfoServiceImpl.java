package com.ruoyi.device.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.system.mapper.SysDeptMapper;
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

    @Autowired
    private SysDeptMapper deptMapper;

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
    @DataScope(deptAlias = "d")
    public List<DeviceInfo> selectDeviceInfoList(DeviceInfo deviceInfo)
    {
        List<DeviceInfo> list = deviceInfoMapper.selectDeviceInfoList(deviceInfo);
        for(DeviceInfo info : list){
            // 获取部门层级
            String[] deptIds = info.getDept().getAncestors().split(",");
            String deptName = "";
            for(String deptId: deptIds){
                if(Long.parseLong(deptId)!=0){
                    SysDept dept = deptMapper.selectDeptById(Long.parseLong(deptId));
                    deptName = deptName + dept.getDeptName() + " - ";
                }
            }
            info.getDept().setDeptName(deptName + info.getDept().getDeptName());
        }
        return list;

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
