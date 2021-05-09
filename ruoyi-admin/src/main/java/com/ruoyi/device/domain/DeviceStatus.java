package com.ruoyi.device.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备状态表对象 device_status
 *
 * @author 王涛
 * @date 2021-05-09
 */
public class DeviceStatus extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private DeviceInfo deviceInfo;

    private String updateTimeBegin,updateTimeEnd;

    /** $column.columnComment */
    private Long id;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private Long deviceId;

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getUpdateTimeBegin() {
        return updateTimeBegin;
    }

    public void setUpdateTimeBegin(String updateTimeBegin) {
        this.updateTimeBegin = updateTimeBegin;
    }

    public String getUpdateTimeEnd() {
        return updateTimeEnd;
    }

    public void setUpdateTimeEnd(String updateTimeEnd) {
        this.updateTimeEnd = updateTimeEnd;
    }

    /** 设备实时状态 */
    @Excel(name = "设备实时状态")
    private Long deviceStatus;

    /** ping平均时延 */
    @Excel(name = "ping平均时延")
    private Long pingAvg;

    /** 最小时延 */
    @Excel(name = "最小时延")
    private Long pingMin;

    /** 最大时延 */
    @Excel(name = "最大时延")
    private Long pingMax;

    /** 丢包率 */
    @Excel(name = "丢包率")
    private Long pingLoss;

    /** 备注 */
    @Excel(name = "备注")
    private String note;

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
    public void setDeviceStatus(Long deviceStatus)
    {
        this.deviceStatus = deviceStatus;
    }

    public Long getDeviceStatus()
    {
        return deviceStatus;
    }
    public void setPingAvg(Long pingAvg)
    {
        this.pingAvg = pingAvg;
    }

    public Long getPingAvg()
    {
        return pingAvg;
    }
    public void setPingMin(Long pingMin)
    {
        this.pingMin = pingMin;
    }

    public Long getPingMin()
    {
        return pingMin;
    }
    public void setPingMax(Long pingMax)
    {
        this.pingMax = pingMax;
    }

    public Long getPingMax()
    {
        return pingMax;
    }
    public void setPingLoss(Long pingLoss)
    {
        this.pingLoss = pingLoss;
    }

    public Long getPingLoss()
    {
        return pingLoss;
    }
    public void setNote(String note)
    {
        this.note = note;
    }

    public String getNote()
    {
        return note;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deviceId", getDeviceId())
            .append("deviceStatus", getDeviceStatus())
            .append("pingAvg", getPingAvg())
            .append("pingMin", getPingMin())
            .append("pingMax", getPingMax())
            .append("pingLoss", getPingLoss())
            .append("updateTime", getUpdateTime())
            .append("note", getNote())
            .toString();
    }
}
