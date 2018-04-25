package com.fs.dishes.module.common.report.dao;

import com.fs.dishes.base.annotations.DataRepository;
import com.fs.dishes.module.common.report.entity.MainOrderReport;
import com.fs.dishes.module.common.report.entity.SubCustomerReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 报表统计查询
 * Created by liuwu on 2018/4/25 0025.
 */
@DataRepository
public interface PlsOrderReportDao{

    /**
     * 根据主单ID查询主单下单数据统计
     * @param mainOrderId
     * @return
     */
    List<MainOrderReport> listMainOrderReport(@Param("mainOrderId") Long mainOrderId);

    /**
     * 根据主单ID查询客户下单数据统计
     * @param mainOrderId
     * @return
     */
    List<SubCustomerReport> listCustomerReportByMain(@Param("mainOrderId") Long mainOrderId);
}
