import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

import static org.hamcrest.CoreMatchers.equalTo;

public class RestAssuredProject {

    private RequestSpecification requestSpecification;
    //since my SSH public key contains \(backslashes), storing it as String causes the next character to escape, making the SSH key invalid
    //therefore, I'm not storing the SSH key in the below given variable('SSHPublicKey'), instead I'm storing it in separate JSON file.
    private String SSHPublicKey;
    private int id;

    @BeforeClass
    public void setup() {
        //build request specification
        requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "token ghp_XXXXXXXXXXXXXXXXXXXXXXXXX")
                .setBaseUri("https://api.github.com").build();
    }

    @Test(priority = 1)
    public void addSSHKey() {
        //initialize the JSON request file object
        File file = new File("src/test/java/addSSHKeyRequestBody.json");

        try {
            //read JSON request file and store the request as String
            String requestBody = FileUtils.readFileToString(file, Charset.defaultCharset());

            System.out.println("request body: "+requestBody);

            //send POST request
            Response response = given()
                                    .spec(requestSpecification)
                                    .body(requestBody).
                                when()
                                    .post("/user/keys");

            //get the value of 'id' from response and store it in a variable 'id'
            id = response.body().path("id");

            //log the response to console and assert the status code
            response.then()
                    .log().all()
                    .statusCode(equalTo(201));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 2)
    public void getSSHKey() {
        //send GET request
        Response response = given()
                                .spec(requestSpecification).
                            when()
                                .get("/user/keys");

        //log the response to console and assert the status code
        response.then()
                .log().all()
                .statusCode(equalTo(200));

    }

    @Test(priority = 3)
    public void deleteSSHKey() {
        //send DELETE request
        Response response = given()
                                .spec(requestSpecification).
                            when()
                                .pathParam("keyId", id)
                                .delete("/user/keys/{keyId}");

        //log the response to console and assert the status code
        response.then()
                .log().all()
                .statusCode(equalTo(204));
    }
}
