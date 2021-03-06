package com.fs.dishes.module.order.controller;

import com.fs.dishes.base.annotations.LogManage;
import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.controller.AbstractController;
import com.fs.dishes.base.validator.ValidatorUtils;
import com.fs.dishes.base.validator.group.AddGroup;
import com.fs.dishes.module.order.entity.PlsMainOrder;
import com.fs.dishes.module.order.entity.PlsOrderFood;
import com.fs.dishes.module.order.entity.PlsSubOrder;
import com.fs.dishes.module.order.service.PlsOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

/**
 * 配送单管理
 * Created by liuwu on 2018/4/4 0004.
 */
@Api(description = "配送单相关接口")
@RestController
@RequestMapping("/api/order")
public class OrderController extends AbstractController {

    @Autowired
    private PlsOrderService plsOrderService;

    /**
     * 所有主单列表
     */
    @ApiOperation(value = "主配送单分页列表接口", notes = "配送单列表接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页记录数", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "配送单名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "Integer", paramType = "query")
    })
    @RequestMapping(value = "/main/list", method = RequestMethod.POST)
//    @RequiresPermissions("order:main:list")
    public ResResult list(@RequestBody Map<String, Object> params) {
        return plsOrderService.pageMainOrder(params);
    }

    /**
     * 所有子单列表
     */
    @ApiOperation(value = "子配送单分页列表接口", notes = "配送单列表接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页记录数", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "配送单名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "Integer", paramType = "query")
    })
    @RequestMapping(value = "/sub/list", method = RequestMethod.POST)
//    @RequiresPermissions("order:sub:list")
    public ResResult sublist(@RequestBody Map<String, Object> params) {
        return plsOrderService.pageSubOrder(params);
    }

    /**
     * 获取主单信息
     */
    @ApiOperation(value = "主配送单详情接口", notes = "主配送单详情接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "配送单ID", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/main/info", method = RequestMethod.POST)
    public ResResult mainInfo(@RequestBody Map<String, Object> params) {
        Long mainOrderId = MapUtils.getLong(params, "mainOrderId");
        return plsOrderService.getMainById(mainOrderId);
    }


    /**
     * 获取主单信息
     */
    @ApiOperation(value = "子配送单详情接口", notes = "子配送单详情接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "配送单ID", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/sub/info", method = RequestMethod.POST)
    public ResResult subInfo(@RequestBody Map<String, Object> params) {
        Long subOrderId = MapUtils.getLong(params, "subOrderId");
        return plsOrderService.getSubById(subOrderId);
    }


    /**
     * 获取客户最近下的子单数据
     */
    @ApiOperation(value = "获取客户最近下的子单数据", notes = "获取客户最近下的子单数据", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "客户ID", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/subByCsId/info", method = RequestMethod.POST)
    public ResResult subInfoByCustomerId(@RequestBody Map<String, Object> params) {
        Long customerId = MapUtils.getLong(params, "customerId");
        return plsOrderService.getSubByCustomerId(customerId);
    }

    /**
     * 保存以及修改主单信息
     */
    @ApiOperation(value = "保存以及更新主配送单信息接口", notes = "保存以及更新主配送单信息接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "配送单ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "orderDesc", value = "描述", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "details", value = "配送单详情", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "payStatus", value = "支付状态", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "totalAmount", value = "总金额", dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "discountAmount", value = "总优惠金额", dataType = "double", paramType = "query")
    })
    @LogManage("保存以及修改主单信息")
    @RequestMapping(value = "/main/save", method = RequestMethod.POST)
//    @RequiresPermissions("main:order:save")
    public ResResult save(@RequestBody PlsMainOrder mainOrder) {
        ValidatorUtils.validateEntity(mainOrder, AddGroup.class);
        return plsOrderService.createMainOrder(mainOrder);
    }

    /**
     * 保存以及修改子单信息
     */
    @ApiOperation(value = "保存以及更新子配送单信息接口", notes = "保存以及更新子配送单信息接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "配送单ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "配送单名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "details", value = "配送单详情", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "payStatus", value = "支付状态", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "totalAmount", value = "总金额", dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "discountAmount", value = "总优惠金额", dataType = "double", paramType = "query")
    })
    @LogManage("保存以及修改子单信息")
    @RequestMapping(value = "/sub/save", method = RequestMethod.POST)
//    @RequiresPermissions("sub:order:save")
    public ResResult save(@RequestBody PlsSubOrder subOrder) {
        ValidatorUtils.validateEntity(subOrder, AddGroup.class);
        return plsOrderService.createSubOrder(subOrder);
    }


    /**
     * 选择子单商品
     */
    @ApiOperation(value = "选择子单商品接口", notes = "选择子单商品接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "配送单ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "配送单名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "remarks", value = "配送单详情", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "品种ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "配送单图片地址", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "level", value = "配送单单价", dataType = "String", paramType = "query")
    })
    @LogManage("选择子单商品")
    @RequestMapping(value = "/sub/choice", method = RequestMethod.POST)
//    @RequiresPermissions("sub:order:choice")
    public ResResult choice(@RequestBody List<PlsOrderFood> list) {
        ValidatorUtils.validateEntity(list, AddGroup.class);
//        return plsOrderService.choiceOrderFood(list);
        return new ResResult().ok();
    }


    /**
     * 删除主单信息
     */
    @ApiOperation(value = "删除主配送单信息", notes = "删除主配送单信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerIds", value = "配送单ID集合", paramType = "query"),
    })
    @LogManage("删除主单信息")
    @RequestMapping(value = "/main/delete", method = RequestMethod.POST)
//    @RequiresPermissions("main:order:delete")
    public ResResult deleteByMain(@RequestBody Map<String, Object> params) {
        List<Long> orderIdList = (List) params.get("mainOrderIds");
        return plsOrderService.deleteByMain(orderIdList);
    }

    /**
     * 删除子单信息
     */
    @ApiOperation(value = "删除子配送单信息", notes = "删除子配送单信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerIds", value = "配送单ID集合", paramType = "query"),
    })
    @LogManage("删除子单信息")
    @RequestMapping(value = "/sub/delete", method = RequestMethod.POST)
//    @RequiresPermissions("sub:order:delete")
    public ResResult deleteBySub(@RequestBody Map<String, Object> params) {
        List<Long> orderIdList = (List) params.get("subOrderIds");
        return plsOrderService.deleteBySub(orderIdList);
    }


    @ApiOperation(value = "分单批量设置付款", notes = "分单批量设置付款", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "subOrderIds", value = "配送分单ID集合", paramType = "query"),
    })
    @LogManage("分单批量设置付款")
    @RequestMapping(value = "/sub/payment", method = RequestMethod.POST)
//    @RequiresPermissions("sub:order:payment")
    public ResResult paymentBySub(@RequestBody Map<String, Object> params) {
        Long mainOrderId = MapUtils.getLong(params, "mainOrderId");
        List<Long> orderIdList = (List) params.get("subOrderIds");
        Integer payStatus = MapUtils.getInteger(params, "payStatus", 1);
        return plsOrderService.paymentBySub(mainOrderId, orderIdList, payStatus);
    }


}
