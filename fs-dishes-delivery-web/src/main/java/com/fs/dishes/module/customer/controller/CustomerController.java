package com.fs.dishes.module.customer.controller;

import com.fs.dishes.base.annotations.LogManage;
import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.controller.AbstractController;
import com.fs.dishes.base.validator.ValidatorUtils;
import com.fs.dishes.base.validator.group.AddGroup;
import com.fs.dishes.module.customer.entity.PlsCustomer;
import com.fs.dishes.module.customer.service.PlsCustomerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 客户管理
 * Created by liuwu on 2018/4/4 0004.
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerController extends AbstractController {

    @Autowired
    private PlsCustomerService plsCustomerService;

    /**
     * 所有客户列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("customer:list")
    public ResResult list(@RequestParam Map<String, Object> params) {
        return plsCustomerService.pageCustomer(params);
    }

    /**
     * 获取客户信息
     */
    @RequestMapping(value = "/info{customerId}", method = RequestMethod.POST)
    public ResResult info(@PathVariable("customerId") Long customerId) {
        return plsCustomerService.getById(customerId);
    }

    /**
     * 保存以及更新客户
     */
    @LogManage("保存以及更新客户信息")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @RequiresPermissions("customer:save")
    public ResResult save(@RequestBody PlsCustomer customer) {
        ValidatorUtils.validateEntity(customer, AddGroup.class);
        return plsCustomerService.modifyCustomer(customer);
    }


    /**
     * 删除客户信息
     */
    @LogManage("删除客户信息")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("customer:delete")
    public ResResult delete(@RequestBody Long[] customerIds) {
        return plsCustomerService.delCustomers(customerIds);
    }
}
