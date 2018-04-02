package com.fs.dishes.base.common;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体
 * Created by liuwu on 2018/4/2 0002.
 */
@ToString
@Data
public class BaseEntity implements Serializable{

    private String createBy; //创建人
    private Date createTime; //创建时间
    private String modifyBy; //修改人
    private Date modifyTime; //修改时间
}
