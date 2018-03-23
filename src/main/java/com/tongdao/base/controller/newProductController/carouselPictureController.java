package com.tongdao.base.controller.newProductController;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class carouselPictureController {

    @RequestMapping("/carousel")
    @ResponseBody
    public Map Picture(){

        Map map = new HashMap();
        File mFlie = new File("E:\\picture");

        if(mFlie.exists() && mFlie.isDirectory()){
            List<String> pricture = new ArrayList<>();
            getAllFile( mFlie, pricture);
            map.put("status",1);
            map.put("msg","获取图片成功");
            map.put("images",pricture);

            return map;
        }else {
            map.put("status",2);
            map.put("mes","获取失败");
            return map;
        }
    }
    private static void getAllFile(File mFile,List<String> pricture){
        File[] files = mFile.listFiles();
        if (files != null){
            for (File file : files){
                if (file.isDirectory()){
                    getAllFile(file,pricture);
                }else {
                    String fileName = file.getName();
//                    String prictureName = fileName.substring(fileName.lastIndexOf("/"+1));
//                    E:\\prcture\\
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".gif")){
                        pricture.add(fileName);
                    }
                }
            }
        }

    }
}
