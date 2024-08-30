package com.sparta.springintermediateasignment.weather;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WeatherDataDto {

    String date;
    String weather;
}
