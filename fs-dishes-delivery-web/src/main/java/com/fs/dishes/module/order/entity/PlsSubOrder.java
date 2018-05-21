package com.fs.dishes.module.order.entity;

import com.fs.dishes.base.common.BaseEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * 配送子单
 * Created by liuwu on 2018/4/2 0002.
 */
@ToString
@Data
@Table(name = "pls_sub_order")
public class PlsSubOrder extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;        //主键ID
    private String name ;     //子单名称
    private Long customerId;	//客户ID
    private Long mainOrderId;	//主单ID
    private BigDecimal totalAmount;	//子单总金额
    private BigDecimal totalCost;  //子单成本
    private BigDecimal discountAmount;	//子单总优惠金额
    private Integer payStatus;    //付款状态
    private Integer status;        //状态
    private String remarks;       //备注

    @Transient
    private List<PlsOrderFood> list;

    @Transient
    private String customerName;
}
