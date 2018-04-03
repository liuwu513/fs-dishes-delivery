package com.fs.dishes.module.res.entity;

import com.fs.dishes.base.common.BaseEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 配送食品实体
 * Created by liuwu on 2018/4/2 0002.
 */
@ToString
@Data
@Table(name = "pls_food")
public class PlsFood extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;   //主键ID
    private String name;//食品名称
    private String details;//食品详情
    private Long speciesId;//食品种类ID
    private String imgLink;//食品图片链接，多个以“,”隔开
    private String feature; //食品特点
    private BigDecimal price;//食品默认价格
    private Integer status; //状态 1 正常 2 伪删除
}
