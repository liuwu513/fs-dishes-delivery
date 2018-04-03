package com.fs.dishes.module.sys.controller;

import com.fs.dishes.base.annotations.LogManage;
import com.fs.dishes.base.common.Constant;
import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.utils.PageUtils;
import com.fs.dishes.base.utils.Query;
import com.fs.dishes.base.validator.ValidatorUtils;
import com.fs.dishes.module.sys.entity.SysRole;
import com.fs.dishes.module.sys.service.SysRoleMenuService;
import com.fs.dishes.module.sys.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月8日 下午2:18:33
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 角色列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:role:list")
    public ResResult list(@RequestParam Map<String, Object> params) {
        //如果不是超级管理员，则只查询自己创建的角色列表
        if (getUserId() != Constant.SUPER_ADMIN) {
            params.put("createUserId", getUserId());
        }

        //查询列表数据
        Query query = new Query(params);
        List<SysRole> list = sysRoleService.queryList(query);
        int total = sysRoleService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());

        return ResResult.ok().withData(pageUtil);
    }

    /**
     * 角色列表
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:role:select")
    public ResResult select() {
        Map<String, Object> map = new HashMap<>();
        //如果不是超级管理员，则只查询自己所拥有的角色列表
        if (getUserId() != Constant.SUPER_ADMIN) {
            map.put("createUserId", getUserId());
        }
        List<SysRole> list = sysRoleService.queryList(map);
        return ResResult.ok().withData(list);
    }

    /**
     * 角色信息
     */
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public ResResult info(@PathVariable("roleId") Long roleId) {
        SysRole role = sysRoleService.queryObject(roleId);

        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);
        return ResResult.ok().withData(role);
    }

    /**
     * 保存角色
     */
    @LogManage("保存角色")
    @RequestMapping("/save")
    @RequiresPermissions("sys:role:save")
    public ResResult save(@RequestBody SysRole role) {
        ValidatorUtils.validateEntity(role);
        role.setCreateUserId(getUserId());
        sysRoleService.save(role);
        return ResResult.ok();
    }

    /**
     * 修改角色
     */
    @LogManage("修改角色")
    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    public ResResult update(@RequestBody SysRole role) {
        ValidatorUtils.validateEntity(role);
        role.setCreateUserId(getUserId());
        sysRoleService.update(role);
        return ResResult.ok();
    }

    /**
     * 删除角色
     */
    @LogManage("删除角色")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public ResResult delete(@RequestBody Long[] roleIds) {
        sysRoleService.deleteBatch(roleIds);
        return ResResult.ok();
    }
}
