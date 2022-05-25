package activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static io.restassured.RestAssured.*;

import static org.hamcrest.CoreMatchers.equalTo;

public class Activity2 {

    private final String baseURI = "https://petstore.swagger.io/v2";

    @Test(priority = 1)
    public void addNewUser() {
        File file = new File("src/test/java/activities/userData.json");

        given()
                .baseUri(baseURI)
                .contentType(ContentType.JSON)
                .body(file).
        when()
                .post("/user").
        then()
                .log().all()
                .body("code", equalTo(200))
                .body("message", equalTo("7869"));
    }

    @Test(priority = 2)
    public void getUserData() {
        String userName = "james101";
        File userDataGetResponse = new File("src/test/java/activities/userDataGetResponse.json");

        Response response = given()
                                .baseUri(baseURI)
                                .contentType(ContentType.JSON).
                            when()
                                .pathParam("userName", userName)
                                .get("/user/{userName}");

        String responseBody = response.getBody().prettyPrint();

        try {
            FileUtils.writeStringToFile(userDataGetResponse, responseBody, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

        response.then()
                .body("id", equalTo(7869))
                .body("username", equalTo("james101"))
                .body("firstName", equalTo("James"))
                .body("lastName", equalTo("Bond"))
                .body("email", equalTo("jamesbond@mail.com"))
                .body("password", equalTo("password@123"))
                .body("phone", equalTo("9812763450"));

    }

    @Test(priority = 3)
    public void deleteUserData() {
        String userName = "james101";

        given()
                .baseUri(baseURI)
                .contentType(ContentType.JSON).
        when()
                .pathParam("userName", userName)
                .delete("/user/{userName}").
        then()
                .log().all()
                .body("code", equalTo(200))
                .body("message", equalTo("james101"));
    }
}
