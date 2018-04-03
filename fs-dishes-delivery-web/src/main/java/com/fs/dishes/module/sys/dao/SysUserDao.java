package com.fs.dishes.module.sys.dao;

import com.fs.dishes.base.annotations.DataRepository;
import com.fs.dishes.module.sys.entity.SysUser;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * <p>
 * Created by liuwu on 2018/2/28 0028.
 */
@DataRepository
public interface SysUserDao extends BaseDao<SysUser> {

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户ID
     */
    List<String> queryAllPerms(String userId);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(String userId);

    /**
     * 根据用户名，查询系统用户
     */
    SysUser queryByUserName(String username);

    /**
     * 修改密码
     */
    int updatePassword(Map<String, Object> map);
}
