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
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

/**
 * 配送单管理
 * Created by liuwu on 2018/4/4 0004.
 */
@RestController
@RequestMapping("/api/order")
public class OrderController extends AbstractController {

    @Autowired
    private PlsOrderService plsOrderService;

    /**
     * 所有主单列表
     */
    @RequestMapping(value = "/main/list", method = RequestMethod.POST)
    @RequiresPermissions("order:main:list")
    public ResResult list(@RequestParam Map<String, Object> params) {
        return plsOrderService.pageMainOrder(params);
    }

    /**
     * 所有子单列表
     */
    @RequestMapping(value = "/sub/list", method = RequestMethod.POST)
    @RequiresPermissions("order:sub:list")
    public ResResult sublist(@RequestParam Map<String, Object> params) {
        return plsOrderService.pageSubOrder(params);
    }

    /**
     * 获取主单信息
     */
    @RequestMapping(value = "/main/info/{orderId}", method = RequestMethod.POST)
    public ResResult mainInfo(@PathVariable("orderId") String orderId) {
        return plsOrderService.getMainById(orderId);
    }


    /**
     * 获取主单信息
     */
    @RequestMapping(value = "/sub/info/{orderId}", method = RequestMethod.POST)
    public ResResult subInfo(@PathVariable("orderId") String orderId) {
        return plsOrderService.getSubById(orderId);
    }

    /**
     * 保存以及修改主单信息
     */
    @LogManage("保存以及修改主单信息")
    @RequestMapping(value = "/main/save", method = RequestMethod.POST)
    @RequiresPermissions("main:order:save")
    public ResResult save(@RequestBody PlsMainOrder mainOrder) {
        ValidatorUtils.validateEntity(mainOrder, AddGroup.class);
        return plsOrderService.createMainOrder(mainOrder);
    }

    /**
     * 保存以及修改子单信息
     */
    @LogManage("保存以及修改子单信息")
    @RequestMapping(value = "/sub/save", method = RequestMethod.POST)
    @RequiresPermissions("sub:order:save")
    public ResResult save(@RequestBody PlsSubOrder subOrder) {
        ValidatorUtils.validateEntity(subOrder, AddGroup.class);
        return plsOrderService.createSubOrder(subOrder);
    }


    /**
     * 选择子单商品
     */
    @LogManage("选择子单商品")
    @RequestMapping(value = "/sub/choice", method = RequestMethod.POST)
    @RequiresPermissions("sub:order:choice")
    public ResResult choice(@RequestBody List<PlsOrderFood> list) {
        ValidatorUtils.validateEntity(list, AddGroup.class);
        return plsOrderService.choiceOrderFood(list);
    }


    /**
     * 删除主单信息
     */
    @LogManage("删除主单信息")
    @RequestMapping(value = "/main/delete", method = RequestMethod.POST)
    @RequiresPermissions("main:order:delete")
    public ResResult deleteByMain(@RequestBody String[] orderIds) {
        return plsOrderService.deleteByMain(orderIds);
    }

    /**
     * 删除子单信息
     */
    @LogManage("删除子单信息")
    @RequestMapping(value = "/sub/delete", method = RequestMethod.POST)
    @RequiresPermissions("sub:order:delete")
    public ResResult deleteBySub(@RequestBody String[] orderIds) {
        return plsOrderService.deleteBySub(orderIds);
    }
}
