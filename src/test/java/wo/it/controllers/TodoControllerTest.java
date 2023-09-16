package wo.it.controllers;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import wo.it.models.authentication.Token;

import java.util.HashSet;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

@QuarkusTest
@TestHTTPEndpoint(TodoController.class)
class TodoControllerTest {

    String token;

    @BeforeEach
    void setup() throws Exception {
        this.token = Token.generate("test", new HashSet<>(), 3600L, "test");
    }

    @Disabled
    @DisplayName("When `/todo/{uuid}` has a blank uuid then endpoint must return a bad request status (400) ")
    @Test
    void whenTodoUuidIsBlankThenTheEndpointMustReturnBadRequestStatus() {
        given().header("Authentication", "Bearer " + token)
        .when().get("/null")
        .then().statusCode(400);
    }
}