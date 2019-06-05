package com.miaosha.demo.service;

import com.miaosha.demo.error.BusinessException;
import com.miaosha.demo.service.model.ItemModel;

import java.util.List;

/**
 * 描述：商品模块接口
 * @author wangyu
 * @date 2019/5/30
 */

public interface ItemService {

    //创建商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    //商品列表浏览
    List<ItemModel> listItem() throws BusinessException;


    //商品详情浏览
    ItemModel getItemById(Integer id) throws BusinessException;


    //库存扣减
    boolean decreaseStock(Integer itemId,Integer amount);

    //销量增加
    void increaseSales(Integer itemId,Integer amount) throws BusinessException;




}
