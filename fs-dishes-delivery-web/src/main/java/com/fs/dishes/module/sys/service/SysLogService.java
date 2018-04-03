package com.fs.dishes.module.sys.service;


import com.fs.dishes.module.sys.entity.SysLog;

import java.util.List;
import java.util.Map;

/**
 * 系统日志
 * <p>
 * Created by liuwu on 2018/2/28 0028.
 */
public interface SysLogService {

    SysLog queryObject(Long id);

    List<SysLog> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(SysLog sysLog);

    void update(SysLog sysLog);

    void delete(Long id);

    void deleteBatch(Long[] ids);
}
