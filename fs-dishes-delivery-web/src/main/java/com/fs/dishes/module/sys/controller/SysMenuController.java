package com.fs.dishes.module.sys.controller;

import com.fs.dishes.base.annotations.LogManage;
import com.fs.dishes.base.common.Constant;
import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.exceptions.FDDException;
import com.fs.dishes.module.sys.entity.SysMenu;
import com.fs.dishes.module.sys.service.ShiroService;
import com.fs.dishes.module.sys.service.SysMenuService;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public List<SysMenu> list() {
        List<SysMenu> menuList = sysMenuService.queryList(new HashMap<String, Object>());

        return menuList;
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @RequestMapping("/select")
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
    @RequestMapping("/perms")
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
    @RequestMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public ResResult info(@PathVariable("menuId") Long menuId) {
        SysMenu menu = sysMenuService.queryObject(menuId);
        return ResResult.ok().withData(menu);
    }

    /**
     * 保存
     */
    @LogManage("保存菜单")
    @RequestMapping("/save")
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
    @LogManage("修改菜单")
    @RequestMapping("/update")
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
    @LogManage("删除菜单")
    @RequestMapping("/delete")
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
    @RequestMapping("/user")
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
