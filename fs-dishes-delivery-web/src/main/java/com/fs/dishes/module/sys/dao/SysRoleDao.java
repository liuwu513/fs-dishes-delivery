package com.fs.dishes.module.sys.dao;

import com.fs.dishes.base.annotations.DataRepository;
import com.fs.dishes.module.sys.entity.SysRole;

import java.util.List;

/**
 * 角色管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:33:33
 */
@DataRepository
public interface SysRoleDao extends BaseDao<SysRole> {
	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(String createUserId);
}
