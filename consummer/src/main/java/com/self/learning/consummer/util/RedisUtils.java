package com.self.learning.consummer.util;

import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;


    public boolean set(String key,Object value){
        try{
            redisTemplate.opsForValue().set(key,value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * String类型数据获取
     * @param key
     * @return object
     */
    public Object get(String key){
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }


    public void del(String... key){
        if(key != null && key.length>0){
            if(key.length==1){
                redisTemplate.delete(key[0]);
            }else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }


    /*
    * 设置key过期
    * */
    public boolean expire(String key,long time){
        try{
            redisTemplate.expire(key,time,TimeUnit.SECONDS);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /*
    * 递增
    * */
    public long incr(String key,long delta){
        if(delta<0){
            throw new RuntimeException("递增因子小于零");
        }else {
            return redisTemplate.opsForValue().increment(key,delta);
        }
    }

    /*
    * 递减
    * */
    public long decr(String key,long delta){
        if(delta<0){
            throw new RuntimeException("递减因子小于零");
        }else {
            return redisTemplate.opsForValue().increment(key,-delta);
        }
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /* *
     * @Author: Ruixiang Chen
     * @Date: 2020/4/10 8:50 
     * @Param: [key]
     * @Return: boolean
     * @Todo: 查询是否存在key
     */ 
    public boolean hasKey(String key){
        if(redisTemplate.hasKey(key)){
            return true;
        }else {
            return false;
        }
    }

    /* *
     * @Author: Ruixiang Chen
     * @Date: 2020/4/30 10:19
     * @Param: [key, value, expire]
     * @Return: boolean
     * @Todo: 添加锁
     */
    public boolean lock(String key, Object value,long expire){
        if(null == value){
            value = new byte[]{1};
        }
        if(redisTemplate.opsForValue().setIfAbsent(key,value)){
            expire(key,expire);
            return true;
        }
        return false;
    }

    /* *
     * @Author: Ruixiang Chen
     * @Date: 2020/4/30 10:19
     * @Param: [key]
     * @Return: void
     * @Todo: 删除锁
     */
    public void deleteLock(String key){
        redisTemplate.delete(key);
    }

    /* *
     * @Author: Ruixiang Chen
     * @Date: 2020/4/30 10:18
     * @Param: [nameSpace]
     * @Return: java.util.Set<java.lang.String>
     * @Todo: 获取命名空间的所有key
     */
    public Set<String> keys(String nameSpace){
        return redisTemplate.keys("*"+nameSpace+"*");
    }

    /* *
     * @Author: Ruixiang Chen
     * @Date: 2020/4/30 10:17
     * @Param: [key, count, ttl]
     * @Return: boolean
     * @Todo: 校验一段时间内次数
     */
    public boolean checkFreq(String key,long count,long ttl){
        boolean exist = redisTemplate.hasKey(key);
        BoundValueOperations<String,Object> valueOps = redisTemplate.boundValueOps(key);
        Long value = valueOps.increment(1);
        if(value == null){
            value = count;
        }
        if(!exist){
            redisTemplate.expire(key,ttl,TimeUnit.SECONDS);
        }
        return value<=count;
    }
}
