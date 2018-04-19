package com.fs.dishes.module.common.report.controller;

import com.fs.dishes.base.controller.AbstractController;
import com.fs.dishes.base.view.PdfReportView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

/**
 * 订单报表导出
 * Created by liuwu on 2018/4/18 0018.
 */
@Slf4j
@Controller
@RequestMapping("api/rpt")
public class OrderReportController extends AbstractController {

    @RequestMapping("viewOrderDetail")
    public ModelAndView viewHtml(HttpServletResponse response) throws Exception {
        GradeDataSource gradeDataSource = new GradeDataSource();
        return new ModelAndView(new PdfReportView("template/SecondReport.jasper", "professors"),
                "", gradeDataSource);
    }

}
