package com.fs.dishes.base.validator;

import com.fs.dishes.base.exceptions.FDDException;
import org.apache.commons.lang.StringUtils;

/**
 * 数据校验
 * Created by liuwu on 2018/2/28 0028.
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new FDDException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new FDDException(message);
        }
    }
}
