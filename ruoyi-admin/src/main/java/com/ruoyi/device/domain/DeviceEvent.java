package com.ruoyi.device.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备事件表对象 device_event
 *
 * @author 王涛
 * @date 2021-05-09
 */
public class DeviceEvent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private DeviceInfo deviceInfo;

    /** $column.columnComment */
    private Long id;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private Long deviceId;

    /** 告警类型 */
    @Excel(name = "告警类型")
    private Long warningLevel;

    /** 告警内容 */
    @Excel(name = "告警内容")
    private String warningContent;

    /** 告警时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "告警时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date warningTime;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setDeviceId(Long deviceId)
    {
        this.deviceId = deviceId;
    }

    public Long getDeviceId()
    {
        return deviceId;
    }
    public void setWarningLevel(Long warningLevel)
    {
        this.warningLevel = warningLevel;
    }

    public Long getWarningLevel()
    {
        return warningLevel;
    }
    public void setWarningContent(String warningContent)
    {
        this.warningContent = warningContent;
    }

    public String getWarningContent()
    {
        return warningContent;
    }
    public void setWarningTime(Date warningTime)
    {
        this.warningTime = warningTime;
    }

    public Date getWarningTime()
    {
        return warningTime;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deviceId", getDeviceId())
            .append("warningLevel", getWarningLevel())
            .append("warningContent", getWarningContent())
            .append("warningTime", getWarningTime())
            .toString();
    }
}
