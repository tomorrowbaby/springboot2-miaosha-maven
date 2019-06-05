package com.miaosha.demo.dataobject;

public class OrderDO {
    private String id;

    private Integer userId;

    private Integer itemId;

    private Integer amount;

    private Double orderPrice;

    private Double itemPrice;

    private Integer promoId;
    public OrderDO(String id, Integer userId, Integer itemId, Integer amount, Double orderPrice, Double itemPrice,Integer promoId) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.amount = amount;
        this.orderPrice = orderPrice;
        this.itemPrice = itemPrice;
        this.promoId = promoId;
    }


    public OrderDO() {
        super();
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }
}