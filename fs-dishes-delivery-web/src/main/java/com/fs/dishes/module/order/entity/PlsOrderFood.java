package com.fs.dishes.module.order.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单商品实体
 * Created by liuwu on 2018/4/2 0002.
 */
@ToString
@Data
@Table(name = "pls_order_food")
public class PlsOrderFood implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id; // 主键ID
    private Long mainOrderId;// 主单ID
    private Long subOrderId; // 子单ID
    private String foodId;     //商品ID
    private BigDecimal number; //数量（以斤进行计算）
    private BigDecimal unitPrice; //单价（元/每斤）
    private BigDecimal amount;    //金额

    @Transient
    private String name;
    @Transient
    private Integer unitId;
}
