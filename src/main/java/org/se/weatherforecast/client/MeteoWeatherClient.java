package org.se.weatherforecast.client;

import org.se.weatherforecast.meteo.MeteoWeather;
import org.se.weatherforecast.model.Weather;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Component
public class MeteoWeatherClient {
    private WebClient webClient;

    public MeteoWeatherClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.open-meteo.com")
                .build();
    }
    public Mono<Weather> getOpenWeather() {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/forecast")
                        .queryParam("latitude", 59.31)
                        .queryParam("longitude", 18.03)
                        .queryParam("hourly", "temperature_2m,relative_humidity_2m")
                        .queryParam("timezone", "auto") // ensure correct time matching
                        .build())
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(MeteoWeather.class)
                                .map(res -> {
                                    List<String> times = res.getHourly().getTime();
                                    List<Double> temps = res.getHourly().getTemperature_2m();
                                    List<Integer> humidity = res.getHourly().getRelative_humidity_2m();

                                    ZonedDateTime now = ZonedDateTime.now();
                                    ZonedDateTime target = now.plusHours(24);

                                    int bestIndex = 0;
                                    long smallestDiff = Long.MAX_VALUE;

                                    for (int i = 0; i < times.size(); i++) {
                                        LocalDateTime forecastTime = LocalDateTime.parse(times.get(i)); // no timezone
                                        ZonedDateTime forecastZoned = forecastTime.atZone(ZoneId.systemDefault());

                                        long diff = Math.abs(forecastZoned.toEpochSecond() - target.toEpochSecond());
                                        if (diff < smallestDiff) {
                                            smallestDiff = diff;
                                            bestIndex = i;
                                        }
                                    }

                                    return new Weather(
                                            "Meteo / Liljeholmen",
                                            temps.get(bestIndex),
                                            humidity.get(bestIndex),
                                            times.get(bestIndex)
                                    );
                                });


                    } else {
                        return response.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    System.err.println("Error Response Body: " + errorBody);
                                    return Mono.error(new RuntimeException("API error: " + errorBody));
                                });
                    }
                });
    }

}
