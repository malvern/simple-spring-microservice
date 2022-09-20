package zw.co.malvern.contactservice.utils.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Geolocation {

    @JsonProperty(value = "lat")
    private String latitude;
    @JsonProperty(value = "lng")
    private String longitude;
}
