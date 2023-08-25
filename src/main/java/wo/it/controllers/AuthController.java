package wo.it.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import wo.it.models.CommonValidationResponse;
import wo.it.models.authentication.Credential;
import wo.it.models.authentication.Formulary;
import wo.it.models.authentication.RegistrationResponse;
import wo.it.services.AuthService;

@ApplicationScoped
@Path("/auth")
public class AuthController {

    @Inject AuthService service;

    @POST
    @PermitAll
    @Path("/login")
    public Response login(Credential credential) {
        CommonValidationResponse response = service.authenticate(credential);

        if (response.hasCritics()) {
            return Response.status(401).entity(response).build();
        }

        return Response.ok(credential).build();
    }

    @POST
    @PermitAll
    @Path("/register")
    public Response register(Formulary formulary) {
        RegistrationResponse response = service.register(formulary);

        if (response.hasCritics()) {
            return Response.status(400).build();
        }

        return Response.ok(formulary).build();
    }

}
