package facades;

import static org.junit.jupiter.api.Assertions.*;

import dtos.cityinfo.CityInfosDTO;
import entities.cityinfo.CityInfoRepository;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import utils.ScriptUtils;

class CityInfoFacadeTest {

    private static EntityManagerFactory emf;
    private static CityInfoRepository repo;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        repo = CityInfoFacade.getInstance(emf);
    }

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            ScriptUtils.runSQLScript("zipScript.sql", em);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void getAllCityInfos() {
        CityInfosDTO cityInfosDTO = repo.getAllCityInfos();
        assertNotNull(cityInfosDTO);
    }

    @Test
    void runMigrationScript() {
        assertDoesNotThrow(() -> repo.runMigrationScript());
    }
}