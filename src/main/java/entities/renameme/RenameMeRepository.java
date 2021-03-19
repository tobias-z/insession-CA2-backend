package entities.renameme;

import dtos.RenameMeDTO;
import java.util.List;
import javax.ws.rs.WebApplicationException;

public interface RenameMeRepository {

    RenameMeDTO create(RenameMeDTO rm) throws WebApplicationException;

    RenameMeDTO getById(long id) throws WebApplicationException;

    long getRenameMeCount() throws WebApplicationException;

    List<RenameMeDTO> getAll() throws  WebApplicationException;

}
