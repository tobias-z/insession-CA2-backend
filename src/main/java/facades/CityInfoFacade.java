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
            throw new WebApplicationException("No city info found: Consider running the migration script /cityinfo/runscript", 404);
        }
        return new CityInfosDTO(cityInfo);
    }

    @Override
    public void runMigrationScript() throws WebApplicationException {
        InputStream stream = CityInfoFacade.class.getClassLoader().getResourceAsStream("zipScript.sql");
        if (stream == null) {
            throw new WebApplicationException("File: zipScript.sql, was not found", 500);
        }

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Connection connection = em.unwrap(Connection.class);
            ScriptRunner runner = new ScriptRunner(connection);

            // Dropping the table each time because otherwise it may cause multiple entry errors
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            runner.runScript(new BufferedReader(new InputStreamReader(stream)));
            em.getTransaction().commit();
            System.out.println("Finished running script");
        } finally {
            em.close();
        }
    }
}