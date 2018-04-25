package com.fs.dishes.base.utils;

import java.text.DecimalFormat;

/**
 * 数字格式化
 * Created by liuwu on 2018/4/25 0025.
 */
public class NumberUtils {

    public static final String FOOD_PREFIX = "S";
    public static final String MIN_DIGIT = "0000";

    /**
     * 格式化食品编号
     *
     * @param value
     * @param prefix
     * @param digit
     * @return
     */
    public static String formatNumber(Long value, String prefix, String digit) {
        DecimalFormat df = new DecimalFormat(digit);
        return prefix + df.format(value);
    }
}
