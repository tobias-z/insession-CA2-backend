package rest;

import dtos.RenameMeDTO;
import entities.renameme.RenameMeRepository;
import facades.FacadeExample;
import java.util.List;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import rest.provider.Provider;

//Todo Remove or change relevant parts before ACTUAL use
//Consumes and produces are provided by the provider
@Path("xxx")
public class RenameMeResource extends Provider {

    private final RenameMeRepository REPO = FacadeExample.getInstance(EMF);

    @Override
    public Response getById(int id) {
        RenameMeDTO renameMeDTO = REPO.getById(id);
        return Response.ok(GSON.toJson(renameMeDTO)).build();
    }

    @Override
    public Response getAll() {
        List<RenameMeDTO> renameMeDTO = REPO.getAll();
        return Response.ok(GSON.toJson(renameMeDTO)).build();
    }

    @Override
    public Response delete(int id) {
        //TODO (tz): implement this!
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @Override
    public Response create(String requestBody) {
        RenameMeDTO parsedBody = GSON.fromJson(requestBody, RenameMeDTO.class);
        RenameMeDTO createdRenameMe = REPO.create(parsedBody);
        return Response.ok(GSON.toJson(createdRenameMe)).build();
    }

    @Override
    public Response update(int id, String requestBody) {
        //TODO (tz): implement this!
        throw new UnsupportedOperationException("Not yet implemented!");
    }

}
