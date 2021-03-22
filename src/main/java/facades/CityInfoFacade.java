package facades;

import dtos.cityinfo.CityInfosDTO;
import entities.cityinfo.CityInfo;
import entities.cityinfo.CityInfoRepository;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.WebApplicationException;
import org.apache.ibatis.jdbc.ScriptRunner;
import utils.ScriptUtils;

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
        if (cityInfo == null) {
            throw new WebApplicationException("No city info found: Consider running the migration script /city/runscript", 404);
        }
        return new CityInfosDTO(cityInfo);
    }

    @Override
    public void runMigrationScript() throws WebApplicationException {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            // Dropping the table each time because otherwise it may cause multiple entry errors
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            ScriptUtils.runSQLScript("zipScript.sql", em);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }
}