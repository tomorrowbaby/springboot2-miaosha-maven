package com.miaosha.demo.service.model;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import com.mysql.cj.jdbc.*;
/**
 * 描述：商品秒杀模型层
 * @author wangyu
 * @date 2019/6/3
 */

public class PromoModel {

    private Integer id;

    //秒杀活动名称
    private String promoName;

    //秒杀活动开始时间
    private DateTime startTime;

    //秒杀活动结束时间
    private DateTime endTime;

    //秒杀活动适用商品
    private Integer itemId;

    //秒杀活动商品价格
    private BigDecimal promoItemPrice;


    //秒杀活动状态，为1表示未开始，2表示进行中， 3表示已结束
    private Integer status;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getPromoItemPrice() {
        return promoItemPrice;
    }

    public void setPromoItemPrice(BigDecimal promoItemPrice) {
        this.promoItemPrice = promoItemPrice;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }
}
