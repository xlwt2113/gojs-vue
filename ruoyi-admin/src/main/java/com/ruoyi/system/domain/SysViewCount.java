package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 访问次数统计对象 sys_view_count
 *
 * @author wanngtao
 * @date 2021-05-20
 */
public class SysViewCount extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 拓扑图id */
    @Excel(name = "拓扑图id")
    private Long topologyId;

    /** 访问用户id */
    @Excel(name = "访问用户id")
    private Long sysUserId;

    /** 访问时间 */
    @Excel(name = "访问时间")
    private Date visitTime;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setTopologyId(Long topologyId)
    {
        this.topologyId = topologyId;
    }

    public Long getTopologyId()
    {
        return topologyId;
    }
    public void setSysUserId(Long sysUserId)
    {
        this.sysUserId = sysUserId;
    }

    public Long getSysUserId()
    {
        return sysUserId;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("topologyId", getTopologyId())
            .append("sysUserId", getSysUserId())
            .append("visitTime", getVisitTime())
            .toString();
    }
}
