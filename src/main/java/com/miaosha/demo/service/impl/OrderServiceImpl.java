package com.miaosha.demo.service.impl;

import com.miaosha.demo.dao.OrderDOMapper;
import com.miaosha.demo.dataobject.OrderDO;
import com.miaosha.demo.error.BusinessException;
import com.miaosha.demo.error.EmBusinessError;
import com.miaosha.demo.service.ItemService;
import com.miaosha.demo.service.OrderService;
import com.miaosha.demo.service.SequenceService;
import com.miaosha.demo.service.UserService;
import com.miaosha.demo.service.model.ItemModel;
import com.miaosha.demo.service.model.OrderModel;
import com.miaosha.demo.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;



@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    @Autowired
    OrderDOMapper orderDOMapper;

    @Autowired
    SequenceService sequenceService;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId,Integer itemId,Integer promoId,Integer amount) throws BusinessException {

        //1.校验下单状态：下单的商品是否存在，下单的用户是否合法，购买的数量是否正确
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品信息不存在");
        }

        //2. 检验用户信息
        UserModel userModel = userService.getUserById(userId);
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户信息不存在");
        }

        //3.检验商品数量
        if (amount <= 0 || amount > 99) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"数量信息不正确");
        }

        //4.检验秒杀活动
        //(1)校验是否存在活动
        if (promoId != null) {
            //(2)校验活动信息是否正确
            if (promoId.intValue() != itemModel.getPromoModel().getId()) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动信息不正确");
                //(3)检验商品信息是否在活动中
            }else if (itemModel.getPromoModel().getStatus().intValue() != 2){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动信息不正确");
            }
        }

        //下单减库存
        boolean result = itemService.decreaseStock(itemId,amount);
        if (!result) {
            //减少库存失败
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }

        //3.订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setAmount(amount);
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        if (itemId != null){
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else {
            orderModel.setItemPrice(itemModel.getPrice());
        }

        orderModel.setPromoId(promoId);
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));
        //生成订单ID
        orderModel.setId(sequenceService.generateOrderNo());

        OrderDO orderDO = this.convertOrderDOFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);

        //商品销量增加
        itemService.increaseSales(itemId,amount);


        //5.返回前端
        return orderModel;
    }


    private OrderDO convertOrderDOFromOrderModel(OrderModel orderModel){
        if (orderModel == null) {
            return null;
        }

        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel,orderDO);
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());

        return orderDO;
    }


}
