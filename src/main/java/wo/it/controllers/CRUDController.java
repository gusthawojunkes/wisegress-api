package wo.it.controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

public interface CRUDController<T> {
    @POST Response create(T model);
    @GET Response find(@PathParam("uuid") String uuid);
    @PUT Response update(T model);
    @DELETE Response delete(@PathParam("uuid") String uuid);
}
