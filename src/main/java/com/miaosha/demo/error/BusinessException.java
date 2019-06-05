package com.miaosha.demo.error;

/**
 * 描述：业务异常信息
 * @author wangyu
 * @date 2019/5/23
 */

public class BusinessException extends Exception implements CommonError{

    private CommonError commonError;


    //直接接受BusinessException传参构造业务异常
    public BusinessException(CommonError commonError){
        super();
        this.commonError = commonError;
    }

    //接受自定义errMsg构造异常
    public BusinessException(CommonError commonError,String errMsg){
        super();
        this.commonError = commonError;
        this.commonError.setErrMessage(errMsg);
    }


    @Override
    public int getErrCode() {
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMessage() {
        return this.commonError.getErrMessage();
    }

    @Override
    public CommonError setErrMessage(String errMsg) {
        this.commonError.setErrMessage(errMsg);
        return this;
    }
}
