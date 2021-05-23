package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysViewCount;

/**
 * 访问次数统计Mapper接口
 * 
 * @author wanngtao
 * @date 2021-05-20
 */
public interface SysViewCountMapper 
{
    /**
     * 查询访问次数统计
     * 
     * @param id 访问次数统计ID
     * @return 访问次数统计
     */
    public SysViewCount selectSysViewCountById(Long id);

    /**
     * 查询访问次数统计列表
     * 
     * @param sysViewCount 访问次数统计
     * @return 访问次数统计集合
     */
    public List<SysViewCount> selectSysViewCountList(SysViewCount sysViewCount);

    /**
     * 新增访问次数统计
     * 
     * @param sysViewCount 访问次数统计
     * @return 结果
     */
    public int insertSysViewCount(SysViewCount sysViewCount);

    /**
     * 修改访问次数统计
     * 
     * @param sysViewCount 访问次数统计
     * @return 结果
     */
    public int updateSysViewCount(SysViewCount sysViewCount);

    /**
     * 删除访问次数统计
     * 
     * @param id 访问次数统计ID
     * @return 结果
     */
    public int deleteSysViewCountById(Long id);

    /**
     * 批量删除访问次数统计
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysViewCountByIds(Long[] ids);
}
