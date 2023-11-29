import models.lombok.*;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTest extends TestBase {

    @Test
    void listUsersTest() {
        ListUsersResponseModel response =
        given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().method()
                .when()
                .get("/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(ListUsersResponseModel.class);
        assertEquals(12, response.getTotal());
        assertEquals(7,response.getData().get(0).getId());
    }

    @Test
    void listResourceTest() {
        ListResourceResponseModel response= given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().method()
                .when()
                .get("/unknown")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(ListResourceResponseModel.class);
        assertEquals(2000, response.getData().get(0).getYear());
        assertEquals("#BF1932",response.getData().get(2).getColor());
    }

    @Test
    void createTest() {
        CreateBodyModel createBodyModel = new CreateBodyModel();
        createBodyModel.setName("morpheus");
        createBodyModel.setJob("leader");
        CreateResponseModel response = given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().method()
                .body(createBodyModel)
                .contentType(JSON)
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().as(CreateResponseModel.class);
        assertEquals("morpheus", response.getName());
        assertEquals("leader", response.getJob());
    }

    @Test
    void successfulRegisterTest() {
        SuccessfulRegisterBodyModel successfulRegisterBodyModel = new SuccessfulRegisterBodyModel();
        successfulRegisterBodyModel.setEmail("eve.holt@reqres.in");
        successfulRegisterBodyModel.setPassword("pistol");
        SuccessfulRegisterResponseModel response = given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().method()
                .body(successfulRegisterBodyModel)
                .contentType(JSON)
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(SuccessfulRegisterResponseModel.class);
        assertEquals(4, response.getId());
        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }

    @Test
    void unsuccessfulRegisterTest() {
        UnsuccessfulRegisterBodyModel unsuccessfulRegisterBodyModel = new UnsuccessfulRegisterBodyModel();
        unsuccessfulRegisterBodyModel.setEmail("sydney@fife");
        UnsuccessfulRegisterResponseModel response = given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().method()
                .body(unsuccessfulRegisterBodyModel)
                .contentType(JSON)
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .extract().as(UnsuccessfulRegisterResponseModel.class);
        assertEquals("Missing password", response.getError());
    }
}