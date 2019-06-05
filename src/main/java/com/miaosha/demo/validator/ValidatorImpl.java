package com.miaosha.demo.validator;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 描述：对 Validator 进行实例化配置
 * @author wangyu
 * @date 2019/5/29
 */


@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;

    //实现校验方法并返回结果
    public ValidationResult validate(Object bean) {
        ValidationResult validationResult = new ValidationResult();
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(bean);
        if (constraintViolations.size() > 0) {
            //有错误
            validationResult.setHasError(true);
            constraintViolations.forEach(constraintViolation -> {
                String errMsg = constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();
                validationResult.getErrMsgMap().put(propertyName,errMsg);
            });
        }
        return validationResult;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //使hibernate validator通过工厂初始化进行实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
