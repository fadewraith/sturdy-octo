package com.journalappdemo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

//this part has some side effects
//@Service
//@Slf4j
//public class RedisService {
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    public <T> T get(String key, Class<T> entityClass) {
//        try {
//            Object o = redisTemplate.opsForValue().get(key);
//            ObjectMapper mapper = new ObjectMapper();
//            if(o == null) {
//                return null;
//            }
//            return mapper.readValue(o.toString(), entityClass);
//        } catch (Exception e) {
//            log.error("Exception ", e);
//            return null;
//        }
//    }
//
//    public void set(String key, Object o, Long ttl) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            String jsonValue = objectMapper.writeValueAsString(o);
//            redisTemplate.opsForValue().set(key, jsonValue, ttl, TimeUnit.SECONDS);
//        } catch (Exception e) {
//            log.error("Exception ", e);
//        }
//    }
//
//
//}

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public <T> T get(String key, Class<T> entityClass) {
        try {
            String json = redisTemplate.opsForValue().get(key);
            if (json == null) {
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, entityClass);
        } catch (Exception e) {
            log.error("Exception while retrieving from Redis", e);
            return null;
        }
    }

    public void set(String key, Object o, Long ttl) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonValue = objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key, jsonValue, ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Exception while setting to Redis", e);
        }
    }
}