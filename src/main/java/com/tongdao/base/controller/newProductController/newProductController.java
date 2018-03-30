package com.tongdao.base.controller.newProductController;

import com.alibaba.fastjson.JSONArray;
import com.tongdao.base.model.product.Productinfo;
import com.tongdao.base.service.impl.productInfoimpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class newProductController {

    @Resource
    private productInfoimpl productInfoimpl;

    @RequestMapping("recommendList")
    @ResponseBody
    public Map recommendList() throws Exception {

        Map recommenddate = new HashMap();
        List list2 = new ArrayList();
        try {
            //查询出符合条件的实体集合
            List<Productinfo> productinfoList = productInfoimpl.check("createtime", 4);
            //循环添加数据
            if (productinfoList.size() > 0) {
                for (int i = 0; i < productinfoList.size(); i++) {
                    List<Map> list = new ArrayList<>();
                    Map map = new HashMap();//商品属性；
                    Productinfo productinfo = productinfoList.get(i);
                    String picture = productinfo.getPrictureAddress();
                    String title = productinfo.getProductName();
                    String price = productinfo.getProductprice();
                    map.put("picture", picture);
                    map.put("title", title);
                    map.put("price", price);
                    list.add(map);
                    list2.add(list);

                }

                recommenddate.put("status", 1);
                recommenddate.put("mes", "数据读取成功");
                recommenddate.put("data", list2);
            } else {
                recommenddate.put("status", 0);
                recommenddate.put("mes", "数据读取失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("查询失败");
        }

        return recommenddate;
    }
}
