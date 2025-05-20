package org.se.weatherforecast.controller;


import org.se.weatherforecast.service.WeatherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class WeatherController {
    private WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public Mono<String> weather(Model model) {
        return weatherService.getOptimalWeather()
                .map(forecast -> {
                    model.addAttribute("forecast", forecast);
                    return "weatherForecast";
                });
    }
}
