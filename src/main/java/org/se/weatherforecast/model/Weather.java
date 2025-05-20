package org.se.weatherforecast.model;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Weather {
    private String origin;
    private double temp;
    private int humidity;
    private String timeStamp;
}
