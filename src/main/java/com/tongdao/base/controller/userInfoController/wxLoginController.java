package com.tongdao.base.controller.userInfoController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongdao.base.model.User.Userinfo;
import com.tongdao.base.service.impl.userInfoimpl;
import com.tongdao.core.Dao.util.AesCbcUtil;
import com.tongdao.core.Dao.util.UrlUtil;
import org.bouncycastle.operator.MacCalculatorProvider;
import org.springframework.http.HttpRequest;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class wxLoginController {

    @Resource
    private userInfoimpl userInfoimpl;

    @RequestMapping("/getUserInfo")
    @ResponseBody
    public Map decodeUerInfo(String encryptedData,String iv,String code){

        Map map = new HashMap();

        //登录凭证
        if(code == null || code.length() == 0){
            map.put("status",0);
            map.put("msg","code不能为空");
            return map;
        }

        String wxAppId = "wxcc673f07ab921836";
        String wxSecret = "088f0e33b07be7d6586cc3f6c2e513fa";
        String url = "https://api.weixin.qq.com/sns/jscode2session";

        //授权（必填）
        String grant_type = "authorization_code";

        //获取seeionKey和openid
        String params = "appid=" + wxAppId + "&secret=" + wxSecret + "&js_code=" + code+ "&grant_type=" + grant_type;
        Map<String,String> requestUrlParam = new HashMap<String, String>();
        requestUrlParam.put("appid",wxAppId);
        requestUrlParam.put("secret",wxSecret);
        requestUrlParam.put("url",url);
        requestUrlParam.put("js_code",code);
        requestUrlParam.put("grant_type",grant_type);
        JSONObject jsonObject = JSON.parseObject(UrlUtil.sendPost(url,requestUrlParam));

        String session_key = jsonObject.get("session_key").toString();

        String openid = (String)jsonObject.get("openid");

        //解密
        try{
            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            if (null != result && result.length()>0){
                map.put("status",1);
                map.put("msg","解密成功");
                JSONObject userInfoJSON = JSONObject.parseObject(result);
                Map userInfo = new HashMap();
                userInfo.put("openId", userInfoJSON.get("openId"));
                userInfo.put("nickName", userInfoJSON.get("nickName"));
                userInfo.put("gender", userInfoJSON.get("gender"));
                userInfo.put("city", userInfoJSON.get("city"));
                userInfo.put("province", userInfoJSON.get("province"));
                userInfo.put("country", userInfoJSON.get("country"));
                userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
                userInfo.put("unionId", userInfoJSON.get("unionId"));
                map.put("userInfo", userInfo);

                String appopenid = userInfoJSON.getString("openId");

                if(appopenid!=null){
                    List<Userinfo> userList= userInfoimpl.check(appopenid);
                    if(userList.size()==1){
                        System.out.println("登录成功");
                    }else if(userList.size()<1){
                        Userinfo user = new Userinfo();
                        user.setNickName(userInfoJSON.getString("nickName"));
                        user.setAvatarUrl(userInfoJSON.getString("avatarUrl"));
                        user.setCity(userInfoJSON.getString("city"));
                        user.setCountry(userInfoJSON.getString("country"));
                        user.setGender(userInfoJSON.getString("gender"));
                        user.setOpenId(userInfoJSON.getString("openId"));
                        user.setProvince(userInfoJSON.getString("province"));

                        int u = userInfoimpl.save(user);
                        if(u==1){
                            System.out.println("新增用户成功");
                        }else {
                            System.out.println("新增用户异常");
                        }
                    }else {
                        System.out.println("用户异常");
                    }
                }

                return map;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        map.put("status", 0);
        map.put("msg", "解密失败");
        return map;
    }
}
