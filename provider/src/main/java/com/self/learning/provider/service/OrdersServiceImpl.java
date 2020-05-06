package com.self.learning.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.self.learning.common.dto.Goods;
import com.self.learning.common.dto.Orders;
import com.self.learning.common.dto.OrdersExample;
import com.self.learning.common.service.OrdersService;
import com.self.learning.common.vo.OrdersVo;
import com.self.learning.provider.mapper.GoodsMapper;
import com.self.learning.provider.mapper.OrdersMapper;
import com.self.learning.provider.utils.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/919:54
 * @Description TODO
 */
@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean buyItNow(OrdersVo ordersVo) {
        Goods goods = goodsMapper.selectByPrimaryKey(ordersVo.getgId());
        //库存是否足够
        if (goods.getNumber() > 0) {
            //用户是否买过
            OrdersExample ordersExample = new OrdersExample();
            ordersExample.createCriteria().andGIdEqualTo(ordersVo.getgId()).andPhonenumEqualTo(ordersVo.getPhonenum());
            List<Orders> ordersList = ordersMapper.selectByExample(ordersExample);
            if (ObjectUtils.isEmpty(ordersList)) {//没有这个订单
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    @JmsListener(destination = "createOrder")
    public void creatOrders(OrdersVo ordersVo) {
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersVo, orders);
        if (redisUtils.hasKey(ordersVo.getoNo())) {
            ordersMapper.insertSelective(orders);
            //修改库存
            goodsMapper.reduceCount(1, ordersVo.getgId());
        }
    }
}
