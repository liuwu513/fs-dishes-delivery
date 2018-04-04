package com.fs.dishes.module.sys.controller;

import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.utils.PageUtils;
import com.fs.dishes.base.utils.Query;
import com.fs.dishes.module.sys.entity.SysLog;
import com.fs.dishes.module.sys.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * 系统日志
 * <p>
 * Created by liuwu on 2018/2/28 0028.
 */
@Api(description = "系统日志")
@Controller
@RequestMapping("/sys/log")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;

    /**
     * 列表
     */
    @ApiOperation(value = "日志列表", notes = "日志列表", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "关键字", dataType = "String", paramType = "query")
    })
    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @RequiresPermissions("sys:log:list")
    public ResResult list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<SysLog> sysLogList = sysLogService.queryList(query);
        int total = sysLogService.queryTotal(query);
        PageUtils pageUtil = new PageUtils(sysLogList, total, query.getLimit(), query.getPage());
        return ResResult.ok().withData(pageUtil);
    }

}
