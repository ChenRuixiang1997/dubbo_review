package com.self.learning.common.service;

import com.self.learning.common.dto.Goods;
import com.self.learning.common.dto.Orders;
import com.self.learning.common.vo.LoginUser;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/312:00
 * @Description TODO
 */
public interface UserService {
    //登录
    LoginUser login(LoginUser loginUser);

    //去订单表确认用户是否买过一件
    boolean isBrougth(LoginUser loginUser, Goods goods);

    //抢购一件
    Orders creatOrders(String phoneNum,Goods goods);

    //查询库存是否充足
    boolean goodsNumIsZero(Goods goods);
}
