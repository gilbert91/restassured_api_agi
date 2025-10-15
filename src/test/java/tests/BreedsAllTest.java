package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BreedsAllTest {

    @BeforeAll
    public static void setup() {
        RestAssured.filters(new AllureRestAssured());
        
        RestAssured.baseURI = "https://dog.ceo/api";
    }

    @Test
    public void validarListaBreeds() {

        Response response = given()
                .when()
                .get("/breeds/list/all")
                .then()
                .statusCode(200)
                .body("status", equalTo("success"))
                .body("message.keySet()[0]", equalTo("affenpinscher"))
                .body("message.size()", greaterThan(20))
                .body("message", hasKey("pug"))
                .body("message.terrier", hasItem("boston"))
                .extract()
                .response();
    }
}
