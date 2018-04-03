package com.fs.dishes.module.sys.service;

import java.util.List;


/**
 * 用户与角色对应关系
 * 
 * Created by liuwu on 2018/2/28 0028.
 */
public interface SysUserRoleService {
	
	void saveOrUpdate(String userId, List<Long> roleIdList);
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(String userId);
	
	void delete(String userId);
}
