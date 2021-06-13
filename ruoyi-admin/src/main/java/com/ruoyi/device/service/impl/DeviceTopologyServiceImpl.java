package com.ruoyi.device.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.device.mapper.DeviceTopologyMapper;
import com.ruoyi.device.domain.DeviceTopology;
import com.ruoyi.device.service.IDeviceTopologyService;

/**
 * 拓扑图维护Service业务层处理
 *
 * @author 王涛
 * @date 2021-05-09
 */
@Service
public class DeviceTopologyServiceImpl implements IDeviceTopologyService {
    @Autowired
    private DeviceTopologyMapper deviceTopologyMapper;

    /**
     * 查询拓扑图维护
     *
     * @param id 拓扑图维护ID
     * @return 拓扑图维护
     */
    @Override
    public DeviceTopology selectDeviceTopologyById(Long id) {
        return deviceTopologyMapper.selectDeviceTopologyById(id);
    }

    /**
     * 查询拓扑图维护列表
     *
     * @param deviceTopology 拓扑图维护
     * @return 拓扑图维护
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<DeviceTopology> selectDeviceTopologyList(DeviceTopology deviceTopology) {
        return deviceTopologyMapper.selectDeviceTopologyList(deviceTopology);
    }

    /**
     * 新增拓扑图维护
     *
     * @param deviceTopology 拓扑图维护
     * @return 结果
     */
    @Override
    public int insertDeviceTopology(DeviceTopology deviceTopology) {
        deviceTopology.setCreateTime(DateUtils.getNowDate());
        return deviceTopologyMapper.insertDeviceTopology(deviceTopology);
    }

    /**
     * 修改拓扑图维护
     *
     * @param deviceTopology 拓扑图维护
     * @return 结果
     */
    @Override
    public int updateDeviceTopology(DeviceTopology deviceTopology) {
        deviceTopology.setUpdateTime(DateUtils.getNowDate());
        return deviceTopologyMapper.updateDeviceTopology(deviceTopology);
    }

    /**
     * 批量删除拓扑图维护
     *
     * @param ids 需要删除的拓扑图维护ID
     * @return 结果
     */
    @Override
    public int deleteDeviceTopologyByIds(Long[] ids) {
        return deviceTopologyMapper.deleteDeviceTopologyByIds(ids);
    }

    /**
     * 删除拓扑图维护信息
     *
     * @param id 拓扑图维护ID
     * @return 结果
     */
    @Override
    public int deleteDeviceTopologyById(Long id) {
        return deviceTopologyMapper.deleteDeviceTopologyById(id);
    }

    /**
     * 同步拓扑数据
     *
     * @param maxDeviceId
     */
    @Override
    public void sysTopology(Long maxDeviceId) {
        List<DeviceTopology> list = deviceTopologyMapper.selectDeviceTopologyList(new DeviceTopology());
        for (DeviceTopology topology : list) {
            JSONObject obj = JSONObject.parseObject(topology.getImageData());
            JSONArray nodeDataArray = JSONArray.parseArray(obj.get("nodeDataArray").toString());
            System.out.println(nodeDataArray.toString());
            for (int i = 0; i < nodeDataArray.size(); i++) {
                JSONObject node = nodeDataArray.getJSONObject(i);
                System.out.println(node.get("key"));
                node.put("key", node.getLongValue("key") + maxDeviceId);
            }
            obj.put("nodeDataArray", nodeDataArray);

            JSONArray linkDataArray = JSONArray.parseArray(obj.get("linkDataArray").toString());
            System.out.println(linkDataArray.toString());
            for (int i = 0; i < linkDataArray.size(); i++) {
                JSONObject node = linkDataArray.getJSONObject(i);
                System.out.println(node.get("from"));
                node.put("from", node.getLongValue("from") + maxDeviceId);
                node.put("to", node.getLongValue("to") + maxDeviceId);
            }
            obj.put("linkDataArray", linkDataArray);

            System.out.println(obj.toJSONString());
            topology.setImageData(obj.toJSONString());
            deviceTopologyMapper.updateDeviceTopology(topology);
        }
    }
}
