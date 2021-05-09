package com.ruoyi.device.task;

import com.ruoyi.device.domain.DeviceNotice;
import com.ruoyi.device.service.IDeviceNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *
 */

@Component("deviceTask")
public class DeviceTask {

    @Autowired
    IDeviceNoticeService deviceNoticeService;

    // 定时执行获取设备状态的命令
    public void getDeviceStatus() {
        DeviceNotice notice = new DeviceNotice();
        notice.setFlag(0L);
        notice.setType("scan");
        notice.setRevceiver("client");
        notice.setCreateTime(new Date());
        deviceNoticeService.insertDeviceNotice(notice);
        System.out.println("执行无参方法");
    }
}
