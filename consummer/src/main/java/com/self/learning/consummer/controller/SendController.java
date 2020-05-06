package com.self.learning.consummer.controller;

import com.self.learning.consummer.util.ActiveMqUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/914:42
 * @Description TODO
 */
@RestController(value = "/send")
public class SendController {
    @Autowired
    private ActiveMqUtils activeMqUtils;

    @GetMapping(value = "/sendMsg")
    public void sendMsg(@RequestParam String msg) {
        activeMqUtils.sendQueueMsg("test", "testQueue");
    }

    @JmsListener(destination = "test")
    public String getMsg(String msg) {
        return msg;
    }

    @GetMapping(value = "/sendTopicMsg")
    public void sendTopicMsg(@RequestParam String msg) {
        activeMqUtils.sendTopicMsg("testTopicA", "testTopicA");
    }

    @JmsListener(destination = "testTopicA")
    public void getMsgA(String msg) {
        System.out.println("1." + msg);
    }

    @JmsListener(destination = "testTopicA")
    public void getMsgB(String msg) {
        System.out.println("2." + msg);

    }

    @JmsListener(destination = "testTopicA")
    public void getMsgC(String msg) {
        System.out.println("3." + msg);
    }

}
