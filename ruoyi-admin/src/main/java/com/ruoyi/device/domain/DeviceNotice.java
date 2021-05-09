package com.ruoyi.device.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 设备信息通知对象 device_notice
 *
 * @author 王涛
 * @date 2021-04-21
 */
public class DeviceNotice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 事件类型：字典表定义 */
    @Excel(name = "事件类型：字典表定义")
    private String type;

    /** 是否接收了通知并处理的标记，默认为0，处理完毕后设置为1 */
    @Excel(name = "是否接收了通知并处理的标记，默认为0，处理完毕后设置为1")
    private Long flag;

    /** 通知的处理方：web端处理的通知，值为web，客户端处理的通知，值为client */
    @Excel(name = "通知的处理方：web端处理的通知，值为web，客户端处理的通知，值为client")
    private String revceiver;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setFlag(Long flag)
    {
        this.flag = flag;
    }

    public Long getFlag()
    {
        return flag;
    }
    public void setRevceiver(String revceiver)
    {
        this.revceiver = revceiver;
    }

    public String getRevceiver()
    {
        return revceiver;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("type", getType())
            .append("flag", getFlag())
            .append("revceiver", getRevceiver())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
