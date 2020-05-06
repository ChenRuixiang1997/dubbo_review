package com.self.learning.consummer.conf;

import com.alibaba.fastjson.JSONObject;
import com.self.learning.common.vo.LoginUser;
import com.self.learning.consummer.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class LoginReqComplete implements HandlerInterceptor {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*
         * 如果不是控制器方法就返回
         * */
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        /*
         * 是控制器方法就强转，并且获取方法
         * */
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        /*
         * 获取@LoginRequired注解,如果注解不为@LoginRequired注解,methodAnnotation接收值为null
         * */
        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);
        if (null != methodAnnotation) {
            /*
             * 获取请求头中的"token"
             * */
            String token = request.getHeader("token");
            String tokenWx = request.getHeader("tokenWx");
            if (!StringUtils.isEmpty(token)) {
                /*
                 * 如果存在"token",再去redis缓存中找"token"是否存在且对应一致，若存在且对应一致就在request作用域中将登录用户信息存入
                 * */
                String userToken = (String) redisUtils.get(token);
                String wxToken = (String) redisUtils.get(tokenWx);
                if (!StringUtils.isEmpty(userToken)) {
                    LoginUser loginUser = JSONObject.parseObject(userToken, LoginUser.class);
                    request.setAttribute("loginUser", loginUser);

                } else if (!StringUtils.isEmpty(wxToken)) {
                    LoginUser loginWx = JSONObject.parseObject(wxToken, LoginUser.class);
                    request.setAttribute("loginUser", loginWx);
                } else {
                    /*
                     * 如果redis缓存中没有"token"就抛出运行时异常"login error"
                     * */
                    throw new RuntimeException("login error");
                }
            } else {
                throw new RuntimeException("login error");
            }
            return true;
        }
        //明天讲为啥把fals改成true
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
