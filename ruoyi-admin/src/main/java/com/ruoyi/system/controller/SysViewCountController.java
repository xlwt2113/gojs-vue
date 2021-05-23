package com.ruoyi.system.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysViewCount;
import com.ruoyi.system.service.ISysViewCountService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 访问次数统计Controller
 * 
 * @author wanngtao
 * @date 2021-05-20
 */
@RestController
@RequestMapping("/system/vslog")
public class SysViewCountController extends BaseController
{
    @Autowired
    private ISysViewCountService sysViewCountService;

    /**
     * 查询访问次数统计列表
     */
    @PreAuthorize("@ss.hasPermi('system:vslog:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysViewCount sysViewCount)
    {
        startPage();
        List<SysViewCount> list = sysViewCountService.selectSysViewCountList(sysViewCount);
        return getDataTable(list);
    }

    /**
     * 导出访问次数统计列表
     */
    @PreAuthorize("@ss.hasPermi('system:vslog:export')")
    @Log(title = "访问次数统计", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysViewCount sysViewCount)
    {
        List<SysViewCount> list = sysViewCountService.selectSysViewCountList(sysViewCount);
        ExcelUtil<SysViewCount> util = new ExcelUtil<SysViewCount>(SysViewCount.class);
        return util.exportExcel(list, "访问次数统计数据");
    }

    /**
     * 获取访问次数统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:vslog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysViewCountService.selectSysViewCountById(id));
    }

    /**
     * 新增访问次数统计
     */
    @PreAuthorize("@ss.hasPermi('system:vslog:add')")
    @Log(title = "访问次数统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysViewCount sysViewCount)
    {
        return toAjax(sysViewCountService.insertSysViewCount(sysViewCount));
    }

    /**
     * 修改访问次数统计
     */
    @PreAuthorize("@ss.hasPermi('system:vslog:edit')")
    @Log(title = "访问次数统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysViewCount sysViewCount)
    {
        return toAjax(sysViewCountService.updateSysViewCount(sysViewCount));
    }

    /**
     * 删除访问次数统计
     */
    @PreAuthorize("@ss.hasPermi('system:vslog:remove')")
    @Log(title = "访问次数统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysViewCountService.deleteSysViewCountByIds(ids));
    }
}
