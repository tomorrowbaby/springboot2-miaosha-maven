package com.miaosha.demo.error;

/**
 * 描述：通用错误接口
 * @author wangyu
 * @date 2019/5/23
 */
public interface CommonError {

    int getErrCode();

    String getErrMessage();

    CommonError setErrMessage(String errMsg) ;
}
