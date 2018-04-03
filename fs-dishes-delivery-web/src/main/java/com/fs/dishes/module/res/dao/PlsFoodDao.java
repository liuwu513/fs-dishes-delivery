package com.fs.dishes.module.res.dao;

import com.fs.dishes.base.annotations.DataRepository;
import com.fs.dishes.module.res.entity.PlsFood;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 食品持久层
 * Created by liuwu on 2018/4/2 0002.
 */
@DataRepository
public interface PlsFoodDao extends Mapper<PlsFood>{

    /**
     * 查询食品列表
     * @param params
     * @return
     */
    List<PlsFood> queryList(Map<String,Object> params);

    /**
     * 查询食品是否已存在
     * @param food
     * @return
     */
    Boolean exists(PlsFood food);
}
