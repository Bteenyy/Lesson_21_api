package models.lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListUsersResponseModel {
    int total;
    List<DataI> data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataI {
        int id;
    }
}
