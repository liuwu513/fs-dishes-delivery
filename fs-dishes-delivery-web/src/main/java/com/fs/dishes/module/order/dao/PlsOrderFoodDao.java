package com.fs.dishes.module.order.dao;

import com.fs.dishes.base.annotations.DataRepository;
import com.fs.dishes.module.order.entity.OrderFoodVo;
import com.fs.dishes.module.order.entity.PlsOrderFood;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 食品订单关联
 * Created by liuwu on 2018/4/2 0002.
 */
@DataRepository
public interface PlsOrderFoodDao extends Mapper<PlsOrderFood> {

    List<PlsOrderFood> queryList(Map<String, Object> params);

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    Boolean batchAdd(@Param("list") List<PlsOrderFood> list);

    /**
     * 删除操作
     *
     * @param idList
     * @return
     */
    Boolean batchDel(@Param("idList") List<String> idList);

    /**
     * 根据查询条件获取子单ID集合
     *
     * @param params
     * @return
     */
    List<String> querySubByCondition(Map<String, Object> params);

    /**
     * 根据条件查询到被使用食品ID集合
     *
     * @param params
     * @return
     */
    List<String> queryFoodByCondition(Map<String, Object> params);

    /**
     * 根据主单ID获取主单商品价格列表
     * @param mainOrderId
     * @return
     */
    List<OrderFoodVo> queryPriceByMainOrderId(@Param("mainOrderId") Long mainOrderId);

    /**
     * 根据条件获取食品下单信息
     * @param params
     * @return
     */
    List<OrderFoodVo> queryFoodOrderByCondition(Map<String,Object> params);
}
