package entities.cityinfo;

import dtos.cityinfo.CityInfosDTO;
import javax.ws.rs.WebApplicationException;

public interface CityInfoRepository {

    public CityInfosDTO getAllCityInfos() throws WebApplicationException;

    public void runMigrationScript() throws WebApplicationException;

}
