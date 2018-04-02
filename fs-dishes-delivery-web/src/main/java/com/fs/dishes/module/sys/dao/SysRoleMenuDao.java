package com.fs.dishes.module.sys.dao;

import com.fs.dishes.base.annotations.DataRepository;
import com.fs.dishes.module.sys.entity.SysRoleMenu;

import java.util.List;

/**
 * 角色与菜单对应关系
 * <p>
 * Created by liuwu on 2018/2/28 0028.
 */
@DataRepository
public interface SysRoleMenuDao extends BaseDao<SysRoleMenu> {

    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Long> queryMenuIdList(Long roleId);
}
