package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.BeforeAll;

public class ByBreedTest {

    @BeforeAll
    public static void setup() {
        RestAssured.filters(new AllureRestAssured());
        
        RestAssured.baseURI = "https://dog.ceo/api";
    }

    @Test
    public void validarByBreed() {

        String breed = "hound";
        String subBreed = "afghan";

        Response response = given()
                .pathParam("breed", breed)
                .pathParam("subBreed", subBreed)
                .when()
                .get("/breed/{breed}/{subBreed}/images")
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("message", instanceOf(java.util.List.class)) 
                .body("message.size()", greaterThanOrEqualTo(10)) 
                .body("message", everyItem(
                    allOf(
                        startsWith("https://images.dog.ceo/breeds/"),
                        endsWith(".jpg")
                    )
                ))
                .body("message", hasItem(startsWith("https://images.dog.ceo/breeds/hound-afghan/n02088094")))
                .extract()
                .response();
    }
}
