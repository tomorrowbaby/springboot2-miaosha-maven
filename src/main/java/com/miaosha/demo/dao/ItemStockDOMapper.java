package com.miaosha.demo.dao;

import com.miaosha.demo.dataobject.ItemStockDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ItemStockDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemStockDO record);

    int insertSelective(ItemStockDO itemStockDO);

    ItemStockDO selectByItemId(Integer itemId);

    List<ItemStockDO> listItemStock(@Param("startItemId") Integer startItemId, @Param("endItemId") Integer endItemId);

    ItemStockDO selectByPrimaryKey(Integer id);

    int decreaseStock(@Param("itemId") Integer itemId,@Param("amount") Integer amount);

    int updateByPrimaryKeySelective(ItemStockDO record);

    int updateByPrimaryKey(ItemStockDO record);
}