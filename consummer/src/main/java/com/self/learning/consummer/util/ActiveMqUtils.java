package com.self.learning.consummer.util;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/915:31
 * @Description TODO
 */
@Component
public class ActiveMqUtils {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendQueueMsg(String name, Object msg) {
        Destination destination = new ActiveMQQueue(name);
//        Destination destinations = new ActiveMQTopic(name);
        jmsMessagingTemplate.convertAndSend(destination, msg);
    }

    public void sendTopicMsg(String name, Object msg) {
       Destination destinations = new ActiveMQTopic(name);
        jmsMessagingTemplate.convertAndSend(destinations, msg);
    }
}
