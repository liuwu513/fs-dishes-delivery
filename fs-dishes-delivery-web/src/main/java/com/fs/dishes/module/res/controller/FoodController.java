package com.fs.dishes.module.res.controller;

import com.fs.dishes.base.annotations.LogManage;
import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.controller.AbstractController;
import com.fs.dishes.base.validator.ValidatorUtils;
import com.fs.dishes.base.validator.group.AddGroup;
import com.fs.dishes.module.customer.entity.PlsCustomer;
import com.fs.dishes.module.res.entity.PlsFood;
import com.fs.dishes.module.res.service.PlsFoodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 食品控制层
 * Created by liuwu on 2018/4/4 0004.
 */
@Api(description = "食品相关接口")
@RestController
@RequestMapping("/api/food")
public class FoodController extends AbstractController{

    @Autowired
    private PlsFoodService plsFoodService;

    /**
     * 食品分页列表
     */
    @ApiOperation(value = "食品列表接口", notes = "食品列表接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页记录数", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "食品名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "Integer", paramType = "query")
    })
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("food:list")
    public ResResult list(@RequestParam Map<String, Object> params) {
        return plsFoodService.pageFood(params);
    }


    /**
     * 获取食品信息
     */
    @ApiOperation(value = "食品详情接口", notes = "食品详情接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "foodId", value = "食品ID", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/info{foodId}", method = RequestMethod.POST)
    public ResResult info(@PathVariable("foodId") String foodId) {
        return plsFoodService.getById(foodId);
    }

    /**
     * 保存以及更新食品
     */
    @ApiOperation(value = "保存以及更新食品信息接口", notes = "保存以及更新食品信息接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "食品ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "食品名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "details", value = "食品详情", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "speciesId", value = "品种ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "imgLink", value = "食品图片地址", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "feature", value = "食品特色", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "price", value = "食品单价", dataType = "String", paramType = "query")
    })
    @LogManage("保存以及更新食品信息")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @RequiresPermissions("food:save")
    public ResResult save(@RequestBody PlsFood food) {
        ValidatorUtils.validateEntity(food, AddGroup.class);
        return plsFoodService.modifyFood(food);
    }


    /**
     * 删除食品信息
     */
    @ApiOperation(value = "删除食品信息", notes = "删除食品信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "foodIds", value = "食品ID集合", paramType = "query"),
    })
    @LogManage("删除食品信息")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("food:delete")
    public ResResult delete(@RequestBody String[] foodIds) {
        return plsFoodService.delFoods(foodIds);
    }
}
