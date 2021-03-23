package rest;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import entities.PersonRepository;
import facades.PersonFacade;
import facades.Populator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import rest.provider.Provider;
import utils.ScriptUtils;

//Todo Remove or change relevant parts before ACTUAL use
//Consumes and produces are provided by the provider
@Path("person")
public class PersonResource extends Provider {

    private final PersonRepository REPO = PersonFacade.getPersonFacade(EMF);
  

    @Override
    @GET
    public Response getAll() {
        PersonsDTO persons = REPO.getAll();
        return Response.ok(GSON.toJson(persons)).build();
    }
    
    @GET
    @Path("/runscript")
    public Response runMigrationScript() {
        REPO.runMigrationScript();
        String okayResponse = "{\"worked\":\"Awesome!\"}";
        return Response.ok(okayResponse).build();
    }
    
    
    @Override
    public Response delete(int id) {
        //TODO (tz): implement this!
        throw new UnsupportedOperationException("Not yet implemented!");
    }

   

    @Override
    public Response update(int id, String requestBody) {
        //TODO (tz): implement this!
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @Override
    public Response getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response create(String requestBody) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
