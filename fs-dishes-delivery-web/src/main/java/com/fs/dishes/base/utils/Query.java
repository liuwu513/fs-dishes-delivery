package com.fs.dishes.base.utils;

import org.apache.commons.collections.MapUtils;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 查询参数
 * <p>
 * Created by liuwu on 2018/2/28 0028.
 */
public class Query extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    //当前页码
    private int page;
    //每页条数
    private int limit;

    public Query(Map<String, Object> params) {
        this.putAll(params);

        //分页参数
        this.page = MapUtils.getInteger(params, "pageNo", 1);
        this.limit = MapUtils.getInteger(params, "pageSize", 10);
        this.put("offset", (page - 1) * limit);
        this.put("page", page);
        this.put("limit", limit);
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}