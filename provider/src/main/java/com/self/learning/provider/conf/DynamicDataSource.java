package com.self.learning.provider.conf;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/1617:11
 * @Description TODO
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return MultipleDataSourceHelper.get();
    }
}
