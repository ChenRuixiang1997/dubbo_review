package com.self.learning.provider.conf;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/1619:09
 * @Description TODO
 */
@Component
public class DataSourcePointcut {
    @Pointcut("execution(public * com.self.learning.provider.service.*.*(..))")
    public void selectDataSourcePointcut(){

    }
}
