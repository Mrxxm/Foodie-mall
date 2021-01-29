package com.kenrou.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisOperator {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 返回key的剩余生存时间 单位秒
     * @param key
     * @return
     */
    public long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 设值过期时间 单位秒
     */
    public void expire(String key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 实现命令：INCR key 增加key一次
     * @param key
     * @param delta
     * @return
     */
    public long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 查找所有符合给定模式 pattern的 key
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 删除一个key
     * @param key
     */
    public void del(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 设置一个key-value
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置key-value和超时时间(秒)
     * @param key
     * @param value
     * @param timeout
     */
    public void set(String key, String value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取字符串
     * @param key
     * @return
     */
    public String get(String key) {
        return (String)redisTemplate.opsForValue().get(key);
    }

    /**
     * 哈希表设值
     * @param key
     * @param field
     * @param value
     */
    public void hset(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 获取值
     * @param key
     * @param field
     * @return
     */
    public String hget(String key, String field) {
        return (String)redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 删除
     * @param key
     * @param fields
     */
    public void hdel(String key, Object... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 获取所有的域和值
     * @param key
     * @return
     */
    public Map<Object, Object> hgetall(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public long lpush(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    public String lpop(String key) {
        return (String)redisTemplate.opsForList().leftPop(key);
    }

    public long rpush(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    public String rpop(String key) {
        return (String)redisTemplate.opsForList().rightPop(key);
    }
}
