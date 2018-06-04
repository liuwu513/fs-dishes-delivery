package com.fs.dishes.module.order.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品主单下单统计
 * Created by liuwu on 2018/6/4 0004.
 */
@ToString
@Data
public class OrderFoodVo implements Serializable{

    private Long foodId; //食品ID

    private BigDecimal unitPrice; //单价

    private BigDecimal costPrice;//成本价

    private BigDecimal number; //食品下单量
}
