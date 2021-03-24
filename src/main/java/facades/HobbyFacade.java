package facades;

import dtos.hobby.HobbiesDTO;
import entities.hobby.Hobby;
import entities.hobby.HobbyRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.WebApplicationException;
import utils.ScriptUtils;

public class HobbyFacade implements HobbyRepository {

    private static HobbyFacade instance;
    private static EntityManagerFactory emf;

    private HobbyFacade() {
    }

    public static HobbyFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HobbyFacade();
        }
        return instance;
    }

    @Override
    public HobbiesDTO getAllHobbies() throws WebApplicationException {
        EntityManager em = emf.createEntityManager();
        List<Hobby> hobbies = em.createQuery("SELECT h FROM Hobby h", Hobby.class).getResultList();

        if (hobbies == null) {
            throw new WebApplicationException("No hobbies were found, consider running the migration script: hobbies/runscript", 404);
        }

        return new HobbiesDTO(hobbies);
    }

    @Override
    public void runMigrationScript() throws WebApplicationException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            ScriptUtils.runSQLScript("hobbyScript.sql", em);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}