package com.tongdao.base.service;

import com.tongdao.base.model.product.cementGrindingAid.Cementgrindingaid;

public interface DemoService {

    public int save(Cementgrindingaid po);
    
    public  Cementgrindingaid check(Long id);

}
