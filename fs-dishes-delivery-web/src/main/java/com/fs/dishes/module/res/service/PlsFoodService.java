package com.fs.dishes.module.res.service;

import com.fs.dishes.base.common.Constant;
import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.service.BaseService;
import com.fs.dishes.base.utils.IdGen;
import com.fs.dishes.module.order.dao.PlsOrderFoodDao;
import com.fs.dishes.module.order.entity.PlsMainOrder;
import com.fs.dishes.module.order.entity.PlsOrderFood;
import com.fs.dishes.module.res.dao.PlsFoodDao;
import com.fs.dishes.module.res.dao.PlsFoodSpeciesDao;
import com.fs.dishes.module.res.entity.PlsFood;
import com.fs.dishes.module.res.entity.PlsFoodSpecies;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 客户业务类
 * Created by liuwu on 2018/4/2 0002.
 */
@Service
public class PlsFoodService extends BaseService {

    @Autowired
    private PlsFoodDao plsFoodDao;

    @Autowired
    private PlsFoodSpeciesDao plsFoodSpeciesDao;

    @Autowired
    private PlsOrderFoodDao plsOrderFoodDao;


    /**
     * 食品分页搜索
     *
     * @param params
     * @return
     */
    public ResResult pageFood(Map<String, Object> params) {
        Integer status = MapUtils.getInteger(params, "status", 1);
        params.put("status", status);
        PageHelper.startPage(getPageNo(params), getPageSize(params));
        List<PlsFood> list = plsFoodDao.queryList(params);
        PageInfo<PlsFood> page = new PageInfo<>(list);
        logger.info("搜索条件：{}，搜索到的食品信息共{}条", params, page.getTotal());
        List<PlsFood> foodList = page.getList();
        if (CollectionUtils.isNotEmpty(foodList)) {
            List<Long> speciesIdList = foodList.stream().map(PlsFood::getSpeciesId).collect(Collectors.toList());
            List<Long> distinctSpeciesIdList = speciesIdList.stream().distinct().collect(Collectors.toList());

            Map<String, Object> query = Maps.newHashMap();
            query.put("idList", distinctSpeciesIdList);
            query.put("status", Constant.DataState.NORMAL.getValue());
            List<PlsFoodSpecies> speciesList = plsFoodSpeciesDao.queryList(query);
            if (CollectionUtils.isNotEmpty(speciesIdList)) {
                Map<Long, String> speciesMap = speciesList.stream().collect(Collectors.toMap(PlsFoodSpecies::getId, PlsFoodSpecies::getName));
                foodList.forEach(item -> {
                    item.setSpeciesName(speciesMap.get(item.getSpeciesId()));
                });
            }
        }
        return ResResult.ok().withData(page);
    }

    /**
     * 食品详情
     *
     * @param foodId
     * @return
     */
    public ResResult getById(String foodId) {
        PlsFood food = plsFoodDao.selectByPrimaryKey(foodId);
        return ResResult.ok().withData(food);
    }

    /**
     * 删除该食品
     *
     * @param id 客户ID
     * @return
     */
    public ResResult delFood(String id) {
        PlsFood food = plsFoodDao.selectByPrimaryKey(id);
        if (food != null) {
            food.setStatus(Constant.DataState.FAKE_DEL.getValue());
            plsFoodDao.updateByPrimaryKey(food);
            logger.info("食品信息：{},删除成功！", food.getName());
        }
        return ResResult.ok().withData(Boolean.TRUE);
    }

    /**
     * 删除食品信息
     *
     * @param ids
     * @return
     */
    public ResResult delFoods(List<String> ids) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("status", Constant.DataState.NORMAL.getValue());
        params.put("idList", ids);
        List<PlsFood> foodList = plsFoodDao.queryList(params);
        Boolean flag = Boolean.TRUE;
        StringBuilder errorMsg = new StringBuilder();
        if (CollectionUtils.isNotEmpty(foodList)) {
            List<String> foodIdList = plsOrderFoodDao.queryFoodByCondition(params);
            if (CollectionUtils.isNotEmpty(foodIdList)) {
                flag = Boolean.FALSE;
                errorMsg.append("食品名称 [");
                for (int i = 0; i < foodList.size(); i++) {
                    PlsFood food = foodList.get(i);
                    if (foodIdList.contains(food.getId())) {
                        errorMsg.append(food.getName());
                        if (i < foodList.size() - 1) {
                            errorMsg.append("，");
                        }
                    }
                }
                errorMsg.append("]，已存在子单信息中，请重新选择！");
                logger.info(errorMsg.toString());
            } else {
                flag = plsFoodDao.batchDel(ids, Constant.DataState.FAKE_DEL.getValue());
                logger.info("食品ids：{},共{}个,删除成功！", ids, ids.size());
            }
        }
        if (flag) {
            return ResResult.ok().withData(flag);
        } else {
            return ResResult.error(300, errorMsg.toString());
        }
    }

    /**
     * 新增或者修改食品信息
     *
     * @param plsFood
     * @return
     */
    public ResResult modifyFood(PlsFood plsFood) {
        Boolean isExists = existsFood(plsFood);
        if (isExists) {
            logger.info("食品名称:{}已存在，请重新输入！", plsFood.getName());
            return ResResult.error(300, String.format("食品名称：%s 已存在，请重新输入！", plsFood.getName()));
        }
        if (plsFood.getId() != null) {
            plsFood.setModifyBy(getUserId());
            plsFood.setModifyTime(new Date());
            plsFood.setStatus(Constant.DataState.NORMAL.getValue());
            plsFoodDao.updateByPrimaryKeySelective(plsFood);
            logger.info("食品名称:{}，更新成功", plsFood.getName());
        } else {
            plsFood.setId(IdGen.uuid());
            plsFood.setCreateBy(getUserId());
            plsFood.setCreateTime(new Date());
            plsFood.setStatus(Constant.DataState.NORMAL.getValue());
            plsFoodDao.insert(plsFood);
            logger.info("食品名称:{}，新增成功", plsFood.getName());
        }
        return ResResult.ok().withData(Boolean.TRUE);
    }


    /**
     * 是否已存在该客户
     *
     * @param plsFood
     * @return
     */
    public boolean existsFood(PlsFood plsFood) {
        return plsFoodDao.exists(plsFood);
    }

}
