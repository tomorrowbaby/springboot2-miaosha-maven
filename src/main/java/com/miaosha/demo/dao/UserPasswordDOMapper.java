package com.miaosha.demo.dao;

import com.miaosha.demo.dataobject.UserPasswordDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserPasswordDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPasswordDO record);

    int insertSelective(UserPasswordDO record);

    UserPasswordDO selectByUserId(Integer id);

    UserPasswordDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserPasswordDO record);

    int updateByPrimaryKey(UserPasswordDO record);
}