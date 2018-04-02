package com.fs.dishes.module.sys.entity;


import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 系统配置信息
 * Created by liuwu on 2018/2/28 0028.
 */
@ToString
@Data
public class SysConfig implements Serializable {

    private Long id;
    @NotBlank(message = "参数名不能为空")
    private String key;
    @NotBlank(message = "参数值不能为空")
    private String value;
    private String remark;
}
