package com.fs.dishes.module.sys.service;


import com.fs.dishes.module.sys.entity.SysConfig;

import java.util.List;
import java.util.Map;

/**
 * 系统配置信息
 * Created by liuwu on 2018/2/28 0028.
 */
public interface SysConfigService {

    /**
     * 保存配置信息
     */
    public void save(SysConfig config);

    /**
     * 更新配置信息
     */
    public void update(SysConfig config);

    /**
     * 根据key，更新value
     */
    public void updateValueByKey(String key, String value);

    /**
     * 删除配置信息
     */
    public void deleteBatch(Long[] ids);

    /**
     * 获取List列表
     */
    public List<SysConfig> queryList(Map<String, Object> map);

    /**
     * 获取总记录数
     */
    public int queryTotal(Map<String, Object> map);

    public SysConfig queryObject(Long id);

    /**
     * 根据key，获取配置的value值
     *
     * @param key          key
     * @param defaultValue 缺省值
     */
    public String getValue(String key, String defaultValue);

    /**
     * 根据key，获取value的Object对象
     *
     * @param key   key
     * @param clazz Object对象
     */
    public <T> T getConfigObject(String key, Class<T> clazz);

}
