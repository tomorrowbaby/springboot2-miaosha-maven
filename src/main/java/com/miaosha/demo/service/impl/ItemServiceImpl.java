package com.miaosha.demo.service.impl;


import com.miaosha.demo.dao.ItemDOMapper;
import com.miaosha.demo.dao.ItemStockDOMapper;
import com.miaosha.demo.dataobject.ItemDO;
import com.miaosha.demo.dataobject.ItemStockDO;
import com.miaosha.demo.error.BusinessException;
import com.miaosha.demo.error.EmBusinessError;
import com.miaosha.demo.service.ItemService;
import com.miaosha.demo.service.PromoService;
import com.miaosha.demo.service.model.ItemModel;
import com.miaosha.demo.service.model.PromoModel;
import com.miaosha.demo.validator.ValidationResult;
import com.miaosha.demo.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：商品服务层实现类
 * @author wangyu
 * @date 2019/5/30
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ItemDOMapper itemDOMapper;

    @Autowired
    private ItemStockDOMapper itemStockDOMapper;

    @Autowired
    private PromoService promoService;

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        //判断入参
        ValidationResult validationResult = validator.validate(itemModel);
        if (validationResult.isHasError()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,validationResult.getErrMsg());
        }

        //将Model转化为DataObject
        ItemDO itemDO = convertItemDOFromItemModel(itemModel);

          //插入数据
        itemDOMapper.insertSelective(itemDO);
        itemModel.setItemId(itemDO.getId());

        //将Model转化为DataObject
         ItemStockDO itemStockDO = convertItemStockDOFromItemModel(itemModel);

        //插入数据
         itemStockDOMapper.insertSelective(itemStockDO);

        //返回Model对象
        return this.getItemById(itemModel.getId());
    }


    //商品列表获取
    @Override
    public List<ItemModel> listItem() throws BusinessException {

        //获取商品信息列表
        List<ItemDO> itemDOList = itemDOMapper.listItem();
        if (itemDOList == null || itemDOList.size() == 0){
            throw new BusinessException(EmBusinessError.ITEM_NOT_EXIT);
        }

        //获取商品详情列表
        int startItemId = itemDOList.get(0).getId();
        int endItemId = itemDOList.get(itemDOList.size()-1).getId();

        List<ItemStockDO> itemStockDOList = itemStockDOMapper.listItemStock(startItemId,endItemId);

        if (itemStockDOList == null || itemStockDOList.size() == 0) {
            throw new BusinessException(EmBusinessError.ITEM_STOCK_NOT_EXIT);
        }

        List<ItemModel> itemModelList = this.convertModelListFromDataObjectList(itemDOList,itemStockDOList);

        return itemModelList;
    }


    //商品信息获取
    @Override
    public ItemModel getItemById(Integer id) throws BusinessException {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        if (itemDO == null){
            return null;
        }

        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(id);

        ItemModel itemModel = this.convertModelFromDateObject(itemStockDO,itemDO);

        //获取活动商品信息
        PromoModel promoModel = promoService.getPromoByItemId(id);
        if (promoModel != null && promoModel.getStatus().intValue() != 3) {
            itemModel.setPromoModel(promoModel);
        }

        return itemModel;
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId,Integer amount) {
        int affectRow = itemStockDOMapper.decreaseStock(itemId,amount);

        if (affectRow > 0) {
            //库存更新成功
            return true;
        }else {
            //库存更新失败
            return false;
        }
    }


    //增加销量
    @Override
    @Transactional
    public void increaseSales(Integer itemId, Integer amount) throws BusinessException {
        if (itemId == null || amount == null) {
            throw new BusinessException(EmBusinessError.UNKNOW_ERROR);
        }
        itemDOMapper.increaseSales(itemId,amount);
    }


    private ItemDO convertItemDOFromItemModel(ItemModel itemModel) {
        if (itemModel == null){
            return null;
        }
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel,itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue());
        return itemDO;
    }

    private ItemStockDO convertItemStockDOFromItemModel(ItemModel itemModel){
        if (itemModel == null){
            return null;
        }
        ItemStockDO itemStockDO = new ItemStockDO();
        BeanUtils.copyProperties(itemModel,itemStockDO);
        return itemStockDO;
    }


    private ItemModel convertModelFromDateObject(ItemStockDO itemStockDO, ItemDO itemDO){
        if (itemDO == null || itemStockDO == null){
            return null;
        }
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO,itemModel);
        itemModel.setPrice(new BigDecimal(itemDO.getPrice()));
        itemModel.setStock(itemStockDO.getStock());

        return itemModel;
    }

    private List<ItemModel> convertModelListFromDataObjectList(List<ItemDO> itemDOList,
                                                 List<ItemStockDO> itemStockDOList) throws BusinessException {
        if(itemDOList == null || itemStockDOList == null){
            throw new BusinessException(EmBusinessError.UNKNOW_ERROR);
        }



        //将商品信息和商品详情的dataobject -> model
        List<ItemModel> itemModelList = new ArrayList<>();
        ItemModel itemModel = new ItemModel();
        int flag = 0 ;
        for (ItemDO itemDO:
                itemDOList) {
            ItemStockDO itemStockDO = itemStockDOList.get(flag);
            itemModel = this.convertModelFromDateObject(itemStockDO,itemDO);
            itemModelList.add(itemModel);
            flag++;
        }
        return itemModelList;
    }

}
