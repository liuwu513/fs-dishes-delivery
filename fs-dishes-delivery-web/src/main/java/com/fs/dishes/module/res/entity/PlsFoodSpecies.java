package com.fs.dishes.module.res.entity;

import com.fs.dishes.base.common.BaseEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 食品品种 实体
 * Created by liuwu on 2018/4/2 0002.
 */
@ToString
@Data
@Table(name = "pls_food_species")
public class PlsFoodSpecies extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;  //主键ID
    private String name;//种类名称
    private String remarks;//种类描述
}
