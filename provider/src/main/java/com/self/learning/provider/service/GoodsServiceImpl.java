package com.self.learning.provider.service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.self.learning.common.dto.GoodLikeStatus;
import com.self.learning.common.dto.GoodLikeStatusExample;
import com.self.learning.common.dto.Goods;
import com.self.learning.common.dto.GoodsExample;
import com.self.learning.common.service.GoodsService;
import com.self.learning.common.utils.PageUtils;
import com.self.learning.provider.conf.DataSourceSelector;
import com.self.learning.provider.conf.DynamicDataSource;
import com.self.learning.provider.conf.MultipleDataSourceHelper;
import com.self.learning.provider.mapper.GoodLikeStatusMapper;
import com.self.learning.provider.mapper.GoodsMapper;
import com.self.learning.provider.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/88:57
 * @Description TODO
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

    private Jedis redisOps = new Jedis("localhost",6379);

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodLikeStatusMapper goodLikeStatusMapper;

    private GoodsExample goodsExample;

    String like_key = "Like_Key";

    String sum_like_key = "Sum_Like_Key";

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public List<Goods> selectGoodsList(String searchStr, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public Map showGoodsListMap(String searchStr, int pageNo, int pageSsize) {
        return null;
    }

    @Override
    @DataSourceSelector(value = MultipleDataSourceHelper.MASTER)
    public Goods selectByGId(String gId) {
        //MultipleDataSourceHelper.set(MultipleDataSourceHelper.SLAVE);
        Goods goods = null;
        goods = goodsMapper.selectByPrimaryKey(gId);
        if (!ObjectUtils.isEmpty(goods)){
            return goods;
        }
        return null;
    }

    @Override
    public boolean reduceGoodsNumByGId(String gId) {
        try {
            //减少库存
            Goods goodsFromData = goodsMapper.selectByPrimaryKey(gId);
            goodsFromData.setNumber(goodsFromData.getNumber()-1);
            goodsMapper.updateByPrimaryKey(goodsFromData);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /*
    * 点赞
    * */
    @Override
    public void like(String gId, String userId, Integer var) {
        if(isLiked(gId,userId)&&var == +1){
            throw new RuntimeException("Already liked!");
        }
        redisOps.lpush(like_key+":"+gId+":"+userId,String.valueOf(var));
        if(var == +1){
            redisOps.incrBy(sum_like_key+":"+gId,1);
        }else {
            redisOps.decrBy(sum_like_key+":"+gId,1);
        }
    }

    /*
    * 是否点过赞
    * */
    public boolean isLiked(String gId,String userId){
        boolean result = false;
        List<String> redsiVar = redisOps.lrange(like_key+":"+gId+":"+userId,0,-1);
        List<Integer> intVar = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(redsiVar)){
            redsiVar.forEach(v ->{
                intVar.add(Integer.valueOf(v));
            });
            if(intVar.stream().mapToInt(v -> v).sum()>0){
                result = true;
            }
        }else {
            GoodLikeStatusExample goodLikeStatusExample = new GoodLikeStatusExample();
            goodLikeStatusExample.createCriteria().andGIdEqualTo(gId).andUserIdEqualTo(userId);
            if(goodLikeStatusMapper.selectByExample(goodLikeStatusExample).stream().mapToInt(v -> v.getStatus()).sum()>0){
                result = true;
            }
        }
        return result;
    }

    /*
    * 更新数据库点赞数
    * */
    @Async
    @Scheduled(cron = "0 0 1 * * ?")
    public void saveInDB(){
        goodLikeStatusMapper.truncate();
        Set<String> keys = redisOps.keys("*"+like_key+"*");
        keys.forEach(key->{
            String[] var = key.split(":");
            GoodLikeStatus goodLikeStatus = new GoodLikeStatus();
            goodLikeStatus.setgId(var[1]);
            goodLikeStatus.setUserId(var[2]);
            List<String> lists = redisOps.lrange(key,0,-1);
            List<Integer> intLists = Lists.newArrayList();
            lists.forEach(l -> {
                intLists.add(Integer.valueOf(l));
            });
            goodLikeStatus.setStatus(intLists.stream().mapToInt(i->i).sum());
            goodLikeStatusMapper.insert(goodLikeStatus);
            String sum = redisOps.get(sum_like_key+":"+var[1]);
            Goods goods = new Goods();
            goods.setgId(var[1]);
            goods.setThumbs(Long.valueOf(sum));
            goodsMapper.updateByPrimaryKeySelective(goods);
        });
    }
}
