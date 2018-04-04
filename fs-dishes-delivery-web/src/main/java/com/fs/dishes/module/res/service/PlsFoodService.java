package com.fs.dishes.module.res.service;

import com.fs.dishes.base.common.Constant;
import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.service.BaseService;
import com.fs.dishes.module.res.dao.PlsFoodDao;
import com.fs.dishes.module.res.entity.PlsFood;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 客户业务类
 * Created by liuwu on 2018/4/2 0002.
 */
@Service
public class PlsFoodService extends BaseService {

    @Autowired
    private PlsFoodDao plsFoodDao;


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
        return ResResult.ok().withData(page);
    }

    /**
     * 删除该食品
     *
     * @param id 客户ID
     * @return
     */
    public ResResult delFood(Long id) {
        PlsFood food = plsFoodDao.selectByPrimaryKey(id);
        if (food != null) {
            food.setStatus(Constant.DataState.FAKE_DEL.getValue());
            plsFoodDao.updateByPrimaryKey(food);
            logger.info("食品信息：{},删除成功！", food.getName());
        }
        return ResResult.ok().withData(Boolean.TRUE);
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
            plsFoodDao.updateByPrimaryKey(plsFood);
            logger.info("食品名称:{}，更新成功", plsFood.getName());
        } else {
            plsFood.setCreateBy(getUserId());
            plsFood.setCreateTime(new Date());
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
