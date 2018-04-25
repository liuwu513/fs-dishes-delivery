package com.fs.dishes.module.common.report.service;

import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.service.BaseService;
import com.fs.dishes.base.utils.NumberUtils;
import com.fs.dishes.module.common.enums.DictEnum;
import com.fs.dishes.module.customer.entity.PlsCustomer;
import com.fs.dishes.module.customer.service.PlsCustomerService;
import com.fs.dishes.module.order.entity.PlsOrderFood;
import com.fs.dishes.module.order.entity.PlsSubOrder;
import com.fs.dishes.module.order.service.PlsOrderService;
import com.google.common.collect.Maps;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

/**
 * 配送单报表业务类
 * Created by liuwu on 2018/4/25 0025.
 */
@Service
public class OrderReportService extends BaseService {

    @Autowired
    private PlsOrderService plsOrderService;

    @Autowired
    private PlsCustomerService plsCustomerService;

    /**
     * 获取配送单数据
     * @param subOrderId
     * @return
     */
    public Map<String, Object> getSubOrderReportData(@PathVariable("subOrderId") Long subOrderId) {
        ResResult result = plsOrderService.getSubById(subOrderId);
        //配送分单数据
        PlsSubOrder subOrder = (PlsSubOrder) result.getData();
        //客户数据
        PlsCustomer customer = (PlsCustomer) plsCustomerService.getById(subOrder.getCustomerId()).getData();

        //组装包表数据
        Collection dataList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("reportName", subOrder.getName());
        map.put("customerName", customer.getName());
        map.put("subOrderNo", subOrder.getId());
        map.put("createOrderBy", getUser().getNickname());
        map.put("phone", getUser().getMobile());
        List<PlsOrderFood> list = subOrder.getList();
        list.sort(new Comparator<PlsOrderFood>() {
            @Override
            public int compare(PlsOrderFood o1, PlsOrderFood o2) {
                return o1.getFoodId().compareTo(o2.getFoodId());
            }
        });
        int index = 1;
        for (PlsOrderFood plsOrderFood : list) {
            Map<String, Object> mapItem = Maps.newHashMap();
            mapItem.put("index", index);
            mapItem.put("foodId", NumberUtils.formatNumber(plsOrderFood.getFoodId(), NumberUtils.FOOD_PREFIX, NumberUtils.MIN_DIGIT));
            mapItem.put("foodName", plsOrderFood.getName());
            mapItem.put("class", StringUtils.EMPTY);
            String unitName = DictEnum.UnitEnum.formCode(plsOrderFood.getUnitId()).desc();
            mapItem.put("unitName", unitName);
            mapItem.put("unitPrice", plsOrderFood.getUnitPrice());
            mapItem.put("number", plsOrderFood.getNumber());
            mapItem.put("amount", plsOrderFood.getAmount());
            dataList.add(mapItem);
            index++;
        }
        map.put("orderFoods", new JRMapCollectionDataSource(dataList));
        return map;
    }
}
