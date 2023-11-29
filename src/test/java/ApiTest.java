import models.lombok.*;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static spec.CreateSpec.createRequestSpec;
import static spec.CreateSpec.createResponseSpec;
import static spec.ListResourceSpec.listResourceRequestSpec;
import static spec.ListResourceSpec.listResourceResponseSpec;
import static spec.ListUserRequestSpec.listUserRequestSpec;
import static spec.ListUserRequestSpec.listUserResponseSpec;
import static spec.SuccessfulRegisterSpec.successfulRequestSpec;
import static spec.SuccessfulRegisterSpec.successfulResponseSpec;
import static spec.UnsuccessfulRequestSpec.*;

public class ApiTest extends TestBase {

    @Test
    void listUsersTest() {
        ListUsersResponseModel response = step("Make request", () ->
                given(listUserRequestSpec)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .spec(listUserResponseSpec)
                        .extract().as(ListUsersResponseModel.class));
        step("Verify responce", () -> {
            assertEquals(12, response.getTotal());
            assertEquals(7, response.getData().get(0).getId());
        });
    }

    @Test
    void listResourceTest() {
        ListResourceResponseModel response = step("Make request", () ->
                given(listResourceRequestSpec)
                        .when()
                        .get("/unknown")
                        .then()
                        .spec(listResourceResponseSpec)
                        .extract().as(ListResourceResponseModel.class));
        step("Verify responce", () -> {
            assertEquals(2000, response.getData().get(0).getYear());
            assertEquals("#BF1932", response.getData().get(2).getColor());
        });
    }

    @Test
    void createTest() {
        CreateBodyModel createBodyModel = new CreateBodyModel();
        createBodyModel.setName("morpheus");
        createBodyModel.setJob("leader");
        CreateResponseModel response = step("Make request", () ->
                given(createRequestSpec)
                        .body(createBodyModel)
                        .when()
                        .post("/users")
                        .then()
                        .spec(createResponseSpec)
                        .extract().as(CreateResponseModel.class));
        step("Verify responce", () -> {
            assertEquals("morpheus", response.getName());
            assertEquals("leader", response.getJob());
        });
    }

    @Test
    void successfulRegisterTest() {
        SuccessfulRegisterBodyModel successfulRegisterBodyModel = new SuccessfulRegisterBodyModel();
        successfulRegisterBodyModel.setEmail("eve.holt@reqres.in");
        successfulRegisterBodyModel.setPassword("pistol");
        SuccessfulRegisterResponseModel response = step("Make request", () ->
                given(successfulRequestSpec)
                        .body(successfulRegisterBodyModel)
                        .when()
                        .post("/register")
                        .then()
                        .spec(successfulResponseSpec)
                        .extract().as(SuccessfulRegisterResponseModel.class));
        step("Verify responce", () -> {
            assertEquals(4, response.getId());
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
        });
    }

    @Test
    void unsuccessfulRegisterTest() {
        UnsuccessfulRegisterBodyModel unsuccessfulRegisterBodyModel = new UnsuccessfulRegisterBodyModel();
        unsuccessfulRegisterBodyModel.setEmail("sydney@fife");
        UnsuccessfulRegisterResponseModel response = step("Make request", () ->
                given(unsuccessfulRequestSpec)
                        .body(unsuccessfulRegisterBodyModel)
                        .when()
                        .post("/register")
                        .then()
                        .spec(unsuccessfulResponseSpec)
                        .extract().as(UnsuccessfulRegisterResponseModel.class));
        step("Verify responce", () ->
                assertEquals("Missing password", response.getError()));
    }
}