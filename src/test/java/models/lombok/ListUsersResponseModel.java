package models.lombok;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class ListUsersResponseModel {
    @JsonProperty("per_page")
    int per_page;
    @JsonProperty("total_pages")
    int total_pages;
    int page, total;
    Support support;
    List<DataI> data;

    @Data
    public static class Support {
        String url, text;
    }

    @Data
    public static class DataI {
        int id;
        @JsonProperty("first_name")
        String first_name;
        @JsonProperty("last_name")
        String last_name;
        String email, avatar;
    }
}
