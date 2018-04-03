package com.fs.dishes.module.sys.controller;

import com.fs.dishes.base.annotations.LogManage;
import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.utils.PageUtils;
import com.fs.dishes.base.utils.Query;
import com.fs.dishes.base.validator.ValidatorUtils;
import com.fs.dishes.module.sys.entity.SysConfig;
import com.fs.dishes.module.sys.service.SysConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统配置信息
 *
 * Created by liuwu on 2018/2/28 0028.
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {
    @Autowired
    protected SysConfigService sysConfigService;

    /**
     * 所有配置列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:config:list")
    public ResResult list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<SysConfig> configList = sysConfigService.queryList(query);
        int total = sysConfigService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(configList, total, query.getLimit(), query.getPage());

        return ResResult.ok().withData(pageUtil);
    }


    /**
     * 配置信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:config:info")
    public ResResult info(@PathVariable("id") Long id) {
        SysConfig config = sysConfigService.queryObject(id);
        return ResResult.ok().withData(config);
    }

    /**
     * 保存配置
     */
    @LogManage("保存配置")
    @RequestMapping("/save")
    @RequiresPermissions("sys:config:save")
    public ResResult save(@RequestBody SysConfig config) {
        ValidatorUtils.validateEntity(config);
        sysConfigService.save(config);
        return ResResult.ok();
    }

    /**
     * 修改配置
     */
    @LogManage("修改配置")
    @RequestMapping("/update")
    @RequiresPermissions("sys:config:update")
    public ResResult update(@RequestBody SysConfig config) {
        ValidatorUtils.validateEntity(config);
        sysConfigService.update(config);
        return ResResult.ok();
    }

    /**
     * 删除配置
     */
    @LogManage("删除配置")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:config:delete")
    public ResResult delete(@RequestBody Long[] ids) {
        sysConfigService.deleteBatch(ids);
        return ResResult.ok();
    }

}
