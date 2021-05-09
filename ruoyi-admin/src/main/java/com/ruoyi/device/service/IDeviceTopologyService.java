package com.ruoyi.device.service;

import java.util.List;
import com.ruoyi.device.domain.DeviceTopology;

/**
 * 拓扑图维护Service接口
 * 
 * @author 王涛
 * @date 2021-05-09
 */
public interface IDeviceTopologyService 
{
    /**
     * 查询拓扑图维护
     * 
     * @param id 拓扑图维护ID
     * @return 拓扑图维护
     */
    public DeviceTopology selectDeviceTopologyById(Long id);

    /**
     * 查询拓扑图维护列表
     * 
     * @param deviceTopology 拓扑图维护
     * @return 拓扑图维护集合
     */
    public List<DeviceTopology> selectDeviceTopologyList(DeviceTopology deviceTopology);

    /**
     * 新增拓扑图维护
     * 
     * @param deviceTopology 拓扑图维护
     * @return 结果
     */
    public int insertDeviceTopology(DeviceTopology deviceTopology);

    /**
     * 修改拓扑图维护
     * 
     * @param deviceTopology 拓扑图维护
     * @return 结果
     */
    public int updateDeviceTopology(DeviceTopology deviceTopology);

    /**
     * 批量删除拓扑图维护
     * 
     * @param ids 需要删除的拓扑图维护ID
     * @return 结果
     */
    public int deleteDeviceTopologyByIds(Long[] ids);

    /**
     * 删除拓扑图维护信息
     * 
     * @param id 拓扑图维护ID
     * @return 结果
     */
    public int deleteDeviceTopologyById(Long id);
}
