package models.lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListResourceResponseModel {
    List<DataI> data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataI {
        int year;
        String color;
    }
}
