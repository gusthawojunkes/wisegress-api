package wo.it.controllers;

import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wo.it.core.enums.Feature;
import wo.it.core.enums.Rating;
import wo.it.core.exceptions.PersistException;
import wo.it.database.entities.ApplicationUser;
import wo.it.models.FeedbackModel;
import wo.it.models.authentication.Token;
import wo.it.services.ApplicationUserService;
import wo.it.services.FeedbackService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestHTTPEndpoint(FeedbackController.class)
class FeedbackControllerTest {

    RequestSpecification authorizedRequest;
    @InjectMock FeedbackService service;
    @InjectMock ApplicationUserService applicationUserService;

    @BeforeEach
    void setup() throws Exception {
        this.authorizedRequest = given().header("Authorization", "Bearer " + Token.generate("test", 30L, "test"));
    }

    @DisplayName("When POST `/feedback` receive a invalid user, it must return an bad request status code (400)")
    @Test
    void whenFeedbackReceiveAInvalidUserThenTheEndpointShouldReturnBadRequestStatusCode() {
        FeedbackModel model = new FeedbackModel();
        model.setUserUuid("invalid_uuid");

        when(applicationUserService.findByUuid(model.getUserUuid())).thenReturn(null);

        authorizedRequest
        .contentType(ContentType.JSON).body(model)
        .when().post()
        .then()
        .body("success", is(false))
        .body("error", is(true))
        .body("message", is("Usuário não encontrado. Não é possível cadastrar o feedback"))
        .statusCode(400).log().all();
    }

    @DisplayName("When POST `/feedback` receive has no `feature` property, it must return an bad request status code (400)")
    @Test
    void whenFeedbackHasNoFeaturePropertyThenTheEndpointShouldReturnBadRequestStatusCode() {
        FeedbackModel model = new FeedbackModel();
        model.setUserUuid("1234");
        model.setFeature(null);

        when(applicationUserService.findByUuid(model.getUserUuid())).thenReturn(new ApplicationUser());

        authorizedRequest
        .contentType(ContentType.JSON).body(model)
        .when().post()
        .then()
        .body("success", is(false))
        .body("error", is(true))
        .body("message", is("Não é possível cadastrar um feedback sem identificar a feature."))
        .statusCode(400).log().all();
    }

    @DisplayName("When POST `/feedback` receive has no `rating` property, it must return an bad request status code (400)")
    @Test
    void whenFeedbackHasNoRatingPropertyThenTheEndpointShouldReturnBadRequestStatusCode() {
        FeedbackModel model = new FeedbackModel();
        model.setUserUuid("1234");
        model.setFeature(Feature.KANBAN);
        model.setRating(null);

        when(applicationUserService.findByUuid(model.getUserUuid())).thenReturn(new ApplicationUser());

        authorizedRequest
        .contentType(ContentType.JSON).body(model)
        .when().post()
        .then()
        .body("success", is(false))
        .body("error", is(true))
        .body("message", is("Não é possível cadastrar um feedback sem dar uma nota."))
        .statusCode(400).log().all();
    }

    @DisplayName("When POST `/todo` receive a valid feedback, it must return an created status code (201)")
    @Test
    void whenFeedbackReceiceAValidFeedbackItMustReturnACreatedStatusCode() throws PersistException {
        FeedbackModel model = new FeedbackModel();
        model.setUserUuid("1234");
        model.setFeature(Feature.KANBAN);
        model.setRating(Rating.GOOD);

        when(applicationUserService.findByUuid(model.getUserUuid())).thenReturn(new ApplicationUser());
        doNothing().when(service).create(model.toEntity());

        authorizedRequest
        .contentType(ContentType.JSON)
        .body(model)
        .when().post()
        .then()
        .statusCode(201).log().all();
    }

}