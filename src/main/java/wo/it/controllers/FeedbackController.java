package wo.it.controllers;

import io.quarkus.logging.Log;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import wo.it.core.exceptions.PersistException;
import wo.it.core.interfaces.CRUDController;
import wo.it.core.response.CommonValidationResponse;
import wo.it.database.entities.ApplicationUser;
import wo.it.database.entities.Feedback;
import wo.it.models.Classification;
import wo.it.models.FeedbackModel;
import wo.it.services.ApplicationUserService;
import wo.it.services.FeedbackService;

import java.util.List;
import java.util.Optional;

import static jakarta.ws.rs.core.Response.Status.*;

@ApplicationScoped
@Consumes("application/json")
@Produces("application/json")
@Path("/feedback")
public class FeedbackController {

    @Inject FeedbackService service;

    @Inject ApplicationUserService applicationUserService;

    @Authenticated
    @POST
    public Response create(FeedbackModel model) {
        var response = CommonValidationResponse.initWithSuccess();

        ApplicationUser user = applicationUserService.findByUuid(model.getUserUuid());
        if (user == null) {
            response.setErrorMessage("Usuário não encontrado. Não é possível cadastrar o feedback");
            return Response.status(BAD_REQUEST).entity(response).build();
        }

        if (user.isBlocked()) {
            response.setErrorMessage("Usuário bloqueado no sistema!");
            return Response.status(BAD_REQUEST).entity(response).build();
        }

        if (model.getFeature() == null) {
            response.setErrorMessage("Não é possível cadastrar um feedback sem identificar a feature.");
            return Response.status(BAD_REQUEST).entity(response).build();
        }

        if (model.getRating() == null) {
            response.setErrorMessage("Não é possível cadastrar um feedback sem dar uma nota.");
            return Response.status(BAD_REQUEST).entity(response).build();
        }

        try {
            Feedback feedback = model.toEntity();
            feedback.setUser(user);
            service.create(feedback);
        } catch (PersistException e) {
            response.setErrorMessage(e.getMessage());
            Log.error("Não foi possível cadastrar o feedback", e);
            return Response.status(INTERNAL_SERVER_ERROR).entity(response).build();
        }

        return Response.status(CREATED).build();
    }

    @GET @Path("/calculate/{userUuid}")
    public Response calculate(@PathParam("userUuid") String userUuid) {
        var response = CommonValidationResponse.initWithSuccess();
        ApplicationUser user = applicationUserService.findByUuid(userUuid);
        if (user == null) {
            response.setErrorMessage("Usuário não encontrado. Não é possível cadastrar a task!");
            return Response.status(NOT_FOUND).entity(response).build();
        }

        if (user.isBlocked()) {
            response.setErrorMessage("Usuário bloqueado no sistema!");
            return Response.status(BAD_REQUEST).entity(response).build();
        }

        List<Classification> classification = service.calculate(userUuid);

        return Response.ok().entity(classification).build();
    }

}
