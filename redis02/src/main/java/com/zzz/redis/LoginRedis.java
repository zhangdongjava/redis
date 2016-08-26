package com.zzz.redis;

import com.zzz.dao.AbstractBaseRedisDao;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

/**
 * Created by dell_2 on 2016/8/26.
 */
@Component
public class LoginRedis extends AbstractBaseRedisDao {


    public Object adddUserSession(final String userName,final String password){
       return this.redisTemplate.execute(new RedisCallback<Boolean>() {
           RedisSerializer<String> seria = redisTemplate.getStringSerializer();
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] keys = seria.serialize(userName);
                byte[] userNames = seria.serialize(password);
                redisConnection.set(keys,userNames);
                return null;
            }
        });
    }

    public String getStrByKey(final String key){
        return (String) this.redisTemplate.execute(new RedisCallback<String>() {
            RedisSerializer<String> seria = redisTemplate.getStringSerializer();
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] keys = seria.serialize(key);
                return seria.deserialize(redisConnection.get(keys));
            }
        });
    }
}
