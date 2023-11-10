package wo.it.core.interfaces;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import wo.it.core.response.CommonValidationResponse;
import wo.it.database.entities.ApplicationUser;
import wo.it.models.AbstractModel;
import wo.it.services.ApplicationUserService;

import java.util.Optional;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

public interface CRUDController<T> {
    Response create(T model);
    Response find(@PathParam("uuid") String uuid);
    Response update(T model);
    Response delete(@PathParam("uuid") String uuid);

    default Optional<Response> validateUser(CommonValidationResponse response, ApplicationUserService applicationUserService, AbstractModel model) {
        return validateUser(response, applicationUserService, model.getUserUuid());
    }

    default Optional<Response> validateUser(CommonValidationResponse response, ApplicationUserService applicationUserService, String userUuid) {
        ApplicationUser user = applicationUserService.findByUuid(userUuid);
        if (user == null) {
            response.setErrorMessage("Usuário não encontrado. Não é possível cadastrar a task!");
            return Optional.of(Response.status(NOT_FOUND).entity(response).build());
        }

        if (user.isBlocked()) {
            response.setErrorMessage("Usuário bloqueado no sistema!");
            return Optional.of(Response.status(BAD_REQUEST).entity(response).build());
        }

        return Optional.empty();
    }
}
