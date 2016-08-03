package com.zzz.dao.impl;

import com.zzz.dao.AbstractBaseRedisDao;
import com.zzz.dao.UserDao;
import com.zzz.pojo.User;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by dell_2 on 2016/8/3.
 */

public class UserDaoImpl extends AbstractBaseRedisDao<String, User> implements UserDao {
    public boolean add(final  User user) {
        final String key1 = (new StringBuilder("RKT:JFT")).toString();
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                System.out.println(key1);
                byte[] key  = serializer.serialize(key1);
                byte[] id = serializer.serialize(user.getId().toString());
                connection.zAdd(key, user.getPayNum(),id);
                return true;
            }
        });
        return result;
    }

    public List<User> list(){
        List<User> list = new ArrayList<User>();
        final String key = "RKT:JFT";
        Set<RedisZSetCommands.Tuple> result = redisTemplate.execute(new RedisCallback<Set<RedisZSetCommands.Tuple>>() {
            public Set<RedisZSetCommands.Tuple> doInRedis(RedisConnection connection) throws DataAccessException {
                // TODO Auto-generated method stub
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] keys = serializer.serialize(key);
                Set<RedisZSetCommands.Tuple> values = connection.zRevRangeWithScores(keys, 0, -1);
                return values;
            }
        });
        RedisSerializer<String> serializer = getRedisSerializer();
        for (RedisZSetCommands.Tuple tuple : result) {
            User user = new User();
            user.setPayNum(tuple.getScore());
            String idStr = serializer.deserialize(tuple.getValue());
            user.setId(Integer.valueOf(idStr));
            list.add(user);
        }
        return list;
    }
}
