package com.fs.dishes.module.order.entity;

import com.fs.dishes.base.common.BaseEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * 配送主单
 * Created by liuwu on 2018/4/2 0002.
 */
@ToString
@Data
@Table(name = "pls_main_order")
public class PlsMainOrder extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;        //主键ID
    private String orderDesc;//主单描述(名称后+日期 yyyy-MM-dd)
    private String details;    //主单详情
    private Integer status;      //数据状态
    private Integer payStatus;   //支付状态
    private BigDecimal totalAmount;    //主单总金额
    private BigDecimal totalCost;   //主单总成本
    private BigDecimal discountAmount;    //主单总优惠金额

    @Transient
    private Integer priceRadio; //判断是否调整成本价
    @Transient
    private List<PlsOrderFood> list;
}
