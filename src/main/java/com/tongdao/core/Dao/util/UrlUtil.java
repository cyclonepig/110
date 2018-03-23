package com.tongdao.core.Dao.util;



import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

public class UrlUtil {

    public static String sendPost(String url, Map<String,?> paramMap){

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        String param = "";
        Iterator<String> it = paramMap.keySet().iterator();
        while (it.hasNext()){
            String key = it.next();
            param += key + "=" + paramMap.get(key) + "&";
        }
        try {
            URL realUrl = new URL(url);
            //设置
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept","*/*");
            conn.setRequestProperty("connection","Keep-Alive");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //发送post请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //发送请求参数
            out.print(param);
            //flush输出流缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null){
                result += line;

            }

        }catch (Exception e){

            e.printStackTrace();

        }finally {
            try {
                if (out!= null){
                    out.close();
                }
                if (in!= null){
                    in.close();
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
}
