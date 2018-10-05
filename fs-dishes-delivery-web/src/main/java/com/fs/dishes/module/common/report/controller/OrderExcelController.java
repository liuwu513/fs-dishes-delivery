package com.fs.dishes.module.common.report.controller;

import com.fs.dishes.base.controller.AbstractController;
import com.fs.dishes.base.utils.DateUtils;
import com.fs.dishes.base.view.ExcelReportView;
import com.fs.dishes.base.view.PdfReportView;
import com.fs.dishes.module.common.report.service.OrderReportService;
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
 * jasper excel view
 * Created by Liuwu on 2018/10/5.
 */
@Controller
@RequestMapping("api/xls")
public class OrderExcelController extends AbstractController{

    @Autowired
    private OrderReportService orderReportService;

    @RequestMapping(value = "viewOrderXls/{subOrderId}", method = RequestMethod.GET)
    public ModelAndView viewHtml(@PathVariable("subOrderId") Long subOrderId, HttpServletResponse response) throws Exception {
        //获取配送子单数据
        Map<String, Object> map = orderReportService.getSubOrderReportData(subOrderId);
        String reportName = MapUtils.getString(map, "customerName") + DateUtils.format(new Date(), DateUtils.DATE_PATTERN);
        Integer size = Integer.valueOf(getParameter("size"));
        if (size == 1) {
            return new ModelAndView(new ExcelReportView("template/subOrderReport1.jasper", reportName), map);
        } else {
            return new ModelAndView(new ExcelReportView("template/subOrderReport2.jasper", reportName), map);
        }
    }


    @RequestMapping(value = "viewMainXls/{mainOrderId}", method = RequestMethod.GET)
    public ModelAndView viewMainHtml(@PathVariable("mainOrderId") Long mainOrderId, HttpServletResponse response) throws Exception {
        //获取主单数据
        Map<String, Object> map = orderReportService.getMainOrderReportData(mainOrderId);
        String reportName = MapUtils.getString(map, "mainOrderName") + DateUtils.format(new Date(), DateUtils.DATE_PATTERN);
        return new ModelAndView(new ExcelReportView("template/mainOrderReport1.jasper", reportName), map);
    }
}
