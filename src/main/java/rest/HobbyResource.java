package rest;

import dtos.hobby.HobbiesDTO;
import entities.hobby.HobbyRepository;
import facades.HobbyFacade;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import rest.provider.Provider;

@Path("hobbies")
public class HobbyResource extends Provider {

    private final HobbyRepository REPO = HobbyFacade.getInstance(EMF);

    @Override
    public Response getAll() {
        HobbiesDTO hobbiesDTO = REPO.getAllHobbies();
        return Response.ok(GSON.toJson(hobbiesDTO)).build();
    }

    @GET
    @Path("/runscript")
    public Response runMigrationScript() {
        REPO.runMigrationScript();
        String okayResponse = "{\"worked\":\"HURRAY!\"}";
        return Response.ok(okayResponse).build();
    }

    @Override
    public Response getById(int id) {
        //TODO (tz): implement this!
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @Override
    public Response create(String requestBody) {
        //TODO (tz): implement this!
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @Override
    public Response update(int id, String requestBody) {
        //TODO (tz): implement this!
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @Override
    public Response delete(int id) {
        //TODO (tz): implement this!
        throw new UnsupportedOperationException("Not yet implemented!");
    }
}