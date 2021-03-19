package rest.provider;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

//Will be implemented whenever you extend the Provider class
public interface RestRepository {

    @GET
    Response getAll();

    @Path("/{id}")
    @GET
    Response getById(@PathParam("id") int id);

    @POST
    Response create(String requestBody);

    @PUT
    @Path("/{id}")
    Response update(@PathParam("id") int id, String requestBody);

    @DELETE
    @Path("/{id}")
    Response delete(@PathParam("id") int id);


}
