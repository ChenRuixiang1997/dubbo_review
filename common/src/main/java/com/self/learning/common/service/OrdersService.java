package com.self.learning.common.service;

import com.self.learning.common.vo.OrdersVo;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/919:53
 * @Description TODO
 */
public interface OrdersService {
    //判断能否购买
    boolean buyItNow(OrdersVo ordersVo);
    //生成订单
    void creatOrders(OrdersVo ordersVo);
}
