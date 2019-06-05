package com.miaosha.demo.dao;

import com.miaosha.demo.dataobject.SequenceDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SequenceDOMapper {
    int deleteByPrimaryKey(Integer step);

    int insert(SequenceDO record);

    int insertSelective(SequenceDO record);

    SequenceDO getSequenceByName(String name);

    SequenceDO selectByPrimaryKey(Integer step);

    int updateByPrimaryKeySelective(SequenceDO record);

    int updateByPrimaryKey(SequenceDO record);
}