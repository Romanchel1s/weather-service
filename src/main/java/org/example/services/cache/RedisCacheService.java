package org.example.services.cache;

import redis.clients.jedis.Jedis;

public class RedisCacheService implements ICacheService {
    private final Jedis jedis;

    public RedisCacheService(String host, int port) {
        this.jedis = new Jedis(host, port);
    }

    @Override
    public String get(String key) {
        return jedis.get(key);
    }

    @Override
    public void set(String key, String value, int ttlSeconds) {
        jedis.setex(key, ttlSeconds, value);
    }

    @Override
    public void delete(String key) {
        jedis.del(key);
    }
}
