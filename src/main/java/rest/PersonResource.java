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
    public Response create(String person) {
        PersonDTO p = GSON.fromJson(person, PersonDTO.class);
        PersonDTO newPerson = REPO.create(p);
        return Response.ok(GSON.toJson(newPerson)).build();
    }

    
    @Override
    public Response update(int id, String person) {
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
    
    
    @Override
    public Response getById(int id ) {
        PersonDTO p = REPO.getById(id);
        return Response.ok(GSON.toJson(p)).build();
    }
    
   
    @Path("/number/{number}")
    @GET
    public Response getByNumber(@PathParam("number") String number ) {
        PersonFacade personFacade = PersonFacade.getPersonFacade(EMF);
        PersonDTO p =  personFacade.getByNumber(number);
        return Response.ok(GSON.toJson(p)).build();
    }
    
    @Path("/city/{zipCode}")
    @GET
    public Response getByZip(@PathParam("zipCode") String zipCode ) {
        PersonFacade personFacade = PersonFacade.getPersonFacade(EMF);
        PersonsDTO p =  personFacade.getByZip(zipCode);
        return Response.ok(GSON.toJson(p)).build();
    }
    
    @Path("/hobby/{name}")
    @GET
    public Response getByHobby(@PathParam("name") String name ) {
        PersonFacade personFacade = PersonFacade.getPersonFacade(EMF);
        PersonsDTO p =  personFacade.getByZip(name);
        return Response.ok(GSON.toJson(p)).build();
    }
    
    
}
