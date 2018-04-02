package com.fs.dishes.module.res.service;

import com.fs.dishes.base.common.Constant;
import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.service.BaseService;
import com.fs.dishes.module.res.dao.PlsFoodDao;
import com.fs.dishes.module.res.entity.PlsFood;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        PageHelper.startPage(getPageNo(params), getPageSize(params));
        List<PlsFood> list = plsFoodDao.queryList(params);
        PageInfo<PlsFood> page = new PageInfo<>(list);
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
            return ResResult.error(300, "食品名称已存在，请重新输入！");
        }
        if (plsFood.getId() != null) {
            plsFoodDao.updateByPrimaryKey(plsFood);
        } else {
            plsFoodDao.insert(plsFood);
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
