package com.miaosha.demo.controller.viewobject;

import org.joda.time.DateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 描述：商品展示信息
 * @author wangyu
 * @date 2019/5/30
 */

public class ItemVO {
    private Integer id;

    //商品名
    private String title;

    //商品价格
    private BigDecimal price;

    //商品库存
    private Integer stock;

    //商品描述
    private String description;

    //商品销量
    private Integer sales;

    //商品图片的url
    private String imgUrl;

    //记录商品是否在秒杀活动中，1表示活动未开始，2表示活动正在进行中，3表示已经结束
    private Integer promoStatus;

    //秒杀活动价格
    private BigDecimal promoItemPrice;

    private Integer promoId;

    //秒杀活动开始时间
    private String promoStartTime;

    //秒杀活动结束时间
    private String promoEndTime;

    //系统当前时间
    private String nowTime;

    public String getPromoEndTime() {
        return promoEndTime;
    }

    public void setPromoEndTime(String promoEndTime) {
        this.promoEndTime = promoEndTime;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public BigDecimal getPromoItemPrice() {
        return promoItemPrice;
    }

    public void setPromoItemPrice(BigDecimal promoItemPrice) {
        this.promoItemPrice = promoItemPrice;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public String getPromoStartTime() {
        return promoStartTime;
    }

    public void setPromoStartTime(String promoStartTime) {
        this.promoStartTime = promoStartTime;
    }

    public Integer getPromoStatus() {
        return promoStatus;
    }

    public void setPromoStatus(Integer promoStatus) {
        this.promoStatus = promoStatus;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

}
