package rest;

import dtos.cityinfo.CityInfosDTO;
import entities.cityinfo.CityInfoRepository;
import facades.CityInfoFacade;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import rest.provider.Provider;

@Path("city")
public class CityInfoResource extends Provider {

    private final CityInfoRepository REPO = CityInfoFacade.getInstance(EMF);

    @Override
    public Response getAll() {
        CityInfosDTO cityInfosDTO = REPO.getAllCityInfos();
        return Response.ok(GSON.toJson(cityInfosDTO)).build();
    }

    @GET
    @Path("/runscript")
    public Response runMigrationScript() {
        REPO.runMigrationScript();
        String okayResponse = "{\"worked\":\"yay!\"}";
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