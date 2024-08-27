package com.sparta.springintermediateasignment.weather;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name ="weather", url = "https://f-api.github.io/f-api/weather.json", configuration = WeatherAPIConfig.class)
public interface WeatherAPI {
    @GetMapping
    String getWeather();
}
