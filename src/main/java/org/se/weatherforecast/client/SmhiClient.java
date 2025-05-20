package org.se.weatherforecast.client;

import org.se.weatherforecast.model.Weather;

import org.se.weatherforecast.smhiweather.Parameter;
import org.se.weatherforecast.smhiweather.Smhi;

import org.se.weatherforecast.smhiweather.TimeSeries;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;


@Component
public class SmhiClient {
    private final WebClient webClient;

    private static final String SMHI_URL = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/18.0300/lat/59.3110/data.json";

    public SmhiClient(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    public Mono<Weather> getOptimizedWeather() {
        return webClient
                .get()
                .uri(SMHI_URL)
                .retrieve()
                .bodyToMono(Smhi.class)
                .map(this::extractSmhiWeather);
    }

    public Weather extractSmhiWeather(Smhi response) {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime targetTime = now.plusHours(24);

        TimeSeries closest = null;
        long smallestDiff = Long.MAX_VALUE;

        for (TimeSeries ts : response.getTimeSeries()) {
            ZonedDateTime forecastTime = ZonedDateTime.parse(ts.getValidTime());
            long diff = Math.abs(forecastTime.toEpochSecond() - targetTime.toEpochSecond());
            if (diff < smallestDiff) {
                smallestDiff = diff;
                closest = ts;
            }
        }

        if (closest != null) {
            Double temp = null;
            Integer humidity = null;

            for (Parameter p : closest.getParameters()) {
                if ("t".equals(p.getName())) {
                    temp = p.getValues().get(0);
                } else if ("r".equals(p.getName())) {
                    humidity = p.getValues().get(0).intValue();
                }
            }

            if (temp != null) {
                return new Weather("SMHI / Liljeholmen",
                        temp,
                        humidity,
                        closest.getValidTime());
            }
        }

        return null;
    }
}

