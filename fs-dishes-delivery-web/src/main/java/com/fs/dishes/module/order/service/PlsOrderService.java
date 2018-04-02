package com.fs.dishes.module.order.service;

import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.service.BaseService;
import com.fs.dishes.module.order.dao.PlsMainOrderDao;
import com.fs.dishes.module.order.dao.PlsOrderFoodDao;
import com.fs.dishes.module.order.dao.PlsSubOrderDao;
import com.fs.dishes.module.order.entity.PlsMainOrder;
import com.fs.dishes.module.order.entity.PlsOrderFood;
import com.fs.dishes.module.order.entity.PlsSubOrder;
import com.fs.dishes.module.res.dao.PlsFoodDao;
import com.fs.dishes.module.res.entity.PlsFood;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 订单业务层
 * Created by liuwu on 2018/4/2 0002.
 */
@Service
public class PlsOrderService extends BaseService {

    @Autowired
    private PlsMainOrderDao plsMainOrderDao;

    @Autowired
    private PlsSubOrderDao plsSubOrderDao;

    @Autowired
    private PlsOrderFoodDao plsOrderFoodDao;

    @Autowired
    private PlsFoodDao plsFoodDao;


    /**
     * 创建主单
     *
     * @param mainOrder
     * @return
     */
    public ResResult createMainOrder(PlsMainOrder mainOrder) {
        if (StringUtils.isNotBlank(mainOrder.getId())) {
            mainOrder.setModifyTime(new Date());
            mainOrder.setModifyBy(getUserId());
            plsMainOrderDao.updateByPrimaryKey(mainOrder);
        } else {
            mainOrder.setCreateTime(new Date());
            mainOrder.setCreateBy(getUserId());
            plsMainOrderDao.insert(mainOrder);
        }
        return ResResult.ok().withData(Boolean.TRUE);
    }

    /**
     * 创建子单
     *
     * @param subOrder
     * @return
     */
    public ResResult createSubOrder(PlsSubOrder subOrder) {
        if (StringUtils.isNotBlank(subOrder.getId())) {
            subOrder.setModifyTime(new Date());
            subOrder.setModifyBy(getUserId());
            plsSubOrderDao.updateByPrimaryKey(subOrder);
        } else {
            subOrder.setCreateTime(new Date());
            subOrder.setCreateBy(getUserId());
            plsSubOrderDao.insert(subOrder);
        }
        return ResResult.ok().withData(Boolean.TRUE);
    }

    /**
     * 子单列表
     *
     * @param orderFoodList
     * @return
     */
    public ResResult choiceOrderFood(List<PlsOrderFood> orderFoodList) {
        if (CollectionUtils.isNotEmpty(orderFoodList)) {
            String mainOrderId = orderFoodList.get(0).getMainOrderId();
            String subOrderId = orderFoodList.get(0).getSubOrderId();
            Map<String, Object> params = Maps.newHashMap();
            params.put("mainOrderId", mainOrderId);
            params.put("subOrderId", subOrderId);
            List<PlsOrderFood> preList = plsOrderFoodDao.queryList(params);

            List<String> preIdList = preList.stream().map(item -> item.getId()).collect(Collectors.toList());
            List<String> currIdList = orderFoodList.stream().filter(item -> StringUtils.isNotBlank(item.getId())).
                    map(item -> item.getId()).collect(Collectors.toList());
            //需删除数据
            preIdList.removeAll(currIdList);
            if (CollectionUtils.isNotEmpty(preIdList)) {
                plsOrderFoodDao.batchDel(preIdList);
            }

            //需新增数据
            List<PlsOrderFood> insertList = orderFoodList.stream().filter(item ->
                    StringUtils.isBlank(item.getId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(insertList)) {
                plsOrderFoodDao.batchAdd(insertList);
            }

            //需更新数据
            List<PlsOrderFood> updateList = orderFoodList.stream().filter(item ->
                    StringUtils.isNotBlank(item.getId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(updateList)) {
                for (PlsOrderFood plsOrderFood : updateList) {
                    plsOrderFoodDao.updateByPrimaryKey(plsOrderFood);
                }
            }
            return ResResult.ok().withData(Boolean.TRUE);
        }
        return ResResult.error(300, "子单商品数据入库失败！");
    }


    /**
     * 根据主订单获取商品单价
     *
     * @param mainOrderId
     * @param foodId
     * @return
     */
    public ResResult getPriceByOrder(String mainOrderId, String foodId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("mainOrderId", mainOrderId);
        params.put("foodId", foodId);
        List<PlsOrderFood> orderFoodList = plsOrderFoodDao.queryList(params);
        if (CollectionUtils.isNotEmpty(orderFoodList)) {
            return ResResult.ok().withData(orderFoodList.get(0).getUnitPrice());
        } else {
            PlsFood food = plsFoodDao.selectByPrimaryKey(foodId);
            if (food == null) {
                return ResResult.error(300, "菜品信息不存在，请重新选择！");
            }
            return ResResult.ok().withData(food.getPrice());
        }
    }

}
