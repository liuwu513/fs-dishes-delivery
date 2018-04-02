package com.fs.dishes.module.sys.service;


import com.fs.dishes.module.sys.entity.SysUser;
import com.fs.dishes.module.sys.entity.SysUserToken;

import java.util.Set;

/**
 * shiro相关接口
 * Created by liuwu on 2018/2/28 0028.
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(String userId);

    SysUserToken queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    SysUser queryUser(String userId);
}
