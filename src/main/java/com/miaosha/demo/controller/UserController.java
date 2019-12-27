package com.miaosha.demo.controller;


import com.miaosha.demo.controller.viewobject.UserVO;
import com.miaosha.demo.error.BusinessException;
import com.miaosha.demo.error.EmBusinessError;
import com.miaosha.demo.response.CommonReturnType;
import com.miaosha.demo.service.UserService;
import com.miaosha.demo.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * 描述：用户Controller层
 * @author wangyu
 * @date 2019/5/22
 */


@Controller
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController extends BaseController{

    @Autowired
    UserService userService ;

    @Autowired
    HttpServletRequest httpServletRequest ;

    @Autowired
    private RedisTemplate redisTemplate;

    //用户登录接口
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType login(@RequestParam(name = "telphone")String telphone,
                                  @RequestParam(name = "password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (org.apache.commons.lang3.StringUtils.isEmpty(telphone)
        || org.apache.commons.lang3.StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //用户登录服务，用来校验用户登录是否合法
        UserModel userModel = userService.validateLogin(telphone,password);

        //将用户加入到Session

        //生成Token UUID
        String uuidToken = UUID.randomUUID().toString();
        uuidToken = uuidToken.replace("-","");
        System.out.println(uuidToken);
        redisTemplate.opsForValue().set(uuidToken,userModel);
        redisTemplate.expire(uuidToken,1, TimeUnit.HOURS);


        //基于cookie
//        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
//        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);

        return CommonReturnType.create(uuidToken);

    }


    //获取用户信息接口
    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
        UserModel userModel = userService.getUserById(id) ;
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIT) ;
        }
        //将Model转化为可供ui使用的object
        UserVO userVO =  convertFromModel(userModel) ;
        //返回通用对象
        return CommonReturnType.create(userVO) ;
    }

    //用户获取otp接口
    @RequestMapping(value = "/getopt",method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType getOpt(@RequestParam(name = "telphone")String telphone){
        //按照一定的规则生成OPT验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String optCode = String.valueOf(randomInt);
        //将opt验证码同用户的手机号关联
        redisTemplate.opsForValue().set(telphone,optCode);
        redisTemplate.expire(telphone,3,TimeUnit.MINUTES);
        //将opt验证码通过短信通道发送给用户
        System.out.println(optCode);

        return CommonReturnType.create(null);
    }

    //用户注册接口
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "name")String name,
                                     @RequestParam(name = "telphone")String telphone,
                                     @RequestParam(name = "age")Integer age,
                                     @RequestParam(name = "gender")Integer gender,
                                     @RequestParam(name = "optCode")String optCode,
                                     @RequestParam(name = "password")String password
                                     ) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //验证手机号和对应的opt相符合
        String tel = redisTemplate.opsForValue().get(telphone).toString();
        if (!com.alibaba.druid.util.StringUtils.equals(tel,optCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不符合");
        }
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setAge(age);
        userModel.setGender(gender.byteValue());
        userModel.setTelphone(telphone);
        userModel.setRegisterMode("byphone");
        userModel.setEncrptPassword(this.EncodeByBCrypt(password));
        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    //密码生成
    private String EncodeByBCrypt(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(str);
    }

    //将model转化为viewObject
    public UserVO convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null ;
        }
        UserVO userVO = new UserVO() ;
        BeanUtils.copyProperties(userModel,userVO) ;
        return userVO ;
    }

}
