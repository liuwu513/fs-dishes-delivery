package com.fs.dishes.module.order.dao;

import com.fs.dishes.base.annotations.DataRepository;
import com.fs.dishes.module.order.entity.PlsSubOrder;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 子单持久层
 * Created by liwu on 2018/4/2 0002.
 */
@DataRepository
public interface PlsSubOrderDao extends Mapper<PlsSubOrder>{

    /**
     * 查询订单列表
     * @param params
     * @return
     */
    List<PlsSubOrder> queryList(Map<String, Object> params);
}
