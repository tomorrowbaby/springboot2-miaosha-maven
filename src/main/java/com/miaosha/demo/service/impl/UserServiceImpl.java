package com.miaosha.demo.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.miaosha.demo.dao.UserInfoDOMapper;
import com.miaosha.demo.dao.UserPasswordDOMapper;
import com.miaosha.demo.dataobject.UserInfoDO;
import com.miaosha.demo.dataobject.UserPasswordDO;
import com.miaosha.demo.error.BusinessException;
import com.miaosha.demo.error.EmBusinessError;
import com.miaosha.demo.service.UserService;
import com.miaosha.demo.service.model.UserModel;
import com.miaosha.demo.validator.ValidationResult;
import com.miaosha.demo.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserInfoDOMapper userInfoDOMapper;

    @Autowired
    UserPasswordDOMapper userPasswordDOMapper ;

    @Autowired
    ValidatorImpl validator;

    @Override
    public UserModel getUserById(Integer id) {
        UserInfoDO userInfoDO = userInfoDOMapper.selectByPrimaryKey(id) ;
        if(userInfoDO == null){
            return null ;
        }
         UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userInfoDO.getId()) ;

        return convertFromDataObject(userInfoDO,userPasswordDO) ;
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException{
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        ValidationResult validationResult = validator.validate(userModel);
        if (validationResult.isHasError()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,validationResult.getErrMsg());
        }

        UserInfoDO userInfoDO = convertFromUserModel(userModel);

        userInfoDOMapper.insertSelective(userInfoDO);

        userModel.setId(userInfoDO.getId());

        UserPasswordDO userPasswordDO = convertPasswordFromUserModel(userModel);
        try {
            userPasswordDOMapper.insertSelective(userPasswordDO);
        }catch (DuplicateKeyException e) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"手机号码重复");
        }

    }

    //判断用户登录合法
    @Override
    public UserModel validateLogin(String telphone,String password) throws BusinessException {
        //通过用户获取手机获取
        UserInfoDO userInfoDO = userInfoDOMapper.selectByTelphone(telphone);
        if (userInfoDO == null){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }

        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userInfoDO.getId());
        UserModel userModel = convertFromDataObject(userInfoDO,userPasswordDO);

        //判断密码是否正确
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if( !passwordEncoder.matches(password,userModel.getEncrptPassword())){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }


    //将dataobject转化成model
    public UserModel convertFromDataObject(UserInfoDO userInfoDO, UserPasswordDO userPasswordDO) {
        if (userInfoDO == null){
            return null ;
        }

        UserModel userModel = new UserModel() ;
        BeanUtils.copyProperties(userInfoDO,userModel);

        if (userPasswordDO != null) {
           userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }

        return userModel ;
    }

    //将model转化成dataobject
    public UserInfoDO convertFromUserModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserInfoDO userInfoDO = new UserInfoDO();
        BeanUtils.copyProperties(userModel,userInfoDO);
        return userInfoDO ;
    }

    public UserPasswordDO convertPasswordFromUserModel(UserModel userModel) {
        if (userModel == null){
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }


}
