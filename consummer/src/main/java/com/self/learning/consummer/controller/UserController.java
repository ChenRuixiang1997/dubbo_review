package com.self.learning.consummer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.self.learning.common.enums.UserErrorEnums;
import com.self.learning.common.exception.UserBussinessException;
import com.self.learning.common.service.GoodsService;
import com.self.learning.common.service.UserService;
import com.self.learning.common.vo.LoginUser;
import com.self.learning.consummer.util.RedisUtils;
import com.self.learning.consummer.util.ReturnResults;
import com.self.learning.consummer.util.ReturnResultsUtils;
import com.self.learning.common.utils.SecuritySHA1Utils;
import io.swagger.annotations.ApiOperation;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/312:14
 * @Description TODO
 */
@Log4j
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Reference
    private UserService userService;

    @Reference
    private GoodsService goodsService;

    @Autowired
    private ReturnResultsUtils returnResultsUtils;

    @Autowired
    private RedisUtils redisUtils;

    /* *
     * @Author: Ruixiang Chen
     * @Date: 2020/4/6 8:47
     * @Param: [nickName, password, httpServletRequest]
     * @Return: com.self.learning.consummer.util.ReturnResults
     * @Todo: 前端传用户名与密码
     */
    @ApiOperation(value = "登录", notes = "460表示登录失败，500表示成功")
    @PostMapping(value = "/login")
    public ReturnResults login(@RequestParam(name = "nickName", required = true) String nickName,
                               @RequestParam(name = "password", required = true) String password,
                               HttpServletRequest httpServletRequest) throws Exception {
        LoginUser loginUser = new LoginUser();
        loginUser.setUNickname(nickName);
        loginUser.setUPassword(SecuritySHA1Utils.shaEncode(password));
        //判断前端传的参数是否为空
        if (!ObjectUtils.isEmpty(loginUser)) {
            LoginUser user = userService.login(loginUser);
            String token = httpServletRequest.getSession().getId();
            //将token和用户信息存入缓存
            try {
                redisUtils.set(token, JSONObject.toJSONString(user));
                httpServletRequest.setAttribute("loginUser", user);
                return ReturnResultsUtils.returnSuccesss(token);
            } catch (Exception e) {
                e.printStackTrace();
                return ReturnResultsUtils.returnFail(3, "缓存失败！");
            }
        } else {
            //参数为空异常
            throw new UserBussinessException(UserErrorEnums.EMPTY_PARAM);
        }
    }

    @ApiOperation(value = "登出", notes = "998表示登出失败")
    @GetMapping(value = "/logout")
    public ReturnResults loginout(@RequestParam("token") String token) {
        try {
            redisUtils.del(token);
            return returnResultsUtils.returnSuccess();
        } catch (Exception e) {
            log.error("redis删除缓存失败");
            return returnResultsUtils.returnFail(998, "logout error");
        }
    }
}
