package com.miaosha.demo.service;

/**
 * 描述：本地热点缓存
 * @author wangyu
 * @date 2019/7/25
 */
public interface CacheService {

    //将热点数据key，value存入到本地内存
    void setCommonCache(String key,Object value);

    //取方法
    Object getFromCommonCache(String key);
}
