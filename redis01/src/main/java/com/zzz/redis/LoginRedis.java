package com.zzz.redis;

import com.zzz.dao.AbstractBaseRedisDao;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell_2 on 2016/8/26.
 */
@Component
public class LoginRedis extends AbstractBaseRedisDao {


    public Object addUserSession(final String userName,final String password){
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

    public void setMap(final String key, final Map<String,String> map){
         this.redisTemplate.execute(new RedisCallback<Boolean>() {
            RedisSerializer<String> seria = redisTemplate.getStringSerializer();
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.hMSet(seria.serialize(key),mapStrToByte(map));
                return true;
            }
        });
    }
    public Map<String,String> getMap(final String key){
        Map<byte[],byte[]> map = (Map<byte[], byte[]>) this.redisTemplate.execute(new RedisCallback<Map<byte[],byte[]>>() {
            RedisSerializer<String> seria = redisTemplate.getStringSerializer();
            public Map<byte[],byte[]> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.hGetAll(seria.serialize(key));
            }
        });
        return mapByteToStr(map);

    }


    private Map<byte[],byte[]> mapStrToByte(Map<String,String> map){
        Map<byte[],byte[]> map2 = new HashMap<byte[], byte[]>();
        RedisSerializer<String> seria = redisTemplate.getStringSerializer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            map2.put(seria.serialize(entry.getKey()),seria.serialize(entry.getValue()));
        }
        return map2;
    }

    private Map<String,String> mapByteToStr( Map<byte[],byte[]> map){
        Map<String,String> map2 = new HashMap<String, String>();
        RedisSerializer<String> seria = redisTemplate.getStringSerializer();
        for (Map.Entry<byte[], byte[]> entry : map.entrySet()) {
            map2.put(seria.deserialize(entry.getKey()),seria.deserialize(entry.getValue()));
        }
        return map2;
    }

}
