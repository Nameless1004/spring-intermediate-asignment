package com.sparta.springintermediateasignment.weather;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Slf4j(topic = "WeatherService")
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final Map<String, String> weatherByDate = new HashMap<>();

    private final WeatherAPI weatherAPI;

    public String getWeatherByNow() {
        String now = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("MM-dd"));

        if (!valiateWeatherData(now)) {
            throw new IllegalStateException(now + "에 해당하는 날씨 데이터가 존재하지 않습니다.");
        }

        return weatherByDate.get(now);

    }


    @PostConstruct
    private void init() {
        log.info("weather init");
        // 없으면 불러와서 캐싱
        WeatherDataDto[] weatherDtoList = weatherAPI.getWeather();

        if (weatherDtoList == null || weatherDtoList.length == 0) {
            throw new IllegalStateException("weather data is null");
        }

        for (WeatherDataDto weatherDataDto : weatherDtoList) {
            weatherByDate.put(weatherDataDto.getDate(), weatherDataDto.getWeather());
        }

        for (WeatherDataDto weatherDataDto : weatherDtoList) {
            System.out.println(weatherDataDto.toString());
        }
    }

    private boolean valiateWeatherData(String date) {
        return !weatherByDate.isEmpty() && weatherByDate.containsKey(date);
    }

    @PreDestroy
    @CacheEvict(value = "weatherByDate")
    public void onDestroy() {
        log.info("weather destroy");
    }
}
