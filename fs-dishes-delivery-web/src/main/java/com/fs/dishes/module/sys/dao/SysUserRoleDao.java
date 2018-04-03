package com.fs.dishes.module.sys.dao;

import com.fs.dishes.base.annotations.DataRepository;
import com.fs.dishes.module.sys.entity.SysUserRole;

import java.util.List;

/**
 * 用户与角色对应关系
 * <p>
 * Created by liuwu on 2018/2/28 0028.
 */
@DataRepository
public interface SysUserRoleDao extends BaseDao<SysUserRole> {

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Long> queryRoleIdList(String userId);
}
