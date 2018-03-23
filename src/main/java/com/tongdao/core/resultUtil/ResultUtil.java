package com.tongdao.core.resultUtil;


import com.tongdao.base.model.Result;

/**
 * Created by jing on 2017/9/22.
 */
public class ResultUtil {
    public static Result success(Object object){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("成功!~~!~!~");
        result.setData(object);
        return result;
    }

    public static Result success(){
        return success(null);
    }

    public static Result error(String msg){
        Result result = new Result();
        result.setCode(1);
        result.setMsg(msg);
        return result;
    }
}
