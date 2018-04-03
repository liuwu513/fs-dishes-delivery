package com.fs.dishes.module.sys.dao;

import com.fs.dishes.base.annotations.DataRepository;
import com.fs.dishes.module.sys.entity.SysConfig;
import org.apache.ibatis.annotations.Param;

/**
 * 系统配置信息
 * <p>
 * Created by liuwu on 2018/2/28 0028.
 */
@DataRepository
public interface SysConfigDao extends BaseDao<SysConfig> {

    /**
     * 根据key，查询value
     */
    String queryByKey(String paramKey);

    /**
     * 根据key，更新value
     */
    int updateValueByKey(@Param("key") String key, @Param("value") String value);

}
