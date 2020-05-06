package com.self.learning.provider.conf;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/1617:14
 * @Description TODO
 */
public class MultipleDataSourceHelper {
    public static final String MASTER = "master";
    public final static String SLAVE = "slave";

    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void set(String db) {
        contextHolder.set(db);
    }

    public static String get() {
        return contextHolder.get();
    }
}
