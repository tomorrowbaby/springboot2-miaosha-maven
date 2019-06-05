package com.miaosha.demo.response;

/**
 * 描述：通用返回对象
 * @author wangyu
 * @date 2019/5/23
 */

public class CommonReturnType {

    //返回的请求信息，success或者fail
    private String status ;
    //status 为success返回前端的数据，fail返回一个通用的错误
    private Object data ;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    //定义一个通用的创建方法
    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result,"success") ;
    }

    public static CommonReturnType create(Object result,String status){
        CommonReturnType type = new CommonReturnType() ;
        type.setData(result);
        type.setStatus(status);
        return type;
    }
}
