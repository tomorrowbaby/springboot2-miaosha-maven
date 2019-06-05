package com.miaosha.demo.controller;

import com.miaosha.demo.controller.viewobject.ItemVO;
import com.miaosha.demo.error.BusinessException;
import com.miaosha.demo.error.EmBusinessError;
import com.miaosha.demo.response.CommonReturnType;
import com.miaosha.demo.service.ItemService;
import com.miaosha.demo.service.model.ItemModel;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：商品的控制层
 * @author wangyu
 * @date 2019/5/30
 */
@Controller
@RequestMapping("/item")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ItemController extends BaseController{

    @Autowired
    ItemService itemService;

    //商品创建接口
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType createItem(@RequestParam(name = "title")String title,
                                       @RequestParam(name = "description")String description,
                                       @RequestParam(name = "price") BigDecimal price,
                                       @RequestParam(name = "stock")Integer stock,
                                       @RequestParam(name = "imgUrl")String imgUrl) throws BusinessException {
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setStock(stock);
        itemModel.setDescription(description);
        itemModel.setImgUrl(imgUrl);
        itemModel.setPrice(price);
        ItemModel itemModelReturn = itemService.createItem(itemModel);
        ItemVO itemVO = this.convertVOFromModel(itemModelReturn);

        return CommonReturnType.create(itemVO);
    }

    //商品详情页浏览接口
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ResponseBody
    public CommonReturnType getItem(@RequestParam(name = "id")Integer id) throws BusinessException {
        if (id == null){
            throw new BusinessException(EmBusinessError.UNKNOW_ERROR);
        }
        ItemModel itemModel = itemService.getItemById(id);
        if (itemModel == null) {
            throw new BusinessException(EmBusinessError.ITEM_NOT_EXIT);
        }
        ItemVO itemVO = this.convertVOFromModel(itemModel);
        return CommonReturnType.create(itemVO);
    }


    //商品页面浏览
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public CommonReturnType listItem() throws BusinessException {
        List<ItemModel> itemModelList = itemService.listItem();

        List<ItemVO> itemVOList = itemModelList.stream().map(itemModel -> {
            ItemVO itemVO = this.convertVOFromModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());

        return CommonReturnType.create(itemVOList);
    }


    private ItemVO convertVOFromModel(ItemModel itemModel){
        if (itemModel == null){
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel,itemVO);

        if (itemModel.getPromoModel() != null) {
            itemVO.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVO.setPromoId(itemModel.getPromoModel().getId());
            itemVO.setPromoItemPrice(itemModel.getPromoModel().getPromoItemPrice());
            String promoStartTime = itemModel.getPromoModel().getStartTime().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
            String promoEndTime = itemModel.getPromoModel().getEndTime().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
            itemVO.setPromoStartTime(promoStartTime);
            itemVO.setPromoEndTime(promoEndTime);
            //获取系统当前时间
            itemVO.setNowTime(DateTime.now().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));

        }else {
            itemVO.setPromoStatus(3);
        }

        return itemVO;
    }
}
