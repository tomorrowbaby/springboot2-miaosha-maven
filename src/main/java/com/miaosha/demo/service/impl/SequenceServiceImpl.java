package com.miaosha.demo.service.impl;

import com.miaosha.demo.dao.SequenceDOMapper;
import com.miaosha.demo.dataobject.SequenceDO;
import com.miaosha.demo.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 描述：Sequence接口实现类
 */

@Service
public class SequenceServiceImpl implements SequenceService {

    @Autowired
    SequenceDOMapper sequenceDOMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String generateOrderNo(){
        //订单号 16 位
        StringBuilder stringBuilder = new StringBuilder();
        //年月日    8
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        stringBuilder.append(nowDate);
        //自增序列  6

        int sequence = 0;
        //获取Sequence
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        sequence = sequenceDO.getCurrentValue();
        Integer nextSequence = sequenceDO.getCurrentValue()+sequenceDO.getStep() ;
        if (nextSequence >= 1000000) {
            sequenceDO.setCurrentValue(0);
            sequenceDOMapper.updateByPrimaryKey(sequenceDO);
        }
        sequenceDO.setCurrentValue(nextSequence);
        sequenceDOMapper.updateByPrimaryKey(sequenceDO);


        //拼接
        String sequenceStr = String.valueOf(sequence);
        for (int i = 0; i <6 - sequenceStr.length(); i++) {
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);

        //分库分表  2
        stringBuilder.append("00");

        return stringBuilder.toString();
    }

}
