package com.miaosha.demo.dao;

import com.miaosha.demo.dataobject.ItemDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ItemDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemDO record);

    int insertSelective(ItemDO record);

    List<ItemDO> listItem();

    ItemDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemDO record);

    int updateByPrimaryKey(ItemDO record);

    int increaseSales(@Param("id") Integer id, @Param("amount") Integer amount);
}