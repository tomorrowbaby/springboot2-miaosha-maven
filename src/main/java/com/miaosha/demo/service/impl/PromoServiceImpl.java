package com.miaosha.demo.service.impl;

import com.miaosha.demo.dao.PromoDOMapper;
import com.miaosha.demo.dataobject.PromoDO;
import com.miaosha.demo.service.PromoService;
import com.miaosha.demo.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 描述：商品秒杀实现类
 * @author wangyu
 * @date 2019/6/3
 */

@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    PromoDOMapper promoDOMapper;


    @Override
    public PromoModel getPromoByItemId(Integer itemId)  {

        if (itemId == null) {
            return null;
        }

        PromoDO promoDO = promoDOMapper.selectByItemId(itemId);

        if (promoDO == null) {
            return null;
        }

        PromoModel promoModel = this.convertPromoModelFromPromoDO(promoDO);

        //判断秒杀活动是否开始
        if (promoModel.getStartTime().isAfterNow()){
            promoModel.setStatus(1);
        }else if (promoModel.getEndTime().isAfterNow()){
            promoModel.setStatus(2);
        }else {
            promoModel.setStatus(3);
        }

        return promoModel;
    }



    private PromoModel convertPromoModelFromPromoDO(PromoDO promoDO){
        if (promoDO == null) {
            return null;
        }

        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO,promoModel);

        promoModel.setPromoItemPrice(new BigDecimal(promoDO.getPromoItemPrice()));
        promoModel.setStartTime(new DateTime(promoDO.getStartTime()));
        promoModel.setEndTime(new DateTime(promoDO.getEndTime()));
        return promoModel;

    }
}
