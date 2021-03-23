/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PersonDTO;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import entities.Person;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author peter
 */
public class PersonFacadeTest {
    
    private static Person p1;
    
    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    
    public PersonFacadeTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
       
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = PersonFacade.getPersonFacade(emf);
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
       
        EntityManager em = emf.createEntityManager(); 
       
        try {
            em.getTransaction().begin();
            //em.createNamedQuery("Person.deleteAllRows").executeUpdate(); 
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            p1 = new Person("Test2", "Test2", "Test2");
            em.persist(p1);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
        
    
    
    @AfterEach
    public void tearDown() {
    }

   
    @Test
    public void testAddPerson() {
        PersonDTO p;
        try {
            p = facade.addPerson("Gunner", "Nu", "Hansen");
            assertEquals("Nu", p.getFirstName().toString());
        } catch ( Exception e ) {
            System.out.println("testAddPerson: Something went wrong");
        }        
    }
    
    @Test
    public void testEditPerson() {
        p1.setLastName("Hansen");
        PersonDTO p1New = facade.editPerson(new PersonDTO(p1));
        assertEquals(p1New.getLastName(), p1.getLastName());
        assertNotEquals(p1.getLastName(),"Jensen");
    }
    
    
}
