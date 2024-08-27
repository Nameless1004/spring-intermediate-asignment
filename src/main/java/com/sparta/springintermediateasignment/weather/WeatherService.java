package com.sparta.springintermediateasignment.weather;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
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
        String weatherJson = weatherAPI.getWeather();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<WeatherDataDto> weatherDataListDto = objectMapper.readValue(weatherJson,
                new TypeReference<List<WeatherDataDto>>() {
                });
            if (weatherDataListDto == null || weatherDataListDto.isEmpty()) {
                throw new IllegalStateException("weather data is null");
            }

            for (WeatherDataDto weatherDataDto : weatherDataListDto) {
                weatherByDate.put(weatherDataDto.getDate(), weatherDataDto.getWeather());
            }
        } catch (Exception e) {
            throw new IllegalStateException("weather data is null");
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
