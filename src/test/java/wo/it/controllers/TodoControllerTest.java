package wo.it.controllers;

import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import wo.it.core.exceptions.PersistException;
import wo.it.database.entities.ApplicationUser;
import wo.it.database.entities.Todo;
import wo.it.models.TodoModel;
import wo.it.models.authentication.Token;
import wo.it.services.ApplicationUserService;
import wo.it.services.TodoService;

import java.util.HashSet;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestHTTPEndpoint(TodoController.class)
class TodoControllerTest {

    String token;
    @InjectMock ApplicationUserService applicationUserService;
    @InjectMock TodoService service;

    @BeforeEach
    void setup() throws Exception {
        this.token = Token.generate("test", new HashSet<>(), 30L, "test");
    }

    @Disabled
    @DisplayName("When GET `/todo/{uuid}` has a blank uuid then endpoint must return a bad request status (400) ")
    @Test
    void whenTodoUuidIsBlankThenTheEndpointMustReturnBadRequestStatus() {
        given().header("Authorization", "Bearer " + token)
        .when().get("/1234")
        .then()
        .statusCode(400);
    }

    @DisplayName("When POST `/todo` receive a todo that is already finished, it must return an internal server error (500)")
    @Test
    void whenTodoIsAlreadyDoneThenTheEndpointShouldReturnInternalSeverErrorStatusCode() {
        TodoModel model = new TodoModel();
        model.setDone(true);

        given().header("Authentication", "Bearer " + token)
        .contentType(ContentType.JSON).body(model)
        .when().post()
        .then()
        .body("success", is(false))
        .body("error", is(true))
        .body("message", is("Não é possível criar uma TODO já finalizada"))
        .statusCode(500).log().all();
    }

    @DisplayName("When POST `/todo` receive a blank todo, it must return an internal server error (500)")
    @ParameterizedTest(name = "When `todo.content` is \"{0}\" then the todo isn't valid")
    @NullSource
    @ValueSource(strings = {"   ", "", " "})
    void whenTodoContentIsBlankThenTheEndpointShouldReturnInternalSeverErrorStatusCode(String content) {
        TodoModel model = new TodoModel();
        model.setContent(content);

        given().header("Authentication", "Bearer " + token)
        .contentType(ContentType.JSON).body(model)
        .when().post()
        .then()
        .body("success", is(false))
        .body("error", is(true))
        .body("message", is("Não é possível criar uma TODO sem uma descrição"))
        .statusCode(500).log().all();
    }

    @DisplayName("When POST `/todo` receive a invalid user, it must return an not found status code (404)")
    @Test
    void whenTodoUserIsNotValidThenTheEndpointShouldReturnNotFoundStatusCode() {
        TodoModel model = new TodoModel();
        model.setContent("Uma TODO");
        model.setUserUuid("1234");

        when(applicationUserService.findByUuid(model.getUserUuid())).thenReturn(null);

        given().header("Authentication", "Bearer " + token)
        .contentType(ContentType.JSON).body(model)
        .when().post()
        .then()
        .body("success", is(false))
        .body("error", is(true))
        .body("message", is("Usuário não encontrado. Não é possível cadastrar a TODO"))
        .statusCode(404).log().all();
    }

    @DisplayName("When POST `/todo` receive a valid todo, it must return an created status code (201)")
    @Test
    void whenTodoValidThenTheEndpointShouldReturnCreatedStatusCode() throws PersistException {
        ApplicationUser user = new ApplicationUser();
        TodoModel model = new TodoModel();
        model.setContent("Uma TODO");
        model.setUserUuid("1234");

        Todo todo = model.toEntity();
        todo.setUser(user);

        when(applicationUserService.findByUuid(model.getUserUuid())).thenReturn(user);
        doNothing().when(service).create(todo);

        given().header("Authentication", "Bearer " + token)
        .contentType(ContentType.JSON).body(model)
        .when().post()
        .then().statusCode(201).log().all();
    }

}