package com.self.learning.consummer.controller;

import com.alibaba.fastjson.JSON;
import com.self.learning.common.vo.Position;
import com.self.learning.consummer.enums.Person;
import com.self.learning.consummer.util.RedisUtils;
import com.self.learning.consummer.util.ReturnResults;
import com.self.learning.consummer.util.ReturnResultsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/1011:52
 * @Description TODO
 */
@RestController
@RequestMapping(value = "/position")
@Api(value = "位置")
public class PositionController {
    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "更新位置")
    @GetMapping(value = "/updatePosition")
    public void updatePosition(@Validated Position position, HttpServletRequest request){
        if(!ObjectUtils.isEmpty(position)){
            String jsonStr = JSON.toJSONString(position);
            redisUtils.set("position",jsonStr);
            request.setAttribute("position",position);
        }
    }

    @ApiOperation(value = "监听失效")
    @GetMapping(value = "/testExpireEvent")
    public void testExpireEvent(){
        redisUtils.set("key","value");
        redisUtils.expire("key",10);
    }

    @ApiOperation("枚举与switch")
    @GetMapping(value = "/testEnums")
    public ReturnResults testEnums(Person p) {
        switch (p) {
            case PERSON_A:
                return ReturnResultsUtils.returnSuccesss(p);
            case PERSON_B:
                return ReturnResultsUtils.returnDefined(111, "bbb", p);
            default:
                break;
        }
        return ReturnResultsUtils.returnFail(2121,"失败");
    }
}
