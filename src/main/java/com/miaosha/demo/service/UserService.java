package com.miaosha.demo.service;

import com.miaosha.demo.error.BusinessException;
import com.miaosha.demo.service.model.UserModel;


/**
 * 描述：用户Service层
 * @author wangyu
 * @date 2019/5/22
 */


public interface UserService {
    //通过Id获取对象
    UserModel getUserById(Integer id);

    //插入数据
    void register(UserModel userModel) throws BusinessException;

    /*
    telphone:用户手机号
    encrpPassword:用户加密后的密码
     */
    UserModel validateLogin(String telphone,String encrpPassword) throws BusinessException;

}
