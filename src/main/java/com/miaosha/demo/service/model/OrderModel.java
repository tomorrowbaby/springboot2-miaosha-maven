package com.miaosha.demo.service.model;

import java.math.BigDecimal;

/**
 * 描述：订单模型层
 * @author wangyu
 * @date 2019/6/2
 */

public class OrderModel {

    //订单编号，有特殊的编码形式
    private String id;

    //订单所属的用户
    private Integer userId;

    //订单购买商品
    private Integer itemId;

    //订单的数量
    private Integer amount;

    //订单金额
    private BigDecimal orderPrice;

    //购买商品的单价，如果是以秒杀的形式下单，表示秒杀价格
    private BigDecimal itemPrice;

    //若promoId非空，表示秒杀订单
    private Integer promoId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }
}
