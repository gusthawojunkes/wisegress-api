package wo.it.controllers;

import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wo.it.models.CommonValidationResponse;
import wo.it.models.authentication.AuthValidationResponse;
import wo.it.models.authentication.Credential;
import wo.it.models.authentication.Formulary;
import wo.it.models.authentication.RegistrationResponse;
import wo.it.services.AuthService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestHTTPEndpoint(AuthController.class)
class AuthControllerTest {

    @InjectMock AuthService service;

    @DisplayName("When `AuthService.login()` has critics the endpoint must return unauthorized status code (401)")
    @Test
    void whenLoginEndpointHasCriticsThenMustReturnUnauthorizedStatus() {
        var unauthorizedResponse = AuthValidationResponse.init();
        unauthorizedResponse.setSuccess(false);
        unauthorizedResponse.setError(true);
        unauthorizedResponse.setMessage("Unauthorized!");

        Credential credential = new Credential("test@test.com", "123456");

        when(service.authenticate(credential)).thenReturn(unauthorizedResponse);

        given().contentType(ContentType.JSON).body(credential).when().post("/login")
        .then()
        .body("success", is(false))
        .body("error", is(true))
        .body("message", is("Unauthorized!"))
        .body("user", equalTo(null))
        .statusCode(401);
    }

    @DisplayName("When `AuthService.register()` has critics the endpoint must return bad request status code (400)")
    @Test
    void whenRegisterEndpointHasCriticsThenMustReturnBadRequestStatus() {
        var response = new RegistrationResponse();
        response.setSuccess(false);
        response.setError(true);

        Formulary formulary = new Formulary();

        when(service.register(formulary)).thenReturn(response);

        given().contentType(ContentType.JSON).body(formulary).when().post("/register")
        .then()
        .statusCode(400);

    }
}