package facades;

import dtos.cityinfo.CityInfosDTO;
import entities.cityinfo.CityInfo;
import entities.cityinfo.CityInfoRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.WebApplicationException;

public class CityInfoFacade implements CityInfoRepository {

    private static CityInfoFacade instance;
    private static EntityManagerFactory emf;

    private CityInfoFacade() {
    }

    public static CityInfoFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CityInfoFacade();
        }
        return instance;
    }

    @Override
    public CityInfosDTO getAllCityInfos() throws WebApplicationException {
        EntityManager em = emf.createEntityManager();
        List<CityInfo> cityInfo = em.createQuery("SELECT c FROM CityInfo c", CityInfo.class).getResultList();
        return new CityInfosDTO(cityInfo);
    }
}