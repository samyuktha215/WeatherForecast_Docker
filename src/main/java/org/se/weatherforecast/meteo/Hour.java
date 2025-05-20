package org.se.weatherforecast.meteo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Hour {
    private List<String> time;
    private List<Double> temperature_2m;
    private List<Integer> relative_humidity_2m;
}
