package com.sparta.springintermediateasignment.weather;

import com.sparta.springintermediateasignment.schedule.dto.DateDto;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j(topic = "WeatherService")
@Service
public class WeatherRestService {

    private final Map<String, String> weatherByDate = new HashMap<>();
    private final RestTemplate restTemplate = new RestTemplate();

    public String getWeatherByNow() {
        String now = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("MM-dd"));

        if(!valiateWeatherData(now)){
            throw new IllegalStateException(now + "에 해당하는 날씨 데이터가 존재하지 않습니다.");
        }

        return weatherByDate.get(now);
    }


    @PostConstruct
    private void init() {
        log.info("weather init");
        // 없으면 불러와서 캐싱
        String url = "https://f-api.github.io/f-api/weather.json";
        DateDto[] responseData = restTemplate.getForObject(url, DateDto[].class);
        if (responseData == null) {
            throw new IllegalStateException("weather data is null");
        }
        for (DateDto dateDto : responseData) {
            weatherByDate.put(dateDto.getDate(), dateDto.getWeather());
        }
    }

    private boolean valiateWeatherData(String date) {
        if(weatherByDate.isEmpty() || !weatherByDate.containsKey(date)) {
            return false;
        }

        return true;
    }

    @PreDestroy
    @CacheEvict(value = "weatherByDate")
    public void onDestroy() {
        log.info("weather destroy");
    }
}
