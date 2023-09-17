package wo.it.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import wo.it.core.response.authentication.AuthValidationResponse;
import wo.it.models.authentication.Credential;
import wo.it.models.authentication.Formulary;
import wo.it.core.response.authentication.RegistrationResponse;
import wo.it.services.AuthService;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static jakarta.ws.rs.core.Response.Status.UNAUTHORIZED;

@ApplicationScoped
@Consumes("application/json")
@Produces("application/json")
@Path("/auth")
public class AuthController {

    @Inject AuthService service;

    @POST
    @PermitAll
    @Path("/login")
    public Response login(@Valid Credential credential) {
        AuthValidationResponse response = service.authenticate(credential);

        if (response.hasCritics()) {
            return Response.status(UNAUTHORIZED.getStatusCode()).entity(response).build();
        }

        return Response.ok(response).build();
    }

    @POST
    @PermitAll
    @Path("/register")
    public Response register(@Valid Formulary formulary) {
        RegistrationResponse response = service.register(formulary);

        if (response.hasCritics()) {
            return Response.status(BAD_REQUEST.getStatusCode()).entity(response).build();
        }

        return Response.ok(response).build();
    }

}
