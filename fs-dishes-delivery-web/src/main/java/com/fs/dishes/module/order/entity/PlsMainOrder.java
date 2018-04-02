package com.fs.dishes.module.order.entity;

import com.fs.dishes.base.common.BaseEntity;
import lombok.Data;
import lombok.ToString;

/**
 * 配送主单
 * Created by liuwu on 2018/4/2 0002.
 */
@ToString
@Data
public class PlsMainOrder extends BaseEntity{
    private String id;        //主键ID
    private String orderDesc ;//主单描述(名称后+日期 yyyy-MM-dd)
    private String details;	//主单详情
    private String totalAmount;	//主单总金额
    private String discountAmount;	//主单总优惠金额
}
