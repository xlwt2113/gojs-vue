package com.ruoyi.device.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.device.domain.DeviceEvent;
import com.ruoyi.device.domain.DeviceInfo;
import com.ruoyi.device.domain.DeviceStatus;
import com.ruoyi.device.service.IDeviceEventService;
import com.ruoyi.device.service.IDeviceInfoService;
import com.ruoyi.device.service.IDeviceStatusService;
import com.ruoyi.system.service.ISqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/home")
public class HomePageController extends BaseController {

    @Autowired
    private ISqlService sqlService;

    public AjaxResult home2() {
        HashMap result = new HashMap();
        Long currentDeptId = SecurityUtils.getLoginUser().getUser().getDeptId();
        String filterSql = "";
        if (currentDeptId != 1) {
            // 不是超管都要进行权限过滤
            filterSql = " and (d.dept_id IN (SELECT dept_id FROM sys_dept WHERE dept_id = " + currentDeptId + " OR find_in_set(" + currentDeptId + ", ancestors))) ";
        }
        String infoBaseSql = "select count(*)  from device_info u left join sys_dept d on u.dept_id = d.dept_id ";

        result.put("total", sqlService.selectOne("SELECT count(*) as cnt FROM device_info"));
        result.put("switch", sqlService.selectOne("SELECT count(*) as cnt FROM device_info where device_type = '1' "));
        result.put("router", sqlService.selectOne("SELECT count(*) as cnt FROM device_info where device_type = '2' "));
        result.put("powerSubstation", sqlService.selectOne("SELECT count(*) as cnt FROM device_info where device_type = '3' "));
        result.put("wifi", sqlService.selectOne("SELECT count(*) as cnt FROM device_info where device_type = '4' "));
        result.put("good", sqlService.selectOne("SELECT count(*) as cnt FROM device_status where  device_status = '1'"));
        result.put("common", sqlService.selectOne("SELECT count(*) as cnt FROM device_status where  device_status = '2'"));
        result.put("bad", sqlService.selectOne("SELECT count(*) as cnt FROM device_status where  device_status = '3'"));
        result.put("break", sqlService.selectOne("SELECT count(*) as cnt FROM device_status where  device_status = '4'"));
        result.put("notExist", sqlService.selectOne("SELECT count(*) as cnt FROM device_status where  device_status = '5'"));
        result.put("level1", sqlService.selectOne("select count(*) from device_event where warning_level = 1 and date_format(warning_time,'%Y-%m-%d')  = '" + DateUtils.getDate() + "'"));
        result.put("level2", sqlService.selectOne("select count(*) from device_event where warning_level = 2 and date_format(warning_time,'%Y-%m-%d')  = '" + DateUtils.getDate() + "'"));
        result.put("level3", sqlService.selectOne("select count(*) from device_event where warning_level = 3 and date_format(warning_time,'%Y-%m-%d')  = '" + DateUtils.getDate() + "'"));

        List dayList = new ArrayList();
        List dataList = new ArrayList();
        for (int i = -6; i <= 0; i++) {
            String curentDay = DateUtils.parseDateToStr("yyyy-MM-dd", DateUtils.addDays(new Date(), i));
            dayList.add(curentDay);
            dataList.add(sqlService.selectOne("select count(*) from  device_event where date_format(warning_time,'%Y-%m-%d') = '" + curentDay + "'"));
        }
        result.put("dayList", dayList);
        result.put("dataList", dataList);

        //获取经常访问的拓扑图
        List<Map<String, Object>> list = sqlService.selectList("SELECT topology_id,count(topology_id) as cnt,t2.name  FROM sys_view_count t1 inner join device_topology t2 on t1.topology_id = t2.id  where sys_user_id = " + SecurityUtils.getLoginUser().getUser().getUserId() + "  group by topology_id order by count(topology_id) desc");
        if (list.isEmpty()) {
            list = sqlService.selectList("select id as topology_id,name from  device_topology limit 8");
        }
        result.put("topologyList", list);
        return AjaxResult.success(result);
    }

    @Autowired
    IDeviceInfoService deviceInfoService;

    @Autowired
    IDeviceStatusService deviceStatusService;

    @Autowired
    IDeviceEventService deviceEventService;


    @GetMapping
    public AjaxResult home() {
        HashMap result = new HashMap();
        List<DeviceInfo> infoList = deviceInfoService.selectDeviceInfoList(new DeviceInfo());
        result.put("total", infoList.size());
        int switchCnt = 0 , routerCnt = 0 , powerSubstationCnt = 0,wifiCnt = 0;
        for(DeviceInfo info:infoList){
            if("1".equals(info.getDeviceType())){
                switchCnt++;
            }
            if("2".equals(info.getDeviceType())){
                routerCnt++;
            }
            if("3".equals(info.getDeviceType())){
                powerSubstationCnt++;
            }
            if("4".equals(info.getDeviceType())){
                wifiCnt++;
            }
        }
        result.put("switch",switchCnt);
        result.put("router",routerCnt);
        result.put("powerSubstation",powerSubstationCnt);
        result.put("wifi",wifiCnt);

        int good = 0,common=0,bad =0,breakCnt = 0,notExist=0;
        List<DeviceStatus> statusList = deviceStatusService.selectDeviceStatusList(new DeviceStatus());
        for(DeviceStatus status: statusList){
            if("1".equals(status.getDeviceStatus())){
                good++;
            }
            if("2".equals(status.getDeviceStatus())){
                common++;
            }
            if("3".equals(status.getDeviceStatus())){
                bad++;
            }
            if("4".equals(status.getDeviceStatus())){
                breakCnt++;
            }
            if("5".equals(status.getDeviceStatus())){
                notExist++;
            }
        }
        result.put("good",good);
        result.put("common",common);
        result.put("bad",bad);
        result.put("break",breakCnt);
        result.put("notExist",notExist);

        int level1 =0 ,level2 =0,level3 =0;
        DeviceEvent param = new DeviceEvent();
        param.setUpdateTimeBegin(DateUtils.getDate());
        param.setUpdateTimeEnd(DateUtils.getDate());
        List<DeviceEvent> eventList = deviceEventService.selectDeviceEventList(param);
        for(DeviceEvent event: eventList){
            if("1".equals(event.getWarningLevel())){
                level1++;
            }
            if("2".equals(event.getWarningLevel())){
                level2++;
            }
            if("3".equals(event.getWarningLevel())){
                level3++;
            }
        }
        result.put("level1",level1);
        result.put("level2",level2);
        result.put("level3",level3);

        List dayList = new ArrayList();
        List dataList = new ArrayList();
        for (int i = -6; i <= 0; i++) {
            String currentDay = DateUtils.parseDateToStr("yyyy-MM-dd", DateUtils.addDays(new Date(), i));
            param.setUpdateTimeBegin(currentDay);
            param.setUpdateTimeEnd(currentDay);
            dayList.add(currentDay);
            dataList.add(deviceEventService.selectDeviceEventList(param).size());
        }
        result.put("dayList", dayList);
        result.put("dataList", dataList);

        //获取经常访问的拓扑图
        List<Map<String, Object>> list = sqlService.selectList("SELECT topology_id,count(topology_id) as cnt,t2.name  FROM sys_view_count t1 inner join device_topology t2 on t1.topology_id = t2.id  where sys_user_id = " + SecurityUtils.getLoginUser().getUser().getUserId() + "  group by topology_id order by count(topology_id) desc");
        if (list.isEmpty()) {
            list = sqlService.selectList("select id as topology_id,name from  device_topology limit 8");
        }
        result.put("topologyList", list);
        return AjaxResult.success(result);
    }
}
