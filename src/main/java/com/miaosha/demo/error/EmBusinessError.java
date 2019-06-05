package com.miaosha.demo.error;

/**
 * 描述：错误接口实现类
 * @author wangyu
 * @date 2019/5/23
 */

public enum EmBusinessError implements CommonError{

    //定义一个通用类型10001
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    //定义一个未知错误
    UNKNOW_ERROR(10002,"未知错误"),


    //以2开头的为用户相关错误
    USER_NOT_EXIT(20001,"用户不存在"),
    USER_LOGIN_FAIL(20002,"用户账号或密码不正确"),
    USER_NOT_LOGIN(20003,"用户还未登录"),


    //以3开头的为商品相关错误
    ITEM_NOT_EXIT(30001,"商品信息不存在"),
    ITEM_STOCK_NOT_EXIT(30002,"商品详情不存在"),


    //以4开头为交易错误
    STOCK_NOT_ENOUGH(40001,"库存不够了")
    ;

    private int errCode;
    private String errMsg;

    private EmBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }


    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int getErrCode() {
        return errCode;
    }

    @Override
    public String getErrMessage() {
        return errMsg;
    }

    //定义一个通用的错误类型00001
    @Override
    public CommonError setErrMessage(String errMsg) {
        this.errMsg = errMsg ;
        return this;
    }
}
