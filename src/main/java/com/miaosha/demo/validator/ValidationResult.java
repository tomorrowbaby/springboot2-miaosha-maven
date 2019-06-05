package com.miaosha.demo.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：校验结果
 * @author wangyu
 * @date 2019/5/29
 */

public class ValidationResult {

    //判断校验是否有错
    private boolean hasError = false;

    //存放错误信息的map
    private Map<String,String> errMsgMap = new HashMap<>();

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public Map<String, String> getErrMsgMap() {
        return errMsgMap;
    }

    public void setErrMsgMap(Map<String, String> errMsgMap) {
        this.errMsgMap = errMsgMap;
    }

    //格式化字符串信息获得错误结果的msg方法
    public String getErrMsg(){
        return StringUtils.join(errMsgMap.values().toArray(),",");
    }
}
