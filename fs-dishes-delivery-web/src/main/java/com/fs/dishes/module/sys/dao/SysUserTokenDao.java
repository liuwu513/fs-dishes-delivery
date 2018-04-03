package com.fs.dishes.module.sys.dao;

import com.fs.dishes.base.annotations.DataRepository;
import com.fs.dishes.module.sys.entity.SysUserToken;

/**
 * 系统用户Token
 * <p>
 * Created by liuwu on 2018/2/28 0028.
 */
@DataRepository
public interface SysUserTokenDao extends BaseDao<SysUserToken> {

    SysUserToken queryByUserId(String userId);

    SysUserToken queryByToken(String token);

}
