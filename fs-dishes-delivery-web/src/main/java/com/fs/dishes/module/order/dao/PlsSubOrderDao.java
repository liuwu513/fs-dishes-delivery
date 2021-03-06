package com.fs.dishes.module.order.dao;

import com.fs.dishes.base.annotations.DataRepository;
import com.fs.dishes.module.order.entity.PlsSubOrder;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 子单持久层
 * Created by liwu on 2018/4/2 0002.
 */
@DataRepository
public interface PlsSubOrderDao extends Mapper<PlsSubOrder> {

    /**
     * 查询订单列表
     *
     * @param params
     * @return
     */
    List<PlsSubOrder> queryList(Map<String, Object> params);

    /**
     * 查询主单下所有分单的支付状态
     * @param mainOrderId
     * @return
     */
    List<Integer> queryAllPayStatus(@Param("mainOrderId") Long mainOrderId);

    /**
     * 根据客户ID获取客户最新下的子单ID
     * @param customerId
     * @return
     */
    Long getSubIdByCustomerId(@Param("customerId") Long customerId);

    /**
     * 批量更新付款状态
     *
     * @param params
     * @return
     */
    Boolean batchUpdatePayStatus(Map<String, Object> params);

    /**
     * 根据条件查询存在的主单
     *
     * @param params
     * @return
     */
    List<String> queryMainByCondition(Map<String, Object> params);

    /**
     * 根据客户ID搜索是否客户已有下单
     *
     * @param params
     * @return
     */
    List<Long> queryCustomerByCondition(Map<String, Object> params);

    /**
     * 批量伪删除
     *
     * @param idList
     * @param status
     * @return
     */
    Boolean batchDel(@Param("idList") List<Long> idList, @Param("status") Integer status);
}
