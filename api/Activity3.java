package activities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity3 {

    private RequestSpecification requestSpecification;
    private ResponseSpecification responseSpecification;

    @BeforeClass
    public void setup() {
        String baseURI = "https://petstore.swagger.io/v2";

        requestSpecification = new RequestSpecBuilder().
                                    setContentType(ContentType.JSON).
                                    setBaseUri(baseURI).build();

        responseSpecification = new ResponseSpecBuilder().
                                    expectStatusCode(200).
                                    expectContentType(ContentType.JSON).
                                    expectBody("status", equalTo("alive")).build();
    }

    @Test(priority = 1)
    public void addPets() {
        String requestBody1 = "{\"id\": 77232, \"name\": \"Riley\", \"status\": \"alive\"}";
        String requestBody2 = "{\"id\": 77233, \"name\": \"Hansel\", \"status\": \"alive\"}";

        //post request1
        given()
                .spec(requestSpecification)
                .body(requestBody1).
        when()
                .post("/pet").
        then()
                .log().all()
                .spec(responseSpecification);

        //post request2
        given()
                .spec(requestSpecification)
                .body(requestBody2).
        when()
                .post("/pet").
        then()
                .log().all()
                .spec(responseSpecification);
    }

    @DataProvider
    public Object[][] petDataProvider() {
        return new Object[][]{
                { 77232, "Riley", "alive" },
                { 77233, "Hansel", "alive" }
        };
    }

    @Test(priority = 2, dataProvider = "petDataProvider")
    public void getPets(int id, String name, String status) {
        given()
                .spec(requestSpecification).
        when()
                .pathParam("petId", id)
                .get("/pet/{petId}").
        then()
                .log().all()
                .spec(responseSpecification)
                .body("name", equalTo(name));

    }

    @Test(priority = 3, dataProvider = "petDataProvider")
    public void deletePets(int id, String name, String status) {
        given()
                .spec(requestSpecification).
        when()
                .pathParam("petId", id)
                .delete("/pet/{petId}").
        then()
                .log().all()
                .body("code", equalTo(200));
    }
}
