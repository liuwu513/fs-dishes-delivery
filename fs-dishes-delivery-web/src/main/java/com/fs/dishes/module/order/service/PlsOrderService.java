package com.fs.dishes.module.order.service;

import com.fs.dishes.base.common.Constant;
import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.service.BaseService;
import com.fs.dishes.base.utils.IdGen;
import com.fs.dishes.module.customer.dao.PlsCustomerDao;
import com.fs.dishes.module.customer.entity.PlsCustomer;
import com.fs.dishes.module.order.dao.PlsMainOrderDao;
import com.fs.dishes.module.order.dao.PlsOrderFoodDao;
import com.fs.dishes.module.order.dao.PlsSubOrderDao;
import com.fs.dishes.module.order.entity.PlsMainOrder;
import com.fs.dishes.module.order.entity.PlsOrderFood;
import com.fs.dishes.module.order.entity.PlsSubOrder;
import com.fs.dishes.module.res.dao.PlsFoodDao;
import com.fs.dishes.module.res.entity.PlsFood;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Arrays;
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

    @Autowired
    private PlsCustomerDao plsCustomerDao;


    /**
     * 主单分页搜索
     *
     * @param params
     * @return
     */
    public ResResult pageMainOrder(Map<String, Object> params) {
        Integer status = MapUtils.getInteger(params, "status", 1);
        params.put("status", status);
        PageHelper.startPage(getPageNo(params), getPageSize(params));
        List<PlsMainOrder> list = plsMainOrderDao.queryList(params);
        PageInfo<PlsMainOrder> page = new PageInfo<>(list);
        logger.info("搜索条件：{}，搜索到的主单信息共{}条", params, page.getTotal());
        return ResResult.ok().withData(page);
    }

    /**
     * 子单分页搜索
     *
     * @param params
     * @return
     */
    public ResResult pageSubOrder(Map<String, Object> params) {
        Integer status = MapUtils.getInteger(params, "status", 1);
        params.put("status", status);
        PageHelper.startPage(getPageNo(params), getPageSize(params));
        List<PlsSubOrder> list = plsSubOrderDao.queryList(params);
        PageInfo<PlsSubOrder> page = new PageInfo<>(list);
        List<PlsSubOrder> subOrderList = page.getList();
        if (CollectionUtils.isNotEmpty(subOrderList)) {
            List<Long> speciesIdList = subOrderList.stream().map(PlsSubOrder::getCustomerId).collect(Collectors.toList());
            List<Long> distinctSpeciesIdList = speciesIdList.stream().distinct().collect(Collectors.toList());

            Map<String, Object> query = Maps.newHashMap();
            query.put("idList", distinctSpeciesIdList);
            query.put("status", Constant.DataState.NORMAL.getValue());
            List<PlsCustomer> customerList = plsCustomerDao.queryList(query);
            if (CollectionUtils.isNotEmpty(customerList)) {
                Map<Long, String> customerMap = customerList.stream().collect(Collectors.toMap(PlsCustomer::getId, PlsCustomer::getName));
                subOrderList.forEach(item -> {
                    item.setCustomerName(customerMap.get(item.getCustomerId()));
                });
            }
        }
        logger.info("搜索条件：{}，搜索到的子单信息共{}条", params, page.getTotal());
        return ResResult.ok().withData(page);
    }

    /**
     * 获取主单详情
     *
     * @param orderId
     * @return
     */
    public ResResult getMainById(String orderId) {
        PlsMainOrder mainOrder = plsMainOrderDao.selectByPrimaryKey(orderId);
        return ResResult.ok().withData(mainOrder);
    }

    /**
     * 获取子弹详情
     *
     * @param orderId
     * @return
     */
    public ResResult getSubById(Long orderId) {
        PlsSubOrder subOrder = plsSubOrderDao.selectByPrimaryKey(orderId);
        Map<String, Object> params = Maps.newHashMap();
        params.put("subOrderId", orderId);
        List<PlsOrderFood> list = plsOrderFoodDao.queryList(params);
        if (CollectionUtils.isNotEmpty(list)) {
            List<Long> foodIdList = list.stream().map(PlsOrderFood::getFoodId).collect(Collectors.toList());
            Map<String, Object> foodParams = Maps.newHashMap();
            foodParams.put("idList", foodIdList);
            foodParams.put("status", Constant.DataState.NORMAL.getValue());
            List<PlsFood> foodList = plsFoodDao.queryList(foodParams);
            if (CollectionUtils.isNotEmpty(foodList)) {
                Map<Long, PlsFood> foodMap = foodList.stream().collect(Collectors.toMap(item -> item.getId(), item -> item));
                for (PlsOrderFood plsOrderFood : list) {
                    PlsFood plsFood = foodMap.get(plsOrderFood.getFoodId());
                    if (plsFood != null) {
                        plsOrderFood.setName(plsFood.getName());
                        plsOrderFood.setUnitId(plsFood.getUnitId());
                    }
                }
            }
        }
        subOrder.setList(list);
        return ResResult.ok().withData(subOrder);
    }


    /**
     * 根据客户ID获取该客户最近下的子单食品数据
     * @param customerId
     * @return
     */
    public ResResult getSubByCustomerId(Long customerId){
        Long subOrderId = plsSubOrderDao.getSubIdByCustomerId(customerId);
        if (subOrderId != null){
            ResResult resResult = getSubById(subOrderId);
            if (resResult.getData() != null){
                PlsSubOrder plsSubOrder = (PlsSubOrder)resResult.getData();
                //设置ID为空
                plsSubOrder.setId(null);
                if (CollectionUtils.isNotEmpty(plsSubOrder.getList())){
                    plsSubOrder.getList().forEach(item->{
                        item.setId(StringUtils.EMPTY);
                    });
                }
                return resResult;
            }
        }
        return ResResult.ok();
    }

    /**
     * 创建主单
     *
     * @param mainOrder
     * @return
     */
    public ResResult createMainOrder(PlsMainOrder mainOrder) {
        mainOrder.setStatus(Constant.DataState.NORMAL.getValue());
        if (mainOrder.getId() != null) {
            mainOrder.setModifyTime(new Date());
            mainOrder.setModifyBy(getUserId());
            plsMainOrderDao.updateByPrimaryKey(mainOrder);
            logger.info("主单名称:{}，更新成功", mainOrder.getOrderDesc());
        } else {
            mainOrder.setId(IdGen.nextId());
            mainOrder.setPayStatus(Constant.PayState.UN_PAY.getValue());
            mainOrder.setCreateTime(new Date());
            mainOrder.setCreateBy(getUserId());
            plsMainOrderDao.insert(mainOrder);
            logger.info("主单名称:{}，新增成功", mainOrder.getOrderDesc());
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
        if (subOrder.getId() != null) {
            subOrder.setModifyTime(new Date());
            subOrder.setModifyBy(getUserId());
            plsSubOrderDao.updateByPrimaryKeySelective(subOrder);
            logger.info("子单名称:{}，更新成功", subOrder.getName());
        } else {
            subOrder.setPayStatus(Constant.PayState.UN_PAY.getValue());
            subOrder.setId(IdGen.nextId());
            subOrder.setCreateTime(new Date());
            subOrder.setCreateBy(getUserId());
            subOrder.setStatus(Constant.DataState.NORMAL.getValue());
            plsSubOrderDao.insert(subOrder);
            logger.info("子单名称:{}，新增成功", subOrder.getName());
        }
        Long subOrderId = subOrder.getId();
        if (subOrderId != null) {
            List<PlsOrderFood> orderFoodList = subOrder.getList();
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (PlsOrderFood plsOrderFood : orderFoodList) {
                plsOrderFood.setCreateTime(new Date());
                plsOrderFood.setSubOrderId(subOrderId);
                plsOrderFood.setAmount(plsOrderFood.getNumber().multiply(plsOrderFood.getUnitPrice()));
                totalAmount = totalAmount.add(plsOrderFood.getAmount());
            }
            subOrder.setTotalAmount(totalAmount);
            subOrder.setDiscountAmount(BigDecimal.ZERO);
            plsSubOrderDao.updateByPrimaryKeySelective(subOrder);
            return choiceOrderFood(orderFoodList, subOrder.getMainOrderId(), subOrderId);
        }
        return ResResult.ok().withData(Boolean.TRUE);
    }

    /**
     * 子单列表
     *
     * @param orderFoodList
     * @return
     */
    public ResResult choiceOrderFood(List<PlsOrderFood> orderFoodList, Long mainOrderId, Long subOrderId) {
        if (CollectionUtils.isNotEmpty(orderFoodList)) {
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
                logger.info("子单商品信息ids:{}，共{}个，刪除成功", preIdList, preIdList.size());
            }

            //需新增数据
            List<PlsOrderFood> insertList = orderFoodList.stream().filter(item ->
                    StringUtils.isBlank(item.getId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(insertList)) {
                plsOrderFoodDao.batchAdd(insertList);
                logger.info("新增子单{}商品信息共{}个，新增成功", subOrderId, insertList.size());
            }

            //需更新数据
            List<PlsOrderFood> updateList = orderFoodList.stream().filter(item ->
                    StringUtils.isNotBlank(item.getId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(updateList)) {
                for (PlsOrderFood plsOrderFood : updateList) {
                    plsOrderFoodDao.updateByPrimaryKey(plsOrderFood);
                }
                logger.info("更新子单{}商品信息共{}个，更新成功", subOrderId, updateList.size());
            }
            if (mainOrderId != null){
                Example example = new Example(PlsSubOrder.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("mainOrderId",mainOrderId);
                criteria.andEqualTo("status",Constant.DataState.NORMAL.getValue());
                List<PlsSubOrder> subOrderList = plsSubOrderDao.selectByExample(example);
                if (CollectionUtils.isNotEmpty(subOrderList)){
                    BigDecimal totalAmount = BigDecimal.ZERO;
                    for (PlsSubOrder subOrder : subOrderList) {
                        totalAmount = totalAmount.add(subOrder.getTotalAmount());
                    }
                    PlsMainOrder plsMainOrder = plsMainOrderDao.selectByPrimaryKey(mainOrderId);
                    plsMainOrder.setTotalAmount(totalAmount);
                    plsMainOrderDao.updateByPrimaryKey(plsMainOrder);
                }
            }
            return ResResult.ok().withData(Boolean.TRUE);
        }
        return ResResult.error(300, "子单商品数据入库失败！");
    }

    /**
     * 删除主单信息
     *
     * @param ids
     * @return
     */
    public ResResult deleteByMain(List<Long> ids) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("idList", ids);
        params.put("status", Constant.DataState.NORMAL.getValue());
        List<PlsMainOrder> mainOrderList = plsMainOrderDao.queryList(params);
        Boolean flag = Boolean.TRUE;
        StringBuilder errorMsg = new StringBuilder();
        if (CollectionUtils.isNotEmpty(mainOrderList)) {
            List<String> mainOrderIdList = plsSubOrderDao.queryMainByCondition(params);
            if (CollectionUtils.isNotEmpty(mainOrderIdList)) {
                flag = Boolean.FALSE;
                errorMsg.append("主单名称 [");
                for (int i = 0; i < mainOrderList.size(); i++) {
                    PlsMainOrder plsMainOrder = mainOrderList.get(i);
                    if (mainOrderIdList.contains(plsMainOrder.getId().toString())) {
                        errorMsg.append(plsMainOrder.getOrderDesc());
                        if (i < mainOrderList.size() - 1) {
                            errorMsg.append("，");
                        }
                    }
                }
                errorMsg.append("],已存在子单信息，请重新选择！");
                logger.info(errorMsg.toString());
            } else {
                flag = plsMainOrderDao.batchDel(ids, Constant.DataState.FAKE_DEL.getValue());
                logger.info("主单ids：{},共{}个,删除成功！", ids, ids.size());
            }
        }
        if (flag) {
            return ResResult.ok().withData(flag);
        } else {
            return ResResult.error(300, errorMsg.toString());
        }
    }

    public ResResult deleteBySub(List<Long> ids) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("idList", ids);
        params.put("status", Constant.DataState.NORMAL.getValue());
        List<PlsSubOrder> subOrderList = plsSubOrderDao.queryList(params);

        Boolean flag = Boolean.TRUE;
        StringBuilder errorMsg = new StringBuilder();
        if (CollectionUtils.isNotEmpty(subOrderList)) {
            List<String> subOrderIdList = plsOrderFoodDao.querySubByCondition(params);
            if (CollectionUtils.isNotEmpty(subOrderIdList)) {
                flag = Boolean.FALSE;
                errorMsg.append("子单名称 [");
                for (int i = 0; i < subOrderList.size(); i++) {
                    PlsSubOrder plsSubOrder = subOrderList.get(i);
                    if (subOrderIdList.contains(plsSubOrder.getId().toString())) {
                        errorMsg.append(plsSubOrder.getName());
                        if (i < subOrderList.size() -1) {
                            errorMsg.append("，");
                        }
                    }
                }
                errorMsg.append("]，已存在子单商品信息，请重新选择！");
                logger.info(errorMsg.toString());
            } else {
                flag = plsSubOrderDao.batchDel(ids, Constant.DataState.FAKE_DEL.getValue());
                logger.info("子单ids：{},共{}个,删除成功！", ids, ids.size());
            }
        }
        if (flag) {
            return ResResult.ok().withData(flag);
        } else {
            return ResResult.error(300, errorMsg.toString());
        }
    }

    /**
     * 分单付款
     *
     * @param subOrderIdList
     * @param payStatus
     * @return
     */
    public ResResult paymentBySub(Long mainOrderId, List<Long> subOrderIdList, Integer payStatus) {
        if (CollectionUtils.isEmpty(subOrderIdList)) {
            return ResResult.error(300, "请选择分单信息！");
        }
        Map<String, Object> params = Maps.newHashMap();
        params.put("idList", subOrderIdList);
        params.put("payStatus", payStatus);
        plsSubOrderDao.batchUpdatePayStatus(params);

        List<Integer> statusList = plsSubOrderDao.queryAllPayStatus(mainOrderId);
        if (CollectionUtils.isNotEmpty(statusList)) {
            Integer payStatusByMain = Constant.PayState.PAYING.getValue();
            //子单已全部付款，更新主单付款状态
            if (statusList.size() == 1) {
                if (statusList.get(0) == Constant.PayState.PAID.getValue()) {
                    payStatusByMain = Constant.PayState.PAID.getValue();
                }
            }
            plsMainOrderDao.updatePayStatusById(mainOrderId, payStatusByMain);
        }
        return ResResult.ok();
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
