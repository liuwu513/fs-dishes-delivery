package com.fs.dishes.module.customer.service;

import com.fs.dishes.base.common.Constant;
import com.fs.dishes.base.common.ResResult;
import com.fs.dishes.base.service.BaseService;
import com.fs.dishes.module.customer.dao.PlsCustomerDao;
import com.fs.dishes.module.customer.entity.PlsCustomer;
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
public class PlsCustomerService extends BaseService {

    @Autowired
    private PlsCustomerDao plsCustomerDao;


    /**
     * 客户分页搜索
     *
     * @param params
     * @return
     */
    public ResResult pageCustomer(Map<String, Object> params) {
        PageHelper.startPage(getPageNo(params), getPageSize(params));
        List<PlsCustomer> list = plsCustomerDao.queryList(params);
        PageInfo<PlsCustomer> page = new PageInfo<>(list);
        return ResResult.ok().withData(page);
    }

    /**
     * 删除该客户
     *
     * @param id 客户ID
     * @return
     */
    public ResResult delCustomer(Long id) {


        PlsCustomer customer = plsCustomerDao.selectByPrimaryKey(id);
        if (customer != null) {
            customer.setStatus(Constant.DataState.FAKE_DEL.getValue());
            plsCustomerDao.updateByPrimaryKey(customer);
        }
        return ResResult.ok().withData(Boolean.TRUE);
    }

    /**
     * 新增或者修改客户信息
     *
     * @param plsCustomer
     * @return
     */
    public ResResult modifyCustomer(PlsCustomer plsCustomer) {
        Boolean isExists = existsCustomer(plsCustomer);
        if (isExists) {
            return ResResult.error(300, "客户名称已存在，请重新输入！");
        }
        if (plsCustomer.getId() != null) {
            plsCustomerDao.updateByPrimaryKey(plsCustomer);
        } else {
            plsCustomerDao.insert(plsCustomer);
        }
        return ResResult.ok().withData(Boolean.TRUE);
    }


    /**
     * 是否已存在该客户
     *
     * @param plsCustomer
     * @return
     */
    public boolean existsCustomer(PlsCustomer plsCustomer) {
        return plsCustomerDao.exists(plsCustomer);
    }

}
