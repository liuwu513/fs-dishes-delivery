package com.fs.dishes.module.common.report.controller;

import com.fs.dishes.base.controller.AbstractController;
import com.fs.dishes.base.utils.DateUtils;
import com.fs.dishes.base.view.HtmlReportView;
import com.fs.dishes.base.view.PdfReportView;
import com.fs.dishes.module.common.report.service.OrderReportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 订单报表导出
 * Created by liuwu on 2018/4/18 0018.
 */
@Slf4j
@Controller
@RequestMapping("api/rpt")
public class OrderReportController extends AbstractController {

    @Autowired
    private OrderReportService orderReportService;

    @RequestMapping(value = "viewOrderDetail/{subOrderId}", method = RequestMethod.GET)
    public ModelAndView viewHtml(@PathVariable("subOrderId") Long subOrderId, HttpServletResponse response) throws Exception {
        //获取配送子单数据
        Map<String, Object> map = orderReportService.getSubOrderReportData(subOrderId);
        String reportName = MapUtils.getString(map, "customerName") + DateUtils.format(new Date(), DateUtils.DATE_PATTERN);
        return new ModelAndView(new PdfReportView("template/subOrderReport.jasper", reportName), map);
    }


    @RequestMapping(value = "viewMainHtml/{mainOrderId}", method = RequestMethod.GET)
    public ModelAndView viewMainHtml(@PathVariable("mainOrderId") Long mainOrderId, HttpServletResponse response) throws Exception {
        //获取主单数据
        Map<String, Object> map = orderReportService.getMainOrderReportData(mainOrderId);
        String reportName = MapUtils.getString(map, "mainOrderName") + DateUtils.format(new Date(), DateUtils.DATE_PATTERN);
        return new ModelAndView(new PdfReportView("template/mainOrderReport.jasper", reportName),map);
    }
}
