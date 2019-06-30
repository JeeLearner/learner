package com.jee.web.aspect;

import com.jee.common.exception.GlobalException;
import com.jee.common.result.CodeMsg;
import com.jee.web.filter.SQLFilter;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Description: Aspect处理sql注入问题
 * @Auther: jeeLearner
 * @Version:v1.0
 */
@Aspect
@Component
public class OrderByFilterAspect {

    @Pointcut("@annotation(com.jee.web.annotation.OrderByFilter)")
    public void pointCut(){

    }

    @Around("pointCut()")
    public void aroud(ProceedingJoinPoint joinPoint) throws Exception {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();

        Object[] args = joinPoint.getArgs();
        //参数获取
        Map<String, Object> params = (Map<String, Object>) args[0];
        String field = (String) params.get("field");
        String sortKey = (String) params.get("sortKey");

        if (StringUtils.isBlank(sortKey)){
            sortKey = SortKeyEnum.ASC.getValue();
        }
        boolean flag = SortKeyEnum.ASC.getValue().equalsIgnoreCase(sortKey) || SortKeyEnum.DESC.getValue().equalsIgnoreCase(sortKey);
        if(!flag){
            throw new GlobalException(CodeMsg.COMMON_CHAR_ILLEGAL);
        }
        //防止SQL注入（因为field、orderKey是通过拼接SQL实现排序的，会有SQL注入风险）
        field = SQLFilter.sqlInject(field);
        sortKey = SQLFilter.sqlInject(sortKey);
        params.put("field", field);
        params.put("sortKey", sortKey);

        Class<?> forName = Class.forName(className);
        Object newInstance = forName.newInstance();
        method.invoke(newInstance, params);
    }

}

