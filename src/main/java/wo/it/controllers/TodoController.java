package wo.it.controllers;

import io.quarkus.logging.Log;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import wo.it.core.exceptions.PersistException;
import wo.it.database.entities.ApplicationUser;
import wo.it.database.entities.Todo;
import wo.it.core.response.CommonValidationResponse;
import wo.it.models.TodoModel;
import wo.it.services.ApplicationUserService;
import wo.it.services.TodoService;

import static jakarta.ws.rs.core.Response.Status.*;

@ApplicationScoped
@Consumes("application/json")
@Produces("application/json")
@Path("/todo")
public class TodoController implements CRUDController<TodoModel> {

    @Inject TodoService service;
    @Inject ApplicationUserService applicationUserService;

    @Override
    @POST
    public Response create(TodoModel model) {
        var response = CommonValidationResponse.initWithSuccess();

        if (model.isDone()) {
            response.makeInvalid();
            response.setMessage("Não é possível criar uma TODO já finalizada");
            return Response.status(INTERNAL_SERVER_ERROR).entity(response).build();
        }

        if (StringUtils.isBlank(model.getContent())) {
            response.makeInvalid();
            response.setMessage("Não é possível criar uma TODO sem uma descrição");
            return Response.status(INTERNAL_SERVER_ERROR).entity(response).build();
        }

        ApplicationUser user = applicationUserService.findByUuid(model.getUserUuid());
        if (user == null) {
            response.makeValid();
            response.setMessage("Usuário não encontrado. Não é possível cadastrar a TODO");
            return Response.status(NOT_FOUND).entity(response).build();
        }

        try {
            Todo todo = model.toEntity();
            todo.setUser(user);
            service.create(todo);
        } catch (PersistException e) {
            Log.error("Não foi possível criar a TODO", e);
            return Response.status(INTERNAL_SERVER_ERROR).build();
        }

        return Response.status(CREATED).build();
    }

    @Override
    @Authenticated
    @GET @Path("{uuid}")
    public Response find(@PathParam("uuid") String uuid) {
        if (StringUtils.isBlank(uuid)) {
            return Response.status(BAD_REQUEST).entity("{ \"message\": The uuid cannot be empty }").build();
        }

        Todo todo = service.findByUuid(uuid);

        if (todo == null) {
            return Response.status(NOT_FOUND).build();
        }

        return Response.ok().entity(todo.toModel()).build();
    }

    @Override
    @PUT
    public Response update(TodoModel model) {
        return null;
    }

    @Override
    @DELETE @Path("{uuid}")
    public Response delete(@PathParam("uuid") String uuid) {
        return null;
    }


}
