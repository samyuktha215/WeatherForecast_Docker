package org.se.weatherforecast.controller;

import org.se.weatherforecast.model.Weather;
import org.se.weatherforecast.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class WeatherRestController {
    private final WeatherService weatherService;

    public WeatherRestController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }
    @GetMapping("/forecast")
    public Mono<Weather> getWeather() {
        return weatherService.getOptimalWeather();
    }
}
