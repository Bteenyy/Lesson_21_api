package spec;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;

public class UnsuccessfulRequestSpec {
    public static RequestSpecification unsuccessfulRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().method()
            .contentType(JSON);
    public static ResponseSpecification unsuccessfulResponseSpec = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .expectStatusCode(400)
            .build();

}
