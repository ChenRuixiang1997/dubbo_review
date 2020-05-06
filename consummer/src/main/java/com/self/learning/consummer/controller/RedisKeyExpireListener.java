package com.self.learning.consummer.controller;

import com.self.learning.consummer.util.ReturnResults;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/1017:14
 * @Description TODO
 */
@Component
public class RedisKeyExpireListener extends KeyExpirationEventMessageListener {

    public RedisKeyExpireListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        super.onMessage(message, pattern);
        /*
        * 在此添加监听到失效事件时触发的代码
        * */
    }

}
