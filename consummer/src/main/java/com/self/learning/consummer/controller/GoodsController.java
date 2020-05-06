package com.self.learning.consummer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.self.learning.common.dto.Goods;
import com.self.learning.common.service.GoodsService;
import com.self.learning.common.utils.PageUtils;
import com.self.learning.common.vo.GoodsVo;
import com.self.learning.common.vo.LoginUser;
import com.self.learning.consummer.conf.CurrentUser;
import com.self.learning.consummer.conf.LoginRequired;
import com.self.learning.consummer.util.RedisUtils;
import com.self.learning.consummer.util.ReturnResults;
import com.self.learning.consummer.util.ReturnResultsUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/88:54
 * @Description TODO
 */
@RestController
public class GoodsController {

    @Autowired
    private RedisUtils redisUtils;

    @Reference
    private GoodsService goodsService;

    //展示商品(模糊查询/分页)
    @ApiOperation(value = "展示商品")
    @GetMapping(value = "/queryGoods")
    public PageUtils<List<GoodsVo>> queryGoods(String searchStr, Integer pageNo, Integer pageSize) {
        PageUtils pageUtils = new PageUtils();
        pageUtils.setPageNo(pageNo);
        Map<String, Object> map = goodsService.showGoodsListMap(searchStr, pageUtils.getPageNo(), pageSize);
        pageUtils.setTotalCount((long) map.get("count"));
        pageUtils.setCurrentPage(pageNo);
        return pageUtils;

    }

    //商品详情
    @ApiOperation(value = "商品详情")
    @GetMapping(value = "/goodsDetails")
    public ReturnResults goodsDetails(String gId) {
        Goods goods = goodsService.selectByGId(gId);
        if (!ObjectUtils.isEmpty(goods)) {
            return ReturnResultsUtils.returnSuccesss(goods);
        }
        return ReturnResultsUtils.returnFail(234, "查询出错");
    }

    //点赞
    @ApiOperation(value = "点赞")
    @GetMapping(value = "/like")
    @LoginRequired
    public ReturnResults like(@CurrentUser LoginUser loginUser, @RequestParam String gId, @RequestParam int var) {
        if(!redisUtils.checkFreq("ifRequestBusy@"+loginUser.getUId(),2,3)){
            return ReturnResultsUtils.returnFail(111,"点赞频繁!");
        }
        goodsService.like(gId, loginUser.getUId(), var);
        return ReturnResultsUtils.returnSuccess();
    }
}
