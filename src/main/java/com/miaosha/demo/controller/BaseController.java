package com.miaosha.demo.controller;

import com.miaosha.demo.error.BusinessException;
import com.miaosha.demo.error.EmBusinessError;
import com.miaosha.demo.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    //定义exceptionHandler未被controller层吸收的异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object hendlerException(HttpServletRequest request, Exception e) {
        Map<String,Object> responseData = new HashMap<>() ;
        if (e instanceof BusinessException){
            BusinessException businessException = (BusinessException) e;
            responseData.put("errCode",businessException.getErrCode());
            responseData.put("errMsg",businessException.getErrMessage());
        }else {
            responseData.put("errCode", EmBusinessError.UNKNOW_ERROR.getErrCode());
            responseData.put("errMsg",EmBusinessError.UNKNOW_ERROR.getErrMessage());
        }
        return CommonReturnType.create(responseData,"fail");
    }
}
