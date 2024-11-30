package com.journalappdemo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.journalappdemo.api.response.WeatherResponse;
import com.journalappdemo.cache.AppCache;
import com.journalappdemo.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Service
public class WeatherService {


    @Value("${weather.api.key}")
//    private static String apiKey; // error, cause bean doest touch static variables, coz static variable is related to class not to instance
    private String apiKey;
//    @Value("${weather.api.url}")
//    private String API;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTemplate<String, String > redisTemplate;

    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if(weatherResponse != null) {
            return weatherResponse;
        } else {
            String url = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.CITY, city).replace(Placeholders.API_KEY, apiKey);
//        null = request entity like headers, response type =

//        demo of post call using rest template
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("key", "value");
//        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, httpHeaders);
//        String requestBody = "USERNAME\t\n" +
//                "john\n" +
//                "PASSWORD\t\n" +
//                "Test@123";
//        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody);
//        ResponseEntity<WeatherResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, WeatherResponse.class);
//        demo of post ends
            ResponseEntity<WeatherResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = responseEntity.getBody();
            if (body != null) {
//                try {
//                    ObjectMapper mapper = new ObjectMapper();
//                    String jsonValue = mapper.writeValueAsString(body);
//                    redisTemplate.opsForValue().set("weather_of_" + city, jsonValue, 300L, TimeUnit.SECONDS);
//                } catch (JsonProcessingException e) {
//                    throw new RuntimeException(e);
//                }
                redisService.set("weather_of_" + city, body, 300L);
            }
            return body;
        }
    }
}
