package com.miaosha.demo.dao;

import com.miaosha.demo.dataobject.UserInfoDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfoDO record);

    int insertSelective(UserInfoDO record);

    UserInfoDO selectByTelphone(String telphone);

    UserInfoDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfoDO record);

    int updateByPrimaryKey(UserInfoDO record);
}