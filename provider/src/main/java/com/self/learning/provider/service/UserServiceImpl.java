package com.self.learning.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.self.learning.common.dto.*;
import com.self.learning.common.enums.UserErrorEnums;
import com.self.learning.common.exception.UserBussinessException;
import com.self.learning.common.service.UserService;
import com.self.learning.common.vo.LoginUser;
import com.self.learning.provider.mapper.GoodsMapper;
import com.self.learning.provider.mapper.OrdersMapper;
import com.self.learning.provider.mapper.TestUserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/312:07
 * @Description TODO
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private TestUserMapper testUserMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public LoginUser login(LoginUser loginUser) {
        TestUserExample testUserExample = new TestUserExample();
        if (!ObjectUtils.isEmpty(loginUser)) {
            testUserExample.createCriteria().andUNicknameEqualTo(loginUser.getUNickname()).andUPasswordEqualTo(loginUser.getUPassword());
            List<TestUser> testUsers = testUserMapper.selectByExample(testUserExample);
            if (!testUsers.isEmpty()) {
                TestUser testUser = testUsers.get(0);
                BeanUtils.copyProperties(testUser, loginUser);
                return loginUser;
            }
            return null;
        }
        throw new UserBussinessException(UserErrorEnums.EMPTY_PARAM);
    }

    @Override
    public boolean isBrougth(LoginUser loginUser, Goods goods) {
        try {
            OrdersExample ordersExample = new OrdersExample();
            //已支付
            ordersExample.createCriteria().andPhonenumEqualTo(loginUser.getUPhonneNumber()).andGIdEqualTo(goods.getgId());
            List<Orders> orders = ordersMapper.selectByExample(ordersExample);
            if (!orders.isEmpty()){
                //表示不能购买
                return false;
            }else {
                //能够买
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Orders creatOrders(String phoneNum, Goods goods) {
        Orders orders = new Orders();
        Date now = new Date();
        String nowStr = now.toString();
        int length = nowStr.length();
        String oNo = phoneNum.substring(7,10)+goods.getgId()+ nowStr.substring(length-3,length);
        UUID.randomUUID();
        orders.setoNo(oNo);//订单号
        orders.setInvalid(1);//未失效
        orders.setoState(0);//未支付
        orders.setgId(goods.getgId());//商品id
        orders.setPhonenum(phoneNum);//手机号
        orders.setGoodNum(1);//商品数量
        try {
            ordersMapper.insertSelective(orders);
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean goodsNumIsZero(Goods goods) {
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andGIdEqualTo(goods.getgId());
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        if (!goodsList.isEmpty()){
            Goods goods1 = goodsList.get(0);
            if (goods1.getNumber()==0){
                return false;
            }else {
                return true;
            }
        }
        return false;
    }


}
