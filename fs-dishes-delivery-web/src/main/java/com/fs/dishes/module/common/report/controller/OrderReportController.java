package com.fs.dishes.module.common.report.controller;

import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.controller.AbstractController;
import com.fs.dishes.base.view.HtmlReportView;
import com.fs.dishes.base.view.PdfReportView;
import com.fs.dishes.module.common.enums.DictEnum;
import com.fs.dishes.module.customer.entity.PlsCustomer;
import com.fs.dishes.module.customer.service.PlsCustomerService;
import com.fs.dishes.module.order.entity.PlsOrderFood;
import com.fs.dishes.module.order.entity.PlsSubOrder;
import com.fs.dishes.module.order.service.PlsOrderService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 订单报表导出
 * Created by liuwu on 2018/4/18 0018.
 */
@Slf4j
@Controller
@RequestMapping("api/rpt")
public class OrderReportController extends AbstractController {

    @Autowired
    private PlsOrderService plsOrderService;

    @Autowired
    private PlsCustomerService plsCustomerService;

    @RequestMapping(value = "viewOrderDetail/{subOrderId}",method = RequestMethod.GET)
    public ModelAndView viewHtml(@PathVariable("subOrderId") Long subOrderId, HttpServletResponse response) throws Exception {
        ResResult result = plsOrderService.getSubById(subOrderId);
        //配送分单数据
        PlsSubOrder subOrder = (PlsSubOrder)result.getData();
        //客户数据
        PlsCustomer customer = (PlsCustomer) plsCustomerService.getById(subOrder.getCustomerId()).getData();

        //组装包表数据
        Collection dataList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("customerName", customer.getName());
        map.put("subOrderNo", subOrder.getId());
        map.put("createOrderBy",getUser().getNickname());
        map.put("phone",getUser().getMobile());
        List<PlsOrderFood> list = subOrder.getList();
        list.sort(new Comparator<PlsOrderFood>() {
            @Override
            public int compare(PlsOrderFood o1, PlsOrderFood o2) {
                return o1.getFoodId().compareTo(o2.getFoodId());
            }
        });
        for (PlsOrderFood plsOrderFood : list) {
            Map<String,Object> mapItem = Maps.newHashMap();
            mapItem.put("foodId",plsOrderFood.getFoodId());
            mapItem.put("foodName",plsOrderFood.getName());
            mapItem.put("class", StringUtils.EMPTY);
            String unitName = DictEnum.UnitEnum.formCode(plsOrderFood.getUnitId()).desc();
            mapItem.put("unitName",unitName);
            mapItem.put("unitPrice",plsOrderFood.getUnitPrice());
            mapItem.put("number",plsOrderFood.getNumber());
            mapItem.put("amount",plsOrderFood.getAmount());
            dataList.add(mapItem);
        }
        map.put("orderFoods", new JRMapCollectionDataSource(dataList));
        return new ModelAndView(new PdfReportView("template/SecondReport1.jasper", "professors"),map);
    }

    @RequestMapping("viewOrderDetail1")
    public ModelAndView viewHtml1(HttpServletResponse response) throws Exception {
        OrderDataSource gradeDataSource = new OrderDataSource();
        return new ModelAndView(new HtmlReportView("template/SecondReport.jasper", "professors"),
                "", gradeDataSource);
    }

}
