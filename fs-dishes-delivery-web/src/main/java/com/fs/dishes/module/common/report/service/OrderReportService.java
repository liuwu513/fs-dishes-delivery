package com.fs.dishes.module.common.report.service;

import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.service.BaseService;
import com.fs.dishes.base.utils.NumberUtils;
import com.fs.dishes.module.common.enums.DictEnum;
import com.fs.dishes.module.common.report.dao.PlsOrderReportDao;
import com.fs.dishes.module.common.report.entity.MainOrderReport;
import com.fs.dishes.module.common.report.entity.SubCustomerReport;
import com.fs.dishes.module.customer.entity.PlsCustomer;
import com.fs.dishes.module.customer.service.PlsCustomerService;
import com.fs.dishes.module.order.entity.PlsMainOrder;
import com.fs.dishes.module.order.entity.PlsOrderFood;
import com.fs.dishes.module.order.entity.PlsSubOrder;
import com.fs.dishes.module.order.service.PlsOrderService;
import com.google.common.collect.Maps;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private PlsOrderReportDao plsOrderReportDao;

    /**
     * 获取配送单数据
     *
     * @param subOrderId
     * @return
     */
    public Map<String, Object> getSubOrderReportData(Long subOrderId) {
        ResResult result = plsOrderService.getSubById(subOrderId);
        //配送分单数据
        PlsSubOrder subOrder = (PlsSubOrder) result.getData();
        //客户数据
        PlsCustomer customer = (PlsCustomer) plsCustomerService.getById(subOrder.getCustomerId()).getData();

        //组装包表数据
        Collection dataList = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("reportName", subOrder.getName());
        resultMap.put("customerName", customer.getName());
        resultMap.put("subOrderNo", subOrder.getId());
        resultMap.put("createOrderBy", getUser().getNickname());
        resultMap.put("phone", getUser().getMobile());
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

            BigDecimal number = (plsOrderFood.getActualNumber() != null && plsOrderFood.getActualNumber().
                    compareTo(BigDecimal.ZERO) > 0) ? plsOrderFood.getActualNumber() : plsOrderFood.getNumber();

            mapItem.put("number", number);
            mapItem.put("amount", plsOrderFood.getAmount());
            dataList.add(mapItem);
            index++;
        }
        resultMap.put("orderFoods", new JRMapCollectionDataSource(dataList));
        return resultMap;
    }

    /**
     * 获取主单数据信息
     *
     * @param mainOrderId
     * @return
     */
    public Map<String, Object> getMainOrderReportData(Long mainOrderId) {
        ResResult<PlsMainOrder> resResult = plsOrderService.getMainById(mainOrderId);
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("mainOrderName",resResult.getData().getOrderDesc());
        resultMap.put("mainOrderNo",mainOrderId);
        resultMap.put("createOrderBy", getUser().getNickname());
        resultMap.put("phone", getUser().getMobile());
        resultMap.put("SUBREPORT_DIR","template/");

        //获取主单食品汇总信息
        List<MainOrderReport> mainOrderReportList = plsOrderReportDao.listMainOrderReport(mainOrderId);
        //获取主单按客户分类汇总信息
        List<SubCustomerReport> customerReportList = plsOrderReportDao.listCustomerReportByMain(mainOrderId);
        Map<Long, List<SubCustomerReport>> customerMap = customerReportList.stream().collect(Collectors.groupingBy(SubCustomerReport::getFoodId));

        int index = 1;
        for (MainOrderReport mainOrderReport : mainOrderReportList) {
            mainOrderReport.setIndex(index);
            String unitName = DictEnum.UnitEnum.formCode(mainOrderReport.getUnitId()).desc();
            mainOrderReport.setUnitName(unitName);


            List<SubCustomerReport> subReportList = customerMap.get(mainOrderReport.getFoodId());
            if (CollectionUtils.isNotEmpty(subReportList)){
                mainOrderReport.setSubData(new JRBeanCollectionDataSource(subReportList));
            }
            index++;
        }
        JRDataSource dataSource = new JRBeanCollectionDataSource(mainOrderReportList);
        resultMap.put("orderFoods",dataSource);
        return resultMap;
    }
}
