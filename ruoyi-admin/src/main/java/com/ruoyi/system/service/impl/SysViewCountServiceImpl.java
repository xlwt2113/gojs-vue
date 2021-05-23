package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysViewCountMapper;
import com.ruoyi.system.domain.SysViewCount;
import com.ruoyi.system.service.ISysViewCountService;

/**
 * 访问次数统计Service业务层处理
 * 
 * @author wanngtao
 * @date 2021-05-20
 */
@Service
public class SysViewCountServiceImpl implements ISysViewCountService 
{
    @Autowired
    private SysViewCountMapper sysViewCountMapper;

    /**
     * 查询访问次数统计
     * 
     * @param id 访问次数统计ID
     * @return 访问次数统计
     */
    @Override
    public SysViewCount selectSysViewCountById(Long id)
    {
        return sysViewCountMapper.selectSysViewCountById(id);
    }

    /**
     * 查询访问次数统计列表
     * 
     * @param sysViewCount 访问次数统计
     * @return 访问次数统计
     */
    @Override
    public List<SysViewCount> selectSysViewCountList(SysViewCount sysViewCount)
    {
        return sysViewCountMapper.selectSysViewCountList(sysViewCount);
    }

    /**
     * 新增访问次数统计
     * 
     * @param sysViewCount 访问次数统计
     * @return 结果
     */
    @Override
    public int insertSysViewCount(SysViewCount sysViewCount)
    {
        return sysViewCountMapper.insertSysViewCount(sysViewCount);
    }

    /**
     * 修改访问次数统计
     * 
     * @param sysViewCount 访问次数统计
     * @return 结果
     */
    @Override
    public int updateSysViewCount(SysViewCount sysViewCount)
    {
        return sysViewCountMapper.updateSysViewCount(sysViewCount);
    }

    /**
     * 批量删除访问次数统计
     * 
     * @param ids 需要删除的访问次数统计ID
     * @return 结果
     */
    @Override
    public int deleteSysViewCountByIds(Long[] ids)
    {
        return sysViewCountMapper.deleteSysViewCountByIds(ids);
    }

    /**
     * 删除访问次数统计信息
     * 
     * @param id 访问次数统计ID
     * @return 结果
     */
    @Override
    public int deleteSysViewCountById(Long id)
    {
        return sysViewCountMapper.deleteSysViewCountById(id);
    }
}
