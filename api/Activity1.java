package activities;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import static org.hamcrest.CoreMatchers.equalTo;

public class Activity1 {

    private final String baseURI = "https://petstore.swagger.io/v2";

    @Test(priority = 1)
    public void addPet() {
        String requestBody = "{\n" +
                "  \"id\": 77232,\n" +
                "  \"name\": \"Riley\",\n" +
                "  \"status\": \"alive\"\n" +
                "}";

        given()
                .baseUri(baseURI)
                .contentType(ContentType.JSON)
                .body(requestBody).
        when()
                .post("/pet").
        then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(77232))
                .body("name", equalTo("Riley"))
                .body("status", equalTo("alive"));

    }

    @Test(priority = 2)
    public void getPet() {
        String petId = "77232";

        given()
                .baseUri(baseURI)
                .contentType(ContentType.JSON).
        when()
                .pathParam("petId", petId)
                .get("/pet/{petId}").
        then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(77232))
                .body("name", equalTo("Riley"))
                .body("status", equalTo("alive"));

    }

    @Test(priority = 3)
    public void deletePet() {
        String petId = "77232";

        given()
                .baseUri(baseURI)
                .contentType(ContentType.JSON).
        when()
                .pathParam("petId", petId)
                .delete("/pet/{petId}").
        then()
                .log().all()
                .body("code", equalTo(200))
                .body("message", equalTo("77232"));
    }
}
