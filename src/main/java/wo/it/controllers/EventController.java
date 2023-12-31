package wo.it.controllers;

import io.quarkus.logging.Log;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import wo.it.core.exceptions.EmptyParameterException;
import wo.it.core.exceptions.EntityNotFoundException;
import wo.it.core.filter.EventFilter;
import wo.it.core.interfaces.CRUDController;
import wo.it.core.response.CommonValidationResponse;
import wo.it.database.entities.ApplicationUser;
import wo.it.database.entities.Event;
import wo.it.models.EventModel;
import wo.it.services.ApplicationUserService;
import wo.it.services.EventService;

import java.util.ArrayList;

import static jakarta.ws.rs.core.Response.Status.*;


@ApplicationScoped
@Consumes("application/json")
@Produces("application/json")
@Path("/event")
public class EventController implements CRUDController<EventModel> {

    @Inject EventService service;
    @Inject ApplicationUserService applicationUserService;

    @Authenticated
    @Override
    @POST
    public Response create(EventModel model) {
        CommonValidationResponse response = CommonValidationResponse.initWithSuccess();
        var validationResponse = validateUser(response, applicationUserService, model);
        if (validationResponse.isPresent()) {
            return validationResponse.get();
        }

        ApplicationUser user = applicationUserService.findByUuid(model.getUserUuid());

        try {
            Event event = model.toEntity();
            event.setUser(user);
            service.create(event);
        } catch (Exception e) {
            response.setErrorMessage(e.getMessage());
            Log.error("Não foi possível criar a task", e);
            return Response.status(INTERNAL_SERVER_ERROR).entity(response).build();
        }

        return Response.status(CREATED).entity(response).build();
    }

    @Authenticated
    @Override
    @GET() @Path("{uuid}")
    public Response find(@PathParam("uuid") String uuid) {
        return null;
    }

    @Authenticated
    @Override
    @PUT
    public Response update(EventModel model) {
        var response = CommonValidationResponse.initWithSuccess();
        var validationResponse = validateUser(response, applicationUserService, model);
        if (validationResponse.isPresent()) {
            return validationResponse.get();
        }

        try {
            Event event = service.findByUuid(model.getUuid());
            ApplicationUser user = applicationUserService.findByUuid(model.getUserUuid());
            if (event == null) {
                response.setErrorMessage("O evento de UUID '" + model.getUuid() + "' não pode ser atualizado pois o registro não existe!");
                return Response.status(NOT_FOUND).entity(response).build();
            }

            if (StringUtils.isBlank(model.getDescription())) {
                response.setErrorMessage("A descrição do evento não pode estar em branco!");
                return Response.status(BAD_REQUEST).entity(response).build();
            }

            event.loadFieldsToUpdate(model);
            event.setUser(user);

            service.update(event);
        } catch (Exception e) {
            response.setErrorMessage(e.getMessage());
            return Response.status(INTERNAL_SERVER_ERROR).entity(response).build();
        }

        return Response.status(NO_CONTENT).build();
    }

    @Authenticated
    @DELETE @Path("{uuid}")
    public Response delete(@PathParam("uuid") String uuid) {
        var response = new CommonValidationResponse();
        if (StringUtils.isBlank(uuid)) {
            response.setErrorMessage("O UUID do evento não pode ser vazio!");
            return Response.status(BAD_REQUEST).entity(response).build();
        }

        try {
            service.delete(uuid);
        } catch (EntityNotFoundException | EmptyParameterException e) {
            response.setErrorMessage("O evento'1 de UUID '" + uuid + "' não pode ser removido pois o registro não existe!");
            return Response.status(NOT_FOUND).entity(response).build();
        }

        return Response.status(NO_CONTENT).build();
    }

    @Authenticated
    @GET @Path("/all/{userUuid}")
    public Response findByUser(@PathParam("userUuid") String userUuid) {
        var response = CommonValidationResponse.initWithSuccess();
        var events = new ArrayList<EventModel>();
        if (StringUtils.isBlank(userUuid)) {
            return Response.status(BAD_REQUEST).entity("{ \"message\": The user uuid cannot be empty }").build();
        }

        var validationResponse = validateUser(response, applicationUserService, userUuid);
        if (validationResponse.isPresent()) {
            return validationResponse.get();
        }

        EventFilter filter = EventFilter.from(userUuid);
        var foundEvents = this.service.find(filter);
        for (Event event : foundEvents) {
            var model = event.toModel();
            model.setUserUuid(userUuid);
            events.add(model);
        }

        return Response.ok().entity(events).build();
    }
}
