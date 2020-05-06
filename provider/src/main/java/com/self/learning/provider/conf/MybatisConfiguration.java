package com.self.learning.provider.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = "com.self.learning.provider.mapper")
public class MybatisConfiguration {
}
