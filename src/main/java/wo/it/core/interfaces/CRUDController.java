package wo.it.core.interfaces;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

public interface CRUDController<T> {
    Response create(T model);
    Response find(@PathParam("uuid") String uuid);
    Response update(T model);
    Response delete(@PathParam("uuid") String uuid);
}
