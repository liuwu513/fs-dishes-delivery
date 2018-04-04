package com.fs.dishes.module.sys.controller;

import com.fs.dishes.base.annotations.LogManage;
import com.fs.dishes.base.common.Constant;
import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.controller.AbstractController;
import com.fs.dishes.base.utils.PageUtils;
import com.fs.dishes.base.utils.Query;
import com.fs.dishes.base.validator.ValidatorUtils;
import com.fs.dishes.module.sys.entity.SysRole;
import com.fs.dishes.module.sys.service.SysRoleMenuService;
import com.fs.dishes.module.sys.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(description = "系统角色接口")
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
    @ApiOperation(value = "系统角色列表接口", notes = "系统角色列表接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "角色名称", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/list",method = RequestMethod.POST)
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
    @ApiOperation(value = "角色列表", notes = "角色列表", httpMethod = "GET")
    @RequestMapping(value = "/select",method = RequestMethod.GET)
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
    @ApiOperation(value = "角色详情", notes = "角色详情", httpMethod = "GET")
    @RequestMapping(value = "/info/{roleId}",method = RequestMethod.GET)
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
    @ApiOperation(value = "保存角色信息", notes = "保存角色信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "角色名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "menuIdList", value = "菜单ID集合", dataType = "String", paramType = "query")
    })
    @LogManage("保存角色")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
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
    @ApiOperation(value = "保存角色信息", notes = "保存角色信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "roleName", value = "角色名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "menuIdList", value = "菜单ID集合", paramType = "query")
    })
    @LogManage("修改角色")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
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
    @ApiOperation(value = "删除角色", notes = "删除角色", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleIds", value = "角色ID集合", paramType = "query")
    })
    @LogManage("删除角色")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @RequiresPermissions("sys:role:delete")
    public ResResult delete(@RequestBody Long[] roleIds) {
        sysRoleService.deleteBatch(roleIds);
        return ResResult.ok();
    }
}
