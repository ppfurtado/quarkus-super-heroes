package io.quarkus.workshop.superheroes.villain;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class VillainResourceTest {
    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/api/villains/hello")
          .then()
             .statusCode(200)
             .body(is("Hello Villain Resource VillainResource"));
    }

    @Test
    void randomVillain() {
        given()
            .when()
                .get("/api/villains/random")
            .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON);
    }
}
