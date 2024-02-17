package com.zhifei.cloud.tools;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 手动校验实体类对象
 *
 * @author He Zhifei
 * @date 2020/12/31 11:44
 */
public class ValidatorTools {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 校验对象，校验分组为默认分组
     * @param obj 待校验的对象
     * @return 异常信息
     */
    public static Map validateModel(Object obj) {
        return validateModel(obj, Default.class);
    }

    /**
     * 校验对象，并指定校验分组
     * @param obj 待校验的对象
     * @param classes 分组类型
     * @return 异常信息
     */
    public static Map validateModel(Object obj, Class<?>... classes) {
        //验证某个对象
        Set<ConstraintViolation<Object>> constraintViolations = VALIDATOR.validate(obj, classes);
        Iterator<ConstraintViolation<Object>> iterator = constraintViolations.iterator();
        if (iterator != null && iterator.hasNext()) {
            ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
            do {
                ConstraintViolation<Object> next = iterator.next();
                if (next != null) {
                    map.put(next.getPropertyPath().toString(), next.getMessage());
                }
            } while (iterator.hasNext());
            return map;
        }
        return null;
    }

}
