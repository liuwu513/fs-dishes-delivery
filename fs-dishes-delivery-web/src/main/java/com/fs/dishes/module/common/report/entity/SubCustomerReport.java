package com.fs.dishes.module.common.report.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 客户下单量
 * Created by liuwu on 2018/4/25 0025.
 */
@ToString
@Data
public class SubCustomerReport implements Serializable{


    private Long foodId;

    private Long customerId;

    private String customerName;

    private BigDecimal number;

    private BigDecimal totalAmount;
}
