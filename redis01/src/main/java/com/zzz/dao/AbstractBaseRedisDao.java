package com.zzz.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * AbstractBaseRedisDao 
 * @author http://blog.csdn.net/java2000_wl  
 * @version <b>1.0</b>  
 */   
public abstract class AbstractBaseRedisDao<K, V> {  
      
    protected RedisTemplate<K, V> redisTemplate;

    /** 
     * 设置redisTemplate 
     * @param redisTemplate the redisTemplate to set 
     */
    @Autowired
    public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {  
        this.redisTemplate = redisTemplate;
    }  
      
    /** 
     * 获取 RedisSerializer 
     * <br>------------------------------<br> 
     */  
    protected RedisSerializer<String> getRedisSerializer() {
        return redisTemplate.getStringSerializer();  
    }


}