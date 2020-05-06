package com.self.learning.provider.conf;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/1618:58
 * @Description TODO
 */
@Aspect
@Component
public class DataSourceSelectorComplete {

    @Before("com.self.learning.provider.conf.DataSourcePointcut.selectDataSourcePointcut()")
    public void changeDatsSource(JoinPoint joinPoint) throws NoSuchMethodException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Method realMethod = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(),method.getParameterTypes());
        DataSourceSelector dataSourceSelector = realMethod.getAnnotation(DataSourceSelector.class);
        if(null == dataSourceSelector){return;}
        MultipleDataSourceHelper.set(dataSourceSelector.value());
    }
}
