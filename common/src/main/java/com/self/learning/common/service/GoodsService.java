package com.self.learning.common.service;

import com.self.learning.common.dto.Goods;
import com.self.learning.common.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/88:55
 * @Description TODO
 */
public interface GoodsService {

    List<Goods> selectGoodsList(String searchStr,int pageNo,int pageSize);

    Map showGoodsListMap(String searchStr,int pageNo,int pageSsize);

    Goods selectByGId(String gId);

    boolean reduceGoodsNumByGId(String gId);

    void like(String gId,String userId,Integer var);
}
