package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import dtos.PersonsDTO;

import entities.person.PersonRepository;
import facades.PersonFacade;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import rest.provider.Provider;

//Todo Remove or change relevant parts before ACTUAL use
//Consumes and produces are provided by the provider
@Path("persons")
public class PersonResource extends Provider {

    private final PersonRepository REPO = PersonFacade.getPersonFacade(EMF);
     private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
  

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
    
    @POST
    @Override
    public Response create(String person) {
        PersonDTO p = GSON.fromJson(person, PersonDTO.class);
        PersonDTO newPerson = REPO.create(p.getEmail(), p.getFirstName(), p.getLastName());
        return Response.ok(GSON.toJson(newPerson)).build();
    }
    
    

    @PUT
    @Path("{id}")
    @Override
    public Response update(@PathParam("id") int id, String person) {
        PersonDTO pDTO = GSON.fromJson(person, PersonDTO.class);
        pDTO.setId(id);
        PersonDTO pEdited = REPO.editPerson(pDTO);
        return Response.ok(GSON.toJson(pEdited)).build();
    }
    
   
    
    @Override
    public Response delete(int id) {
        //TODO (tz): implement this!
        throw new UnsupportedOperationException("Not yet implemented!");
    }

   
    @Path("{id}")
    @GET
    @Override
    public Response getById(@PathParam("id") int id ) {
        PersonDTO p = REPO.getById(id);
        return Response.ok(GSON.toJson(p)).build();
    }
    
}
