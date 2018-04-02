package com.fs.dishes.module.sys.service;

import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.module.sys.entity.SysUserToken;

/**
 * 用户Token
 * Created by liuwu on 2018/2/28 0028.
 */
public interface SysUserTokenService {

    SysUserToken queryByUserId(String userId);

    SysUserToken queryByToken(String token);

    void save(SysUserToken token);

    void update(SysUserToken token);

    /**
     * 生成token
     *
     * @param userId 用户ID
     */
    ResResult createToken(String userId);
}
