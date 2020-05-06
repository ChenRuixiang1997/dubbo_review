package com.self.learning.consummer.controller;

import com.self.learning.consummer.util.RedisUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/1316:55
 * @Description TODO
 */
@RestController
@RequestMapping(value = "/redis")
public class HRedisController {

    @Autowired
    private RedisUtils redisUtils;

    String nameSpace = "TIME_TO_BUY:";

    @ApiOperation(value = "命名空间")
    @GetMapping(value = "/setKeys")
    public void setKeys(String str){
        redisUtils.set(nameSpace+str,"1");
    }

    @ApiOperation(value = "获取命名空间")
    @GetMapping(value = "/getKeys")
    public Set<String> getKeys(){
        return redisUtils.keys(nameSpace);
    }
}
