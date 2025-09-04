import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;

public class PatientIntegrationTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:4004";
    }

    @Test
    public void shouldReturnPatientsWithValidToken(){
        //Steps for writing a test case
        //1. arrange
        String loginPayload = """
                {
                   "email": "testuser@test.com",
                   "password": "password123"
                }
                """;
        //2. act
        String token = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("token");

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/patients")
                .then()
                //assert
                .statusCode(200)
                .body("patients", notNullValue());
    }

    @Test
    public void shouldContainSpecificPatient() {
        String loginPayload = """
                {
                   "email": "testuser@test.com",
                   "password": "password123"
                }
                """;
        String token = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("token");

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/patients");

        System.out.println(response.getBody().asString());

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/patients")
                .then()
                .statusCode(200)
                .body("name", hasItem("Julyanna Merchan"));
    }
}




