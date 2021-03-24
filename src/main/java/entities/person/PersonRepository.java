package entities.person;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import javax.ws.rs.WebApplicationException;

public interface PersonRepository {

    public PersonDTO create(String email, String firstName, String lastName) throws WebApplicationException;
    public PersonsDTO getAll() throws WebApplicationException;
    public PersonDTO getById(int id) throws WebApplicationException;
    public PersonDTO editPerson(PersonDTO p) throws WebApplicationException;
    public void runMigrationScript() throws WebApplicationException;
}
