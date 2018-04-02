package com.fs.dishes.module.sys.dao;

import com.fs.dishes.base.annotations.DataRepository;
import com.fs.dishes.module.sys.entity.SysMenu;

import java.util.List;

/**
 * 菜单管理
 *
 * Created by liuwu on 2018/2/28 0028.
 */
@DataRepository
public interface SysMenuDao extends BaseDao<SysMenu> {

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId 父菜单ID
     */
    List<SysMenu> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenu> queryNotButtonList();

    /**
     * 查询用户的权限列表
     */
    List<SysMenu> queryUserList(String userId);
}
