package com.self.learning.consummer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.self.learning.common.service.WxService;
import com.self.learning.common.utils.HttpClientUtils;
import com.self.learning.common.vo.LoginUser;
import com.self.learning.consummer.util.RedisUtils;
import com.self.learning.consummer.wx.WxConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/611:04
 * @Description TODO
 */
//不能用RestController
@Controller
@RequestMapping(value = "wx")
public class WxController {

    @Autowired
    private WxConfig wxConfig;
    @Autowired
    private RedisUtils redisUtils;

    @Reference
    private WxService wxService;

    @GetMapping(value = "/wxLogin")
    public String wxLogin() {
        String codeUri = wxConfig.reqCodeUrl();
        return "redirect:" + codeUri;
    }

    @GetMapping(value = "callBack")
    public String callBack(String code) throws IOException {
        if (null != code) {
            String accessTokenStr = HttpClientUtils.doGet(wxConfig.reqAccessTokenUri(code));
            JSONObject jsonObject = JSON.parseObject(accessTokenStr);
            String accessToken = jsonObject.getString("access_token");
            String openId = jsonObject.getString("openid");
            String userInfo = HttpClientUtils.doGet(wxConfig.reqUserInfo(accessToken,openId));
            LoginUser loginUser = JSONObject.parseObject(userInfo,LoginUser.class);
            String user = JSON.toJSONString(loginUser);
            redisUtils.set("loginUser",user);
            return "redirect:"+wxConfig.getLoginSuccess()+userInfo;
        }
        return "redirect:"+wxConfig.getLoginFailure();
    }
}
