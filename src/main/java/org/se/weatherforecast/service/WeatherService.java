package org.se.weatherforecast.service;

import org.se.weatherforecast.client.MeteoWeatherClient;
import org.se.weatherforecast.client.SmhiClient;
import org.se.weatherforecast.model.Weather;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class WeatherService {
    private final SmhiClient smhiClient;
    private final MeteoWeatherClient meteoWeatherClient;


    public WeatherService(SmhiClient smhiClient, MeteoWeatherClient openWeatherClient) {
        this.smhiClient = smhiClient;
        this.meteoWeatherClient = openWeatherClient;
    }

    public Mono<Weather> getOptimalWeather() {
        Mono<Weather> smhi=smhiClient.getOptimizedWeather();
        Mono<Weather> meteo =meteoWeatherClient.getOpenWeather();
        return Mono.zip(smhi,meteo)
                .map(tuple ->{
                    Weather smhiWeather=tuple.getT1();
                    Weather meteoWeather=tuple.getT2();

                    double smhiWeatherTemp=smhiWeather.getTemp();
                    double meteoWeatherTemp=meteoWeather.getTemp();

                    return smhiWeatherTemp>meteoWeatherTemp?smhiWeather:meteoWeather;

                });

    }
}
