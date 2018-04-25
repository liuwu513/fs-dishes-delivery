package com.fs.dishes.module.common.report.entity;

import lombok.Data;
import lombok.ToString;
import net.sf.jasperreports.engine.JRDataSource;

import java.io.Serializable;
import java.math.BigDecimal;

import java.util.List;

/**
 * 主单报表实体
 * Created by liuwu on 2018/4/25 0025.
 */
@ToString
@Data
public class MainOrderReport implements Serializable{

    //主单ID
    private Long mainOrderId;
    //食品ID
    private Long foodId;
    //单位ID
    private Integer unitId;
    //序号
    private Integer index;
    //存货名称
    private String foodName;
    //订购数量
    private BigDecimal number;
    //采购单价
    private BigDecimal unitPrice;
    //单位
    private String unitName;
    //价税总合计
    private BigDecimal totalAmount;
    //备注
    private String remarks;

    /**
     * 子报表数据
     */
    private JRDataSource subData;
}
