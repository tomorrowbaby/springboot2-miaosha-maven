package com.miaosha.demo.dao;

import com.miaosha.demo.dataobject.OrderDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDOMapper {
    int deleteByPrimaryKey(String id);

    int insert(OrderDO record);

    int insertSelective(OrderDO record);

    OrderDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderDO record);

    int updateByPrimaryKey(OrderDO record);
}