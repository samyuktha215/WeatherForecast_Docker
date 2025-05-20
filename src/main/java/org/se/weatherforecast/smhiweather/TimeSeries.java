package org.se.weatherforecast.smhiweather;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class TimeSeries {
    private String validTime;
    private List<Parameter> parameters;


}
