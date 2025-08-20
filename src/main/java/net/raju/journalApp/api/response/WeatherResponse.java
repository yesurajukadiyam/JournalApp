package net.raju.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponse {

    private Current current;

    @Getter
    @Setter
    public class Current{

        @JsonProperty("temp_c")
        private double temperature;

        @JsonProperty("feelslike_c")
        private double feelslike;
    }
}
