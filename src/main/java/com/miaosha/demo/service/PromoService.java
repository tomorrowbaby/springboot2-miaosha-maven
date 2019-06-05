package com.miaosha.demo.service;

import com.miaosha.demo.error.BusinessException;
import com.miaosha.demo.service.model.PromoModel;

/**
 * 描述：秒杀活动接口
 * @author wangyu
 * @date 2019/6/3
 */

public interface PromoService {

    //获取秒杀信息
    PromoModel getPromoByItemId(Integer itemId) throws BusinessException;
}
