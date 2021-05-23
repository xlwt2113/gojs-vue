package com.ruoyi.device.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
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

    @GetMapping
    public AjaxResult home(){
        HashMap result = new HashMap();
        result.put("total",sqlService.selectOne("SELECT count(*) as cnt FROM device_info"));
        result.put("switch",sqlService.selectOne("SELECT count(*) as cnt FROM device_info where device_type = '1' "));
        result.put("router",sqlService.selectOne("SELECT count(*) as cnt FROM device_info where device_type = '2' "));
        result.put("powerSubstation",sqlService.selectOne("SELECT count(*) as cnt FROM device_info where device_type = '3' "));
        result.put("wifi",sqlService.selectOne("SELECT count(*) as cnt FROM device_info where device_type = '4' "));
        result.put("good",sqlService.selectOne("SELECT count(*) as cnt FROM device_status where  device_status = '1'"));
        result.put("common",sqlService.selectOne("SELECT count(*) as cnt FROM device_status where  device_status = '2'"));
        result.put("bad",sqlService.selectOne("SELECT count(*) as cnt FROM device_status where  device_status = '3'"));
        result.put("break",sqlService.selectOne("SELECT count(*) as cnt FROM device_status where  device_status = '4'"));
        result.put("notExist",sqlService.selectOne("SELECT count(*) as cnt FROM device_status where  device_status = '5'"));
        result.put("level1",sqlService.selectOne("select count(*) from device_event where warning_level = 1 and date_format(warning_time,'%Y-%m-%d')  = '"+ DateUtils.getDate() +"'"));
        result.put("level2",sqlService.selectOne("select count(*) from device_event where warning_level = 2 and date_format(warning_time,'%Y-%m-%d')  = '"+ DateUtils.getDate() +"'"));
        result.put("level3",sqlService.selectOne("select count(*) from device_event where warning_level = 3 and date_format(warning_time,'%Y-%m-%d')  = '"+ DateUtils.getDate() +"'"));

        List dayList = new ArrayList();
        List dataList = new ArrayList();
        for(int i = -6;i<=0;i++){
            String curentDay = DateUtils.parseDateToStr("yyyy-MM-dd",DateUtils.addDays(new Date(),i));
            dayList.add(curentDay);
            dataList.add(sqlService.selectOne("select count(*) from  device_event where date_format(warning_time,'%Y-%m-%d') = '"+curentDay+"'"));
        }
        result.put("dayList",dayList);
        result.put("dataList",dataList);

        //获取经常访问的拓扑图
        List<Map<String, Object>> list = sqlService.selectList("SELECT topology_id,count(topology_id) as cnt,t2.name  FROM sys_view_count t1 inner join device_topology t2 on t1.topology_id = t2.id  where sys_user_id = "+ SecurityUtils.getLoginUser().getUser().getUserId() +"  group by topology_id order by count(topology_id) desc");
        if(list.isEmpty()){
            list = sqlService.selectList("select id as topology_id,name from  device_topology limit 8");
        }
        result.put("topologyList",list);
        return AjaxResult.success(result);
    }
}
