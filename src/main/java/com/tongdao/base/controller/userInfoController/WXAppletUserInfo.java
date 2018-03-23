package com.tongdao.base.controller.userInfoController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongdao.core.Dao.util.UrlUtil;
import netscape.javascript.JSObject;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;
import org.codehaus.xfire.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Map;

@Controller
public class WXAppletUserInfo {


    protected Logger log = LoggerFactory.getLogger(WXAppletUserInfo.class);

    /**
     *
     * 获取微信小程序的用户详细信息
     * @author 涂伟超
     * @param code
     * @return
     */
    public static final String appid = "wxcc673f07ab921836";

    public static final String secret = "088f0e33b07be7d6586cc3f6c2e513fa";

    public static final String url = "https://api.weixin.qq.com/sns/jscode2session";

    public static JSONObject getSessionKeyOropenid(String code){

        String wxCode = code;
        Map<String,String> requestUrlParam = new HashMap<String, String>();
        requestUrlParam.put("appid",appid);
        requestUrlParam.put("secret",secret);
        requestUrlParam.put("url",url);
        requestUrlParam.put("js_code",wxCode);
        requestUrlParam.put("grant_type","authorization_code");

        JSONObject jsonObject = JSON.parseObject(UrlUtil.sendPost(url,requestUrlParam));
        return jsonObject;
    }

    /**
     * 解密敏感数据获取用户信息
     * @author 涂伟超
     * @param sessionKey 数据加密签名秘钥
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv 加密算法的初始向量
     * @return
     */
//    @RequestMapping("/getUserInfo")
//    @ResponseBody
    public static JSONObject getUserInfo(String encryptedData,String sessionKey,String iv){
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSON.parseObject(result);
            }
        } catch (NoSuchAlgorithmException e) {
//            log.error(e.getMessage(), e);
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
//            log.error(e.getMessage(), e);
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
//            log.error(e.getMessage(), e);
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
//            log.error(e.getMessage(), e);
            e.printStackTrace();
        } catch (BadPaddingException e) {
//            log.error(e.getMessage(), e);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
//            log.error(e.getMessage(), e);
            e.printStackTrace();
        } catch (InvalidKeyException e) {
//            log.error(e.getMessage(), e);
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
//            log.error(e.getMessage(), e);
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
//            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

}
