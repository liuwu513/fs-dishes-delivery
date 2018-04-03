package com.fs.dishes.base.service;

import com.fs.dishes.module.sys.entity.SysUser;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.SecurityUtils;

import java.util.Map;

/**
 * 基础业务类
 * Created by liuwu on 2018/4/2 0002.
 */
public class BaseService {

    protected Integer getPageNo(Map<String, Object> params) {
        return MapUtils.getInteger(params, "pageNo", 1);
    }

    protected Integer getPageSize(Map<String, Object> params) {
        return MapUtils.getInteger(params, "pageSize", 10);
    }

    protected SysUser getUser() {
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

    protected String getUserId() {
        return getUser().getUserId();
    }
}
