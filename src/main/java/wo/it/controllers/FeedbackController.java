package wo.it.controllers;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import wo.it.core.exceptions.PersistException;
import wo.it.core.response.CommonValidationResponse;
import wo.it.database.entities.ApplicationUser;
import wo.it.database.entities.Feedback;
import wo.it.models.FeedbackModel;
import wo.it.services.ApplicationUserService;
import wo.it.services.FeedbackService;

import static jakarta.ws.rs.core.Response.Status.*;

@ApplicationScoped
@Consumes("application/json")
@Produces("application/json")
@Path("/feedback")
public class FeedbackController {

    @Inject FeedbackService service;

    @Inject ApplicationUserService applicationUserService;

    @POST
    public Response create(FeedbackModel model) {
        var response = CommonValidationResponse.initWithSuccess();

        ApplicationUser user = applicationUserService.findByUuid(model.getUserUuid());
        if (user == null) {
            response.setErrorMessage("Usuário não encontrado. Não é possível cadastrar o feedback");
            return Response.status(NOT_FOUND).entity(response).build();
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

}
