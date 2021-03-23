package facades;

import static org.junit.jupiter.api.Assertions.*;

import dtos.cityinfo.CityInfosDTO;
import dtos.hobby.HobbiesDTO;
import entities.cityinfo.CityInfoRepository;
import entities.hobby.HobbyRepository;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import utils.ScriptUtils;

class HobbyFacadeTest {

    private static EntityManagerFactory emf;
    private static HobbyRepository repo;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        repo = HobbyFacade.getInstance(emf);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            ScriptUtils.runSQLScript("hobbyScript.sql", em);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterAll
    static void tearDown() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void getAllHobbies() {
        HobbiesDTO hobbies = repo.getAllHobbies();
        assertNotNull(hobbies);
    }

    @Test
    void runMigrationScript() {
        assertDoesNotThrow(() -> repo.runMigrationScript());
    }
}