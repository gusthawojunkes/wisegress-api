package wo.it.controllers;

import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import wo.it.core.enums.Priority;
import wo.it.core.exceptions.PersistException;
import wo.it.database.entities.ApplicationUser;
import wo.it.database.entities.Todo;
import wo.it.models.TodoModel;
import wo.it.models.authentication.Token;
import wo.it.repositories.TodoRepository;
import wo.it.services.ApplicationUserService;
import wo.it.services.TodoService;

import java.util.HashSet;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestHTTPEndpoint(TodoController.class)
class TodoControllerTest {

    RequestSpecification authorizedRequest;
    @InjectMock ApplicationUserService applicationUserService;
    @InjectMock TodoService service;
    @InjectMock TodoRepository repository;

    @BeforeEach
    void setup() throws Exception {
        this.authorizedRequest = given().header("Authorization", "Bearer " + Token.generate("test", 30L, "test"));
    }

    @DisplayName("When GET `/todo/{uuid}` has a blank uuid then endpoint must return a bad request status (400) ")
    @ParameterizedTest(name = "When `uuid` is \"{0}\" then the exception must bw thrown")
    @ValueSource(strings = {"   ", " "})
    void whenTodoUuidIsBlankThenTheEndpointMustReturnBadRequestStatus(String uuid) {
        authorizedRequest
        .pathParam("uuid", uuid)
        .when().get("/{uuid}")
        .then()
        .statusCode(400).log().all();
    }

    @DisplayName("When GET `/todo/{uuid}` has a invalid uuid then endpoint must return a not found status (404) ")
    @Test
    void whenTodoUuidDoesntExistsThenTheEndpointMustReturnNotFoundStatus() {
        String uuid = "uuid_not_found";

        when(service.findByUuid(uuid)).thenReturn(null);

        authorizedRequest
        .pathParam("uuid", uuid)
        .when().get("/{uuid}")
        .then()
        .statusCode(404).log().all();
    }

    @DisplayName("When GET `/todo/{uuid}` has a valid uuid then endpoint must the correspondent todo with a ok status (200) ")
    @Test
    void whenTodoUuidExistsThenTheEndpointMustReturnTheCorrespondentTodo() {
        String uuid = "valid_uuid";
        Todo todo = new Todo();
        todo.setContent("TODO");
        todo.setUuid(uuid);
        todo.setDone(false);
        todo.setPriority(Priority.MEDIUM);

        when(service.findByUuid(uuid)).thenReturn(todo);

        authorizedRequest
        .pathParam("uuid", uuid)
        .when().get("/{uuid}")
        .then()
        .body("uuid", is(uuid))
        .body("content", is("TODO"))
        .body("done", is(false))
        .body("priority", is("MEDIUM"))
        .body("completedAt", equalTo(null))
        .statusCode(200).log().all();
    }

    @DisplayName("When POST `/todo` send a already done todo, the request must return a bad request status (400)")
    @Test
    void whenTodoIsAlreadyDoneThenTheEndpointShouldReturnBadRequestStatusCode() {
        TodoModel model = new TodoModel();
        model.setDone(true);

        authorizedRequest
        .contentType(ContentType.JSON).body(model)
        .when().post()
        .then()
        .body("success", is(false))
        .body("error", is(true))
        .body("message", is("Não é possível criar uma TODO já finalizada"))
        .statusCode(400).log().all();
    }

    @DisplayName("When POST `/todo` receive a blank todo, it must return bad request status (400)")
    @ParameterizedTest(name = "When `todo.content` is \"{0}\" then the todo isn't valid")
    @NullSource
    @ValueSource(strings = {"   ", "", " "})
    void whenTodoContentIsBlankThenTheEndpointShouldReturnBadRequestStatusCode(String content) {
        TodoModel model = new TodoModel();
        model.setContent(content);

        authorizedRequest
        .contentType(ContentType.JSON).body(model)
        .when().post()
        .then()
        .body("success", is(false))
        .body("error", is(true))
        .body("message", is("Não é possível criar uma TODO sem uma descrição"))
        .statusCode(400).log().all();
    }

    @DisplayName("When POST `/todo` receive a invalid user, it must return an not found status code (404)")
    @Test
    void whenTodoUserIsNotValidThenTheEndpointShouldReturnNotFoundStatusCode() {
        TodoModel model = new TodoModel();
        model.setContent("Uma TODO");
        model.setUserUuid("1234");

        when(applicationUserService.findByUuid(model.getUserUuid())).thenReturn(null);

        authorizedRequest
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

        authorizedRequest
        .contentType(ContentType.JSON).body(model)
        .when().post()
        .then().statusCode(201).log().all();
    }

