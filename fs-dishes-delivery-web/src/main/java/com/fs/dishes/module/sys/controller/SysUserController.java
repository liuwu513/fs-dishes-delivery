package com.fs.dishes.module.sys.controller;

import com.fs.dishes.base.annotations.LogManage;
import com.fs.dishes.base.common.Constant;
import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.utils.PageUtils;
import com.fs.dishes.base.utils.Query;
import com.fs.dishes.base.validator.Assert;
import com.fs.dishes.base.validator.ValidatorUtils;
import com.fs.dishes.base.validator.group.AddGroup;
import com.fs.dishes.base.validator.group.UpdateGroup;
import com.fs.dishes.module.sys.entity.SysUser;
import com.fs.dishes.module.sys.service.SysUserRoleService;
import com.fs.dishes.module.sys.service.SysUserService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 所有用户列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:user:list")
    public ResResult list(@RequestParam Map<String, Object> params) {
        //只有超级管理员，才能查看所有管理员列表
        if (getUserId() != Constant.SUPER_ADMIN) {
            params.put("createUserId", getUserId());
        }
        //查询列表数据
        Query query = new Query(params);
        List<SysUser> userList = sysUserService.queryList(query);
        int total = sysUserService.queryTotal(query);
        PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());
        return ResResult.ok().withData(pageUtil);
    }

    /**
     * 获取登录的用户信息
     */
    @RequestMapping("/info")
    public ResResult info() {
        return ResResult.ok().withData(getUser());
    }

    /**
     * 修改登录用户密码
     */
    @LogManage("修改密码")
    @RequestMapping("/password")
    public ResResult password(String password, String newPassword) {
        Assert.isBlank(newPassword, "新密码不为能空");

        //sha256加密
        password = new Sha256Hash(password, getUser().getSalt()).toHex();
        //sha256加密
        newPassword = new Sha256Hash(newPassword, getUser().getSalt()).toHex();

        //更新密码
        int count = sysUserService.updatePassword(getUserId(), password, newPassword);
        if (count == 0) {
            return ResResult.error(111, "原密码不正确");
        }
        return ResResult.ok();
    }

    /**
     * 用户信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public ResResult info(@PathVariable("userId") String userId) {
        SysUser user = sysUserService.queryObject(userId);

        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);
        return ResResult.ok().withData(user);
    }

    /**
     * 保存用户
     */
    @LogManage("保存用户")
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    public ResResult save(@RequestBody SysUser user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);

        user.setCreateUserId(getUserId());
        sysUserService.save(user);

        return ResResult.ok();
    }

    /**
     * 修改用户
     */
    @LogManage("修改用户")
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    public ResResult update(@RequestBody SysUser user) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);
        user.setCreateUserId(getUserId());
        sysUserService.update(user);
        return ResResult.ok();
    }

    /**
     * 删除用户
     */
    @LogManage("删除用户")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public ResResult delete(@RequestBody String[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return ResResult.error(111, "系统管理员不能删除");
        }
        if (ArrayUtils.contains(userIds, getUserId())) {
            return ResResult.error(111, "当前用户不能删除");
        }
        sysUserService.deleteBatch(userIds);
        return ResResult.ok();
    }
}
