package in.reqres;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class ApiTests extends TestBase{

    @Test
    public void getResourcesListTest() {
        given()
                .log().uri()
        .when()
                .get("/unknown")
        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(12))
                .body("data", hasSize(6))
                .body("total_pages", is(2))
                .body("support.url", is("https://reqres.in/#support-heading"));
    }

    @Test
    public void resourcesListId4DataTest() {
        given()
                .log().uri()
        .when()
                .get("/unknown")
        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id[3]", is(4))
                .body("data.name[3]", is("aqua sky"))
                .body("data.year[3]", is(2003))
                .body("data.color[3]", is("#7BC4C4"))
                .body("data.pantone_value[3]", is("14-4811"));
    }

    @Test
    public void unsuccessfulGetResourceTest() {
        given()
                .log().uri()
                .when()
                .get("/unknown/13")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void createUserTest() {

        String userName = "Bob";
        String userJob = "Astronaut";

        String userCredential = "{\"name\": \"" + userName + "\", \"job\": \"" + userJob + "\"}";

        given()
                .body(userCredential)
                .contentType(JSON)
                .log().uri()
        .when()
                .post("/users")
        .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is(userName))
                .body("job", is(userJob));
    }

    @Test
    public void updateUserTest() {

        String userName = "Bob";
        String userJob = "Сook";

        String userCredential = "{\"name\": \"" + userName + "\", \"job\": \"" + userJob + "\"}";

        given()
                .body(userCredential)
                .contentType(JSON)
                .log().uri()
            .when()
                .patch("/users/2")
            .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is(userName))
                .body("job", is(userJob));
    }
}
