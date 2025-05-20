
package org.se.weatherforecast.smhiweather;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Getter
@Setter
public class Smhi {
    private String approvedTime;
    private String referenceTime;
    private Geometry geometry;
    private List<TimeSeries> timeSeries;


}
