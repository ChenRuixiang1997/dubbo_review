package com.self.learning.consummer.controller;

import com.alibaba.fastjson.JSON;
import com.self.learning.common.vo.Position;
import com.self.learning.consummer.conf.CurrentPosition;
import com.self.learning.consummer.enums.Person;
import com.self.learning.consummer.util.RedisUtils;
import com.self.learning.consummer.util.ReturnResults;
import com.self.learning.consummer.util.ReturnResultsUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/1010:32
 * @Description TODO
 */
@Configuration
@EnableScheduling
@Log4j
public class TestScheduleController {

    @Autowired
    private RedisUtils redisUtils;

    //@Scheduled(cron = "*/5 * * * * ?")
    /*public void getPositionSchedunle() {
        Object obj = redisUtils.get("position");
        if (!ObjectUtils.isEmpty(obj)) {
            String position = (String) redisUtils.get("position");
            System.out.println(position);
        }
    }*/


}
