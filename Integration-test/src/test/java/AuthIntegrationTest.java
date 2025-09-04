import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class AuthIntegrationTest {
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:4004";
    }

    // Add your test methods here
    @Test
    public void shouldReturnOKWithValidToken() {
        //Steps for writing a test case
        //1. arrange
        String loginPayload = """
                {
                   "email": "testuser@test.com",
                   "password": "password123"
                }
                """;
        //2. act
        Response response= given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
        //3. assert
                .statusCode(200)
                .body("token", notNullValue())
                .extract().response();

        System.out.println("Response: " + response.asString());
        System.out.println("Generated Token: " + response.jsonPath().getString("token"));
    }

    @Test
    public void shouldReturnUnauthorizedOnInvalidLogin() {
        //Steps for writing a test case
        //1. arrange
        String loginPayload = """
                {
                   "email": "invalid_user@test.com",
                   "password": "wrongPassword"
                }
                """;
        //2. act
        given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                //3. assert
                .statusCode(401);
    }
}
