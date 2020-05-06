package com.self.learning.consummer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.self.learning.common.service.OrdersService;
import com.self.learning.common.vo.LoginUser;
import com.self.learning.common.vo.OrdersVo;
import com.self.learning.consummer.conf.CurrentUser;
import com.self.learning.consummer.conf.LoginRequired;
import com.self.learning.consummer.util.ActiveMqUtils;
import com.self.learning.consummer.util.RedisUtils;
import com.self.learning.consummer.util.ReturnResults;
import com.self.learning.consummer.util.ReturnResultsUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/919:52
 * @Description TODO
 */
@RestController
@RequestMapping(value = "/orders")
public class OrdersController {
    @Reference
    private OrdersService ordersService;
    @Autowired
    private ActiveMqUtils activeMqUtils;
    @Autowired
    private RedisUtils redisUtils;

    String nameSpace = "BY_IT_NOW:";

    @ApiOperation(value = "抢购")
    @GetMapping(value = "/byItNow")
    @LoginRequired
    public ReturnResults byItNow(@RequestParam(name = "gId", required = true) String gId,
                                 @CurrentUser LoginUser loginUser) {
        OrdersVo ordersVo = new OrdersVo();
        String orderId = loginUser.getId() + "" + UUID.randomUUID();
        ordersVo.setoNo(orderId);
        ordersVo.setgId(gId);
        ordersVo.setPhonenum(loginUser.getUPhonneNumber());
        if (checkStatus(ordersVo.getoNo(), ordersVo)) {
            activeMqUtils.sendQueueMsg("createOrder", ordersVo);
            ordersService.creatOrders(ordersVo);
            redisUtils.deleteLock(ordersVo.getoNo());
            return ReturnResultsUtils.returnSuccesss(ordersVo);
        }
        return ReturnResultsUtils.returnFail(565, "不能再购买了");
    }

    @ApiOperation(value = "获取命名空间")
    @GetMapping(value = "/getKeys")
    public Set<String> getKeys() {
        return redisUtils.keys(nameSpace);
    }

    public boolean checkStatus(String oNo, OrdersVo ordersVo) {
        return redisUtils.checkFreq(oNo, 2, 600) && redisUtils.lock(oNo, ordersVo, 600) && ordersService.buyItNow(ordersVo);
    }
}
