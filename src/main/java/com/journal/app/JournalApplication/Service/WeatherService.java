package com.journal.app.JournalApplication.Service;

import com.journal.app.JournalApplication.Cache.ApiCache;
import com.journal.app.JournalApplication.Constants.ApiConstants;
import com.journal.app.JournalApplication.Enums.ApiEnums;
import com.journal.app.JournalApplication.api.responsepojo.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Autowired
    private ApiCache apiCache;
    @Value("${weather-api-key}")
    private String API_KEY;
    private static String apiUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    private WeatherResponse weatherResponse = null;

    public WeatherResponse getWeather(String city){
        weatherResponse = redisService.get("Weather_of_"+city, WeatherResponse.class);
        if (weatherResponse!=null){
            return weatherResponse;
        }
        else {
            apiUrl = apiCache.apiConfig.get(ApiEnums.WEATHER_API_URL.toString());
            String finalApiUrl = apiUrl.replace(ApiConstants.API_KEY, API_KEY).replace(ApiConstants.CITY, city);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApiUrl,
                    HttpMethod.GET, null, WeatherResponse.class);
            weatherResponse = response.getBody();
            if (weatherResponse != null){
                redisService.set("Weather_of_" + city, weatherResponse, 300l);
            }
            return weatherResponse;
        }
    }
}
