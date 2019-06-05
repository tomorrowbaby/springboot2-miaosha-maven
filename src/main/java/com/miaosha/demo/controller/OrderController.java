package com.miaosha.demo.controller;

import com.miaosha.demo.error.BusinessException;
import com.miaosha.demo.error.EmBusinessError;
import com.miaosha.demo.response.CommonReturnType;
import com.miaosha.demo.service.OrderService;
import com.miaosha.demo.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述：订单控制层
 * @author wangyu
 * @date 2019/6/2
 */

@Controller
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class OrderController extends BaseController{

    @Autowired
    OrderService orderService;

    @Autowired
    HttpServletRequest httpServletRequest;



    //封装下单请求
    @RequestMapping(value = "/createorder" ,method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name = "itemId")Integer itemId,
                                        @RequestParam(name = "amount")Integer amount,
                                        @RequestParam(name = "promoId",required = false)Integer promoId) throws BusinessException {

        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"用户未登录，不能下单");
        }

        //获取用户登录信息
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");

        orderService.createOrder(userModel.getId(),itemId,promoId,amount);

        return CommonReturnType.create(null);

    }
}
