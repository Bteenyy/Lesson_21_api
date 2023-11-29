package models.lombok;

import lombok.Data;

import java.util.List;

@Data
public class ListResourceResponseModel {
    int page,per_page,total,total_pages;
    Support support;
    List<DataI> data;
    @Data
    public static class Support
    {
        String url,text;
    }
    @Data
    public static class DataI{
        int id,year;
        String name, color,pantone_value;
    }
}
