package com.tongdao.base.service.impl;

import com.tongdao.base.commonDao.ProductDao;
import com.tongdao.base.model.product.Productinfo;
import com.tongdao.base.service.productService;
import com.tongdao.core.Dao.beans.Method;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class productInfoimpl implements productService {

    @Resource
    private ProductDao productDao;

    @Override
    public List<Productinfo> check(String order, Integer limit) {
        return productDao.list(Method.orderByandLimt(order,limit));
    }
}
