package com.tongdao.base.controller;

import com.tongdao.base.model.product.cementGrindingAid.Cementgrindingaid;
import com.tongdao.base.service.DemoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class DemoController {

    @Resource
    private DemoService demoService;

    @RequestMapping("/demo1")
    @ResponseBody
    public String demo(){

//        String tableName = "cementgrindingaid";
        Cementgrindingaid po = new Cementgrindingaid();
        po.setId(1);
        po.setProductName("LX-1");
        po.setRemarks("demo");
        int a = demoService.save(po);
        if(a!=0){
            return "success";
        }else {
            return "error";
        }

    }

    @RequestMapping("/demo2")
    @ResponseBody
    public String demo1(){

        Cementgrindingaid po = demoService.check(Long.valueOf(1));
        if (po!=null){
            return "success";
        }else {
            return "error";
        }

    }

}
