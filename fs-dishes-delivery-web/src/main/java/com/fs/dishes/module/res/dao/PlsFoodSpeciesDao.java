package com.fs.dishes.module.res.dao;

import com.fs.dishes.base.annotations.DataRepository;
import com.fs.dishes.module.res.entity.PlsFood;
import com.fs.dishes.module.res.entity.PlsFoodSpecies;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 食品品种持久层
 * Created by liuwu on 2018/4/2 0002.
 */
@DataRepository
public interface PlsFoodSpeciesDao extends Mapper<PlsFoodSpecies>{
    /**
     * 查询食品种类列表
     * @param params
     * @return
     */
    List<PlsFoodSpecies> queryList(Map<String,Object> params);

    /**
     * 查询食品种类是否已存在
     * @param species
     * @return
     */
    Boolean exists(PlsFoodSpecies species);
}
