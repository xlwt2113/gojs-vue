package com.ruoyi.device.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.SysConfig;
import com.ruoyi.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class SoftVersionController extends BaseController {

    @Autowired
    ISysConfigService configService;

    @GetMapping("/getSoftVersion")
    public AjaxResult version(HttpServletRequest request) {
        String config = configService.selectConfigByKey("softVersion");
        HashMap<String,String> result = new HashMap<>();
        result.put("softVersion",config);
        return AjaxResult.success(result);
    }
}
