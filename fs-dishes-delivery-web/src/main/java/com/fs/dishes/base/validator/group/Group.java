package com.fs.dishes.base.validator.group;

import javax.validation.GroupSequence;

/**
 * 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 * Created by liuwu on 2018/2/28 0028.
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface Group {

}
