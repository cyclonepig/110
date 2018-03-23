package com.tongdao.base.service.impl;

import com.tongdao.base.commonDao.DemoDao;
import com.tongdao.base.model.product.cementGrindingAid.Cementgrindingaid;
import com.tongdao.base.service.DemoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class Demoimpl implements DemoService{


    @Resource
    private DemoDao demoDao;
    @Override
    public int save(Cementgrindingaid po) {

        return demoDao.addLocal(po);
    }

    @Override
    public Cementgrindingaid check(Long id) {

        return demoDao.get(id);
    }
}
