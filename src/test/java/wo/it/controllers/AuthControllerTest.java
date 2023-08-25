package wo.it.controllers;

import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import wo.it.models.CommonValidationResponse;
import wo.it.models.authentication.Credential;
import wo.it.services.AuthService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
@TestHTTPEndpoint(AuthController.class)
class AuthControllerTest {

    @InjectMock AuthService service;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

//    @DisplayName("When `CommonValidationResponse.class` has critics the endpoint must return unauthorized status code (401)")
//    @Test
//    @Disabled
//    void whenResponseHasCriticsThenMustReturnUnauthorizedStatus() {
//        var unauthorizedResponse = new CommonValidationResponse();
//        unauthorizedResponse.setSuccess(false);
//        unauthorizedResponse.setError(true);
//        unauthorizedResponse.setMessage("Unauthorized!");
//
//        Credential credential = new Credential("", "");
//
//        Mockito.when(service.authenticate(credential)).thenReturn(unauthorizedResponse);
//
//        given().contentType("application/json").body(credential).when().post("/login")
//        .then().statusCode(401)
//        .body(is(unauthorizedResponse));
//    }
}