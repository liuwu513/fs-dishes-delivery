package com.fs.dishes.module.res.controller;

import com.fs.dishes.base.annotations.LogManage;
import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.controller.AbstractController;
import com.fs.dishes.base.validator.ValidatorUtils;
import com.fs.dishes.base.validator.group.AddGroup;
import com.fs.dishes.module.res.entity.PlsFoodSpecies;
import com.fs.dishes.module.res.service.FoodSpeciesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

/**
 * 食品种类品种管理
 * Created by liuwu on 2018/4/4 0004.
 */
@Api(description = "食品种类相关接口")
@RestController
@RequestMapping("/api/species")
public class FoodSpeciesController extends AbstractController {

    @Autowired
    private FoodSpeciesService foodSpeciesService;


    /**
     * 所有食品种类列表
     */
    @ApiOperation(value = "食品种类列表接口", notes = "食品种类列表接口", httpMethod = "POST")
    @RequestMapping(value = "/listMenu", method = RequestMethod.POST)
//    @RequiresPermissions("species:list")
    public ResResult list() {
        return foodSpeciesService.listSpecies();
    }

    /**
     * 食品种类分页列表
     */
    @ApiOperation(value = "食品种类分页列表接口", notes = "食品种类分页列表接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页记录数", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "食品种类名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "Integer", paramType = "query")
    })
    @RequestMapping(value = "/list", method = RequestMethod.POST)
//    @RequiresPermissions("species:list")
    public ResResult list(@RequestBody Map<String, Object> params) {
        return foodSpeciesService.pageSpecies(params);
    }

    /**
     * 获取食品种类信息
     */
    @ApiOperation(value = "食品种类详情接口", notes = "食品种类详情接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "speciesId", value = "食品种类ID", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/info{speciesId}", method = RequestMethod.POST)
    public ResResult info(@PathVariable("speciesId") Long speciesId) {
        return foodSpeciesService.getById(speciesId);
    }

    /**
     * 保存以及更新食品种类
     */
    @ApiOperation(value = "保存以及更新食品种类信息接口", notes = "保存以及更新食品种类信息接口", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "食品种类ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "食品种类名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "remarks", value = "食品种类详情", dataType = "String", paramType = "query")
    })
    @LogManage("保存以及更新食品种类信息")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    @RequiresPermissions("species:save")
    public ResResult save(@RequestBody PlsFoodSpecies foodSpecies) {
        ValidatorUtils.validateEntity(foodSpecies, AddGroup.class);
        return foodSpeciesService.modifySpecies(foodSpecies);
    }


    /**
     * 删除食品种类信息
     */
    @ApiOperation(value = "删除食品种类信息", notes = "删除食品种类信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "speciesIds", value = "食品种类ID集合", paramType = "query"),
    })
    @LogManage("删除食品种类信息")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
//    @RequiresPermissions("species:delete")
    public ResResult delete(@RequestBody Map<String, Object> params) {
        List<Long> speciesIdList = (List) params.get("speciesIds");
        return foodSpeciesService.delSpecies(speciesIdList);
    }
}