    @DisplayName("When DELETE `/todo/{uuid}` has a blank uuid then endpoint must return a bad request status (400) ")
    @ParameterizedTest(name = "When `uuid` is \"{0}\" then the exception must bw thrown")
    @ValueSource(strings = {"   ", " "})
    void whenTodoUuidIsBlankForDeleteThenTheEndpointMustReturnBadRequestStatus(String uuid) {
        authorizedRequest
        .pathParam("uuid", uuid)
        .when().delete("/{uuid}")
        .then()
        .statusCode(400).log().all();
    }

    @DisplayName("When DELETE `/todo` receive a existent uuid, it must return an no content status code (204)")
    @Test
    void whenTodoDoesExistsThenTheEndpointShouldReturnNoContentStatusCode() {
        String uuid = "1234";
        var todo = new Todo();
        when(repository.findByUuid(uuid)).thenReturn(todo);
        doNothing().when(repository).delete(todo);

        authorizedRequest
        .pathParam("uuid", uuid)
        .when().delete("/{uuid}")
        .then().statusCode(204).log().all();
    }


    @DisplayName("When PUT `/todo` receive a nonexistent uuid, the request must return a not found status (404)")
    @Test
    void whenTodoIsNonExistentThenTheRequestMustReturnANotFoundStatus() {
        TodoModel model = new TodoModel();
        model.setUuid("nonexistent");

        when(service.findByUuid(model.getUuid())).thenReturn(null);

        authorizedRequest
        .contentType(ContentType.JSON).body(model)
        .when().put()
        .then()
        .body("success", is(false))
        .body("error", is(true))
        .body("message", is("O TODO de UUID '" + model.getUuid() + "' não pode ser atualizado pois o registro não existe!"))
        .statusCode(404).log().all();
    }

    @DisplayName("When PUT `/todo` receive a invalid user, it must return an not found status code (404)")
    @Test
    void whenTodoReceiveANonExistentUserThenTheRequestMustReturnANotFoundStatus() {
        TodoModel model = new TodoModel();
        model.setContent("Uma TODO");
        model.setUserUuid("1234");
        model.setUuid("valid_uuid");

        when(service.findByUuid(model.getUuid())).thenReturn(new Todo());
        when(applicationUserService.findByUuid(model.getUserUuid())).thenReturn(null);

        authorizedRequest
        .contentType(ContentType.JSON).body(model)
        .when().put()
        .then()
        .body("success", is(false))
        .body("error", is(true))
        .body("message", is("Usuário não encontrado. Não é possível cadastrar a TODO"))
        .statusCode(404).log().all();
    }

    @DisplayName("When PUT `/todo` receive a blank todo, it must return bad request status (400)")
    @ParameterizedTest(name = "When `todo.content` is \"{0}\" then the todo isn't valid")
    @NullSource
    @ValueSource(strings = {"   ", "", " "})
    void whenTodoContentIsBlankThenTheEndpointShouldReturnBadRequestStatusCodeOnUpdateEndpoint(String content) {
        TodoModel model = new TodoModel();
        model.setContent(content);
        model.setUserUuid("1234");
        model.setUuid("valid_uuid");

        when(service.findByUuid(model.getUuid())).thenReturn(new Todo());
        when(applicationUserService.findByUuid(model.getUserUuid())).thenReturn(new ApplicationUser());

        authorizedRequest
        .contentType(ContentType.JSON).body(model)
        .when().put()
        .then()
        .body("success", is(false))
        .body("error", is(true))
        .body("message", is("A descrição não pode estar em branco!"))
        .statusCode(400).log().all();
    }

    @DisplayName("When PUT `/todo` receive a valid todo, it must return an no content status code (204)")
    @Test
    void whenTodoIsValidThenTheEndpointShouldReturnNotFoundStatusCode() {
        TodoModel model = new TodoModel();
        model.setContent("TODO");
        model.setUserUuid("1234");
        model.setUuid("valid_uuid");

        var todo = new Todo();
        var user = new ApplicationUser();
        user.setUuid("1234");
        todo.setUser(user);

        when(service.findByUuid(model.getUuid())).thenReturn(todo);
        when(applicationUserService.findByUuid(model.getUserUuid())).thenReturn(user);

        authorizedRequest
        .contentType(ContentType.JSON).body(model)
        .when().put()
        .then()
        .statusCode(204).log().all();
    }

}