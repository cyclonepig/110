package com.tongdao.core.handle;

import com.tongdao.base.model.Result;
import com.tongdao.core.resultUtil.ResultUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jing on 2017/9/22.
 */
@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e){
        return ResultUtil.error(e.getMessage());
    }
}
