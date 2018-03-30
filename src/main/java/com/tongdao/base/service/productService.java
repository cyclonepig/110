package com.tongdao.base.service;

import com.tongdao.base.model.product.Productinfo;

import java.util.List;

public interface productService {

    public List<Productinfo> check(String order, Integer limit);

}
