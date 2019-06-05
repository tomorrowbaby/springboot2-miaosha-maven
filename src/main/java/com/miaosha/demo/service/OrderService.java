package com.miaosha.demo.service;

import com.miaosha.demo.error.BusinessException;
import com.miaosha.demo.service.model.OrderModel;

/**
 * 描述：订单Service层
 * @author wangyu
 * @date 2019/6/2
 */

public interface OrderService {

    //1.通过前端传过来一个秒杀Id，进行校验活动是否开启
    //2.直接在接口中判断秒杀商品是否存在，若存在进行中的则以秒杀价格下单
    OrderModel createOrder(Integer userId, Integer itemId, Integer promoId,Integer amount) throws BusinessException;
}
