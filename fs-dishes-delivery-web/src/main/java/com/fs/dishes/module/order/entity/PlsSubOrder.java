package com.fs.dishes.module.order.entity;

import com.fs.dishes.base.common.BaseEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Transient;
import java.util.List;

/**
 * 配送子单
 * Created by liuwu on 2018/4/2 0002.
 */
@ToString
@Data
public class PlsSubOrder extends BaseEntity {

    private String id;        //主键ID
    private String name ;     //子单名称
    private String customerId;	//客户ID
    private String mainOrderId;	//主单ID
    private String totalAmount;	//子单总金额
    private String discountAmount;	//子单总优惠金额

    @Transient
    private List<PlsOrderFood> list;
}
