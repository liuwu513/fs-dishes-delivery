package com.fs.dishes.module.sys.controller;

import com.fs.dishes.base.annotations.LogManage;
import com.fs.dishes.base.common.Constant;
import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.controller.AbstractController;
import com.fs.dishes.base.exceptions.FDDException;
import com.fs.dishes.module.sys.entity.SysMenu;
import com.fs.dishes.module.sys.service.ShiroService;
import com.fs.dishes.module.sys.service.SysMenuService;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 系统菜单
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午9:58:15
 */
@Api(description = "系统菜单接口")
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private ShiroService shiroService;

    /**
     * 所有菜单列表
     */
    @ApiOperation(value = "系统菜单列表接口", notes = "系统菜单列表接口", httpMethod = "POST")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @RequiresPermissions("sys:menu:list")
    public List<SysMenu> list() {
        List<SysMenu> menuList = sysMenuService.queryList(new HashMap<String, Object>());

        return menuList;
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @ApiOperation(value = "系统菜单选择", notes = "系统菜单选择", httpMethod = "GET")
    @RequestMapping(value = "/select",method = RequestMethod.GET)
    @RequiresPermissions("sys:menu:select")
    public ResResult select() {
        //查询列表数据
        List<SysMenu> menuList = sysMenuService.queryNotButtonList();

        //添加顶级菜单
        SysMenu root = new SysMenu();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);
        return ResResult.ok().withData(menuList);
    }

    /**
     * 角色授权菜单
     */
    @ApiOperation(value = "角色授权菜单", notes = "角色授权菜单", httpMethod = "GET")
    @RequestMapping(value = "/perms",method = RequestMethod.GET)
    @RequiresPermissions("sys:menu:perms")
    public ResResult perms() {
        //查询列表数据
        List<SysMenu> menuList = null;

        //只有超级管理员，才能查看所有管理员列表
        if (getUserId() == Constant.SUPER_ADMIN) {
            menuList = sysMenuService.queryList(new HashMap<String, Object>());
        } else {
            menuList = sysMenuService.queryUserList(getUserId());
        }
        return ResResult.ok().withData(menuList);
    }

    /**
     * 菜单信息
     */
    @ApiOperation(value = "菜单信息", notes = "菜单信息", httpMethod = "GET")
    @RequestMapping(value = "/info/{menuId}",method = RequestMethod.GET)
    @RequiresPermissions("sys:menu:info")
    public ResResult info(@PathVariable("menuId") Long menuId) {
        SysMenu menu = sysMenuService.queryObject(menuId);
        return ResResult.ok().withData(menu);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存菜单", notes = "保存菜单", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "父菜单ID", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "parentName", value = "父菜单名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "菜单名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "url", value = "地址", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "perms", value = "权限编码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "icon", value = "菜单图标", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "orderNum", value = "排序编号", dataType = "Integer", paramType = "query"),
    })
    @LogManage("保存菜单")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @RequiresPermissions("sys:menu:save")
    public ResResult save(@RequestBody SysMenu menu) {
        //数据校验
        verifyForm(menu);
        sysMenuService.save(menu);
        return ResResult.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改菜单", notes = "修改菜单", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "菜单ID", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "parentId", value = "父菜单ID", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "parentName", value = "父菜单名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "菜单名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "url", value = "地址", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "perms", value = "权限编码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "icon", value = "菜单图标", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "orderNum", value = "排序编号", dataType = "Integer", paramType = "query"),
    })
    @LogManage("修改菜单")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @RequiresPermissions("sys:menu:update")
    public ResResult update(@RequestBody SysMenu menu) {
        //数据校验
        verifyForm(menu);
        sysMenuService.update(menu);
        return ResResult.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除菜单", notes = "删除菜单", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "菜单ID", dataType = "Long", paramType = "query")
    })
    @LogManage("删除菜单")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @RequiresPermissions("sys:menu:delete")
    public ResResult delete(long menuId) {
        if (menuId <= 30) {
            return ResResult.error(111, "系统菜单，不能删除");
        }
        //判断是否有子菜单或按钮
        List<SysMenu> menuList = sysMenuService.queryListParentId(menuId);
        if (menuList.size() > 0) {
            return ResResult.error(111, "请先删除子菜单或按钮");
        }
        sysMenuService.deleteBatch(new Long[]{menuId});
        return ResResult.ok();
    }

    /**
     * 用户菜单列表
     */
    @ApiOperation(value = "用户菜单信息", notes = "用户菜单信息", httpMethod = "GET")
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public ResResult user() {
        List<SysMenu> menuList = sysMenuService.getUserMenuList(getUserId());
        Set<String> permissions = shiroService.getUserPermissions(getUserId());
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("menuList", menuList);
        resultMap.put("permissions", permissions);
        return ResResult.ok().withData(resultMap);
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysMenu menu) {
        if (StringUtils.isBlank(menu.getName())) {
            throw new FDDException("菜单名称不能为空");
        }

        if (menu.getParentId() == null) {
            throw new FDDException("上级菜单不能为空");
        }

        //菜单
        if (menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (StringUtils.isBlank(menu.getUrl())) {
                throw new FDDException("菜单URL不能为空");
            }
        }

        //上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();
        if (menu.getParentId() != 0) {
            SysMenu parentMenu = sysMenuService.queryObject(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if (menu.getType() == Constant.MenuType.CATALOG.getValue() ||
                menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (parentType != Constant.MenuType.CATALOG.getValue()) {
                throw new FDDException("上级菜单只能为目录类型");
            }
            return;
        }

        //按钮
        if (menu.getType() == Constant.MenuType.BUTTON.getValue()) {
            if (parentType != Constant.MenuType.MENU.getValue()) {
                throw new FDDException("上级菜单只能为菜单类型");
            }
            return;
        }
    }
}
