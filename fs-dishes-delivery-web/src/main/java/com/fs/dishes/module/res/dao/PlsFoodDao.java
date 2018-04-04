package com.fs.dishes.module.res.dao;

import com.fs.dishes.base.annotations.DataRepository;
import com.fs.dishes.module.res.entity.PlsFood;
import org.apache.ibatis.annotations.Param;
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
     * 根据条件获取种类ID集合
     * @param params
     * @return
     */
    List<Long> querySpeciesByCondition(Map<String,Object> params);

    /**
     * 查询食品是否已存在
     * @param food
     * @return
     */
    Boolean exists(PlsFood food);

    /**
     * 删除操作
     * @param idList
     * @return
     */
    Boolean batchDel(@Param("idList") List<String> idList, @Param("status") Integer status);
}
