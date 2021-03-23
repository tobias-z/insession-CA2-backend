package entities.hobby;

import dtos.hobby.HobbiesDTO;
import javax.ws.rs.WebApplicationException;

public interface HobbyRepository {

    public HobbiesDTO getAllHobbies() throws WebApplicationException;

    public void runMigrationScript() throws WebApplicationException;

}
