
package org.se.weatherforecast.smhiweather;



import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Parameter {
    private String name;
    private String levelType;
    private int level;
    private String unit;
    private List<Double> values;


}
