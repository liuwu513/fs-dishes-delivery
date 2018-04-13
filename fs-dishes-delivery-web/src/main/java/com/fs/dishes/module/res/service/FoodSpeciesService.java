package com.fs.dishes.module.res.service;

import com.fs.dishes.base.common.Constant;
import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.service.BaseService;
import com.fs.dishes.module.order.entity.PlsMainOrder;
import com.fs.dishes.module.res.dao.PlsFoodDao;
import com.fs.dishes.module.res.dao.PlsFoodSpeciesDao;
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

/**
 * Created by liuwu on 2018/4/4 0004.
 */
@Service
public class FoodSpeciesService extends BaseService {

    @Autowired
    private PlsFoodSpeciesDao plsFoodSpeciesDao;

    @Autowired
    private PlsFoodDao plsFoodDao;

    /**
     * 食品分页搜索
     *
     * @param params
     * @return
     */
    public ResResult pageSpecies(Map<String, Object> params) {
        Integer status = MapUtils.getInteger(params, "status", 1);
        params.put("status", status);
        params.put("order", "create_time,modify_time desc");
        PageHelper.startPage(getPageNo(params), getPageSize(params));
        List<PlsFoodSpecies> list = plsFoodSpeciesDao.queryList(params);
        PageInfo<PlsFoodSpecies> page = new PageInfo<>(list);
        logger.info("搜索条件：{}，搜索到的食品品种信息共{}条", params, page.getTotal());
        return ResResult.ok().withData(page);
    }

    /**
     * 查询所有食品种类
     *
     * @return
     */
    public ResResult listSpecies() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("status", Constant.DataState.NORMAL.getValue());
        params.put("order", "id asc");
        List<PlsFoodSpecies> list = plsFoodSpeciesDao.queryList(params);
        logger.info("搜索条件：{}，搜索到的食品品种信息共{}条", params, list.size());
        return ResResult.ok().withData(list);
    }

    /**
     * 获取种类详情
     *
     * @param speciesId
     * @return
     */
    public ResResult getById(Long speciesId) {
        PlsFoodSpecies foodSpecies = plsFoodSpeciesDao.selectByPrimaryKey(speciesId);
        return ResResult.ok().withData(foodSpecies);
    }

    /**
     * 删除该食品
     *
     * @param id 客户ID
     * @return
     */
    public ResResult delSpecies(Long id) {
        PlsFoodSpecies foodSpecies = plsFoodSpeciesDao.selectByPrimaryKey(id);
        if (foodSpecies != null) {
            foodSpecies.setStatus(Constant.DataState.FAKE_DEL.getValue());
            plsFoodSpeciesDao.updateByPrimaryKey(foodSpecies);
            logger.info("品种信息：{},删除成功！", foodSpecies.getName());
        }
        return ResResult.ok().withData(Boolean.TRUE);
    }

    public ResResult delSpecies(List<Long> ids) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("status", Constant.DataState.NORMAL.getValue());
        params.put("idList", ids);
        Boolean flag = Boolean.TRUE;
        StringBuilder errorMsg = new StringBuilder();
        List<PlsFoodSpecies> speciesList = plsFoodSpeciesDao.queryList(params);
        if (CollectionUtils.isNotEmpty(speciesList)) {
            List<Long> speciesIdList = plsFoodDao.querySpeciesByCondition(params);
            if (CollectionUtils.isNotEmpty(speciesIdList)) {
                flag = Boolean.FALSE;
                errorMsg.append("食品种类名称 [");
                for (PlsFoodSpecies foodSpecies : speciesList) {
                    if (speciesIdList.contains(foodSpecies.getId())) {
                        errorMsg.append(foodSpecies.getName() + "，");
                    }
                }
                errorMsg.append("已存在食品信息中，请重新选择！");
                logger.info(errorMsg.toString());
            } else {
                flag = plsFoodSpeciesDao.batchDel(ids, Constant.DataState.FAKE_DEL.getValue());
                logger.info("食品种类ids：{},共{}个,删除成功！", ids, ids.size());
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
     * @param foodSpecies
     * @return
     */
    public ResResult modifySpecies(PlsFoodSpecies foodSpecies) {
        Boolean isExists = existsSpecies(foodSpecies);
        if (isExists) {
            logger.info("品种名称:{}已存在，请重新输入！", foodSpecies.getName());
            return ResResult.error(300, String.format("品种名称：%s 已存在，请重新输入！", foodSpecies.getName()));
        }
        foodSpecies.setStatus(Constant.DataState.NORMAL.getValue());
        if (foodSpecies.getId() != null) {
            foodSpecies.setModifyTime(new Date());
            foodSpecies.setModifyBy(getUserId());
            plsFoodSpeciesDao.updateByPrimaryKeySelective(foodSpecies);
            logger.info("品种名称:{}，更新成功", foodSpecies.getName());
        } else {
            foodSpecies.setCreateTime(new Date());
            foodSpecies.setCreateBy(getUserId());
            plsFoodSpeciesDao.insert(foodSpecies);
            logger.info("品种名称:{}，新增成功", foodSpecies.getName());
        }
        return ResResult.ok().withData(Boolean.TRUE);
    }


    /**
     * 是否已存在该客户
     *
     * @param foodSpecies
     * @return
     */
    public boolean existsSpecies(PlsFoodSpecies foodSpecies) {
        return plsFoodSpeciesDao.exists(foodSpecies);
    }
}
