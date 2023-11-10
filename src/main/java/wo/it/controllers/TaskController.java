package wo.it.controllers;

import io.quarkus.logging.Log;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import wo.it.core.exceptions.EntityNotFoundException;
import wo.it.core.exceptions.PersistException;
import wo.it.core.interfaces.CRUDController;
import wo.it.core.response.CommonValidationResponse;
import wo.it.database.entities.ApplicationUser;
import wo.it.database.entities.Task;
import wo.it.models.TaskModel;
import wo.it.services.ApplicationUserService;
import wo.it.services.TaskService;

import java.time.LocalDateTime;

import static jakarta.ws.rs.core.Response.Status.*;


@ApplicationScoped
@Consumes("application/json")
@Produces("application/json")
@Path("/task")
public class TaskController implements CRUDController<TaskModel> {

    @Inject TaskService service;

    @Inject ApplicationUserService applicationUserService;

    @Override
    @Authenticated
    @POST
    public Response create(TaskModel model) {
        CommonValidationResponse response = CommonValidationResponse.initWithSuccess();
        var validationResponse = validateUser(response, applicationUserService, model);
        if (validationResponse.isPresent()) {
            return validationResponse.get();
        }
        var now = LocalDateTime.now();
        try {
            ApplicationUser user = applicationUserService.findByUuid(model.getUserUuid());

            if (model.isDone() || model.getCompletedAt() != null) {
                response.setErrorMessage("Não é possível criar uma task já finalizada");
                return Response.status(BAD_REQUEST).entity(response).build();
            }

            if (StringUtils.isBlank(model.getDescription())) {
                response.setErrorMessage("Não é possível criar uma task sem uma descrição");
                return Response.status(BAD_REQUEST).entity(response).build();
            }

            if (model.getDueDate() != null && now.isAfter(model.getDueDate())) {
                response.setErrorMessage("Não é possível criar uma task em que a data de expiração é maior que hoje!");
                return Response.status(BAD_REQUEST).entity(response).build();
            }

            try {
                Task task = model.toEntity();
                task.setUser(user);
                service.create(task);
            } catch (PersistException e) {
                response.setErrorMessage(e.getMessage());
                Log.error("Não foi possível criar a task", e);
                return Response.status(INTERNAL_SERVER_ERROR).entity(response).build();
            }
        } catch (Exception e) {
            response.setErrorMessage("Um erro inesperado aconteceu ao tentar cadastrar a task!");
            Log.error("Não foi possível criar a task", e);
            return Response.status(INTERNAL_SERVER_ERROR).entity(response).build();
        }
        return Response.status(CREATED).entity(response).build();
    }

    @Override
    @Authenticated
    @GET @Path("{uuid}")
    public Response find(@PathParam("uuid") String uuid) {
        if (StringUtils.isBlank(uuid)) {
            return Response.status(BAD_REQUEST).entity("{ \"message\": O UUID da task não pode ser vazio }").build();
        }

        Task task = service.findByUuid(uuid);
        if (task == null) {
            return Response.status(NOT_FOUND).build();
        }

        return Response.ok().entity(task.toModel()).build();

    }

    @Override
    @Authenticated
    @PUT
    public Response update(TaskModel model) {
        var response = CommonValidationResponse.initWithSuccess();
        var now = LocalDateTime.now();
        var validationResponse = validateUser(response, applicationUserService, model);
        if (validationResponse.isPresent()) {
            return validationResponse.get();
        }
        try {
            Task task = service.findByUuid(model.getUuid());
            if (task == null) {
                response.setErrorMessage("A task de UUID '" + model.getUuid() + "' não pode ser atualizado pois o registro não existe!");
                return Response.status(NOT_FOUND).entity(response).build();
            }

            if (StringUtils.isBlank(model.getDescription())) {
                response.setErrorMessage("A descrição da task não pode estar em branco!");
                return Response.status(BAD_REQUEST).entity(response).build();
            }

            if (model.getDueDate() != null && now.isAfter(model.getDueDate())) {
                response.setErrorMessage("Não é possível criar uma task em que a data de expiração é maior que hoje!");
                return Response.status(BAD_REQUEST).entity(response).build();
            }

            task.loadFieldsToUpdate(model);

            service.update(task);
        } catch (Exception e) {
            response.setErrorMessage(e.getMessage());
            return Response.status(INTERNAL_SERVER_ERROR).entity(response).build();
        }

        return Response.status(NO_CONTENT).build();
    }

    @Override
    @Authenticated
    @DELETE @Path("{uuid}")
    public Response delete(@PathParam("uuid") String uuid) {
        var response = new CommonValidationResponse();
        if (StringUtils.isBlank(uuid)) {
            response.setErrorMessage("O UUID das task não pode ser vazio!");
            return Response.status(BAD_REQUEST).entity(response).build();
        }

        try {
            service.delete(uuid);
        } catch (EntityNotFoundException e) {
            response.setErrorMessage("O TODO de UUID '" + uuid + "' não pode ser removido pois o registro não existe!");
            return Response.status(NOT_FOUND).entity(response).build();
        }

        return Response.status(NO_CONTENT).build();
    }
}
