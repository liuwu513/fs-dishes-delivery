package com.fs.dishes.module.order.dao;

import com.fs.dishes.base.annotations.DataRepository;
import com.fs.dishes.module.order.entity.PlsMainOrder;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 主单持久层
 * Created by liwu on 2018/4/2 0002.
 */
@DataRepository
public interface PlsMainOrderDao extends Mapper<PlsMainOrder> {

    /**
     * 查询订单列表
     *
     * @param params
     * @return
     */
    List<PlsMainOrder> queryList(Map<String, Object> params);

    /**
     * 更新主单付款状态
     *
     * @param mainOrderId
     * @return
     */
    Boolean updatePayStatusById(@Param("mainOrderId") Long mainOrderId,
                                @Param("payStatus") Integer payStatus);

    /**
     * 批量伪删除
     *
     * @param idList
     * @param status
     * @return
     */
    Boolean batchDel(@Param("idList") List<Long> idList, @Param("status") Integer status);
}
