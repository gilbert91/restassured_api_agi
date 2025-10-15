package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.BeforeAll;

public class RandomImageTest {

    @BeforeAll
    public static void setup() {
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = "https://dog.ceo/api";
    }

    @Test
    public void validarImageRandom() {

        String breed = "sheepdog";
        String subBreed = "english";
        
        String urlRegex = String.format(
            "https:\\/\\/images\\.dog\\.ceo\\/breeds\\/%s-%s\\/[a-zA-Z0-9_\\-]+\\.jpg",
            breed, subBreed
        );

        Response response = given()
                .pathParam("breed", breed)
                .pathParam("subBreed", subBreed)
                .when()
                .get("/breed/{breed}/{subBreed}/images/random")
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("message", instanceOf(String.class))
                .body("message", startsWith("https://images.dog.ceo/breeds/"))
                .body("message", endsWith(".jpg"))
                .body("message", matchesRegex(urlRegex)) 
                .extract()
                .response();
    }
}
