/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import dtos.PhoneDTO;
import entities.Phone;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import entities.person.Person;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author peter
 */
public class PersonFacadeTest {

    private static Person p1;
    private static Phone phone1;

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
        p1 = new Person("Test2", "Test2", "Test2");
        phone1 = new Phone("1234", "number");
        p1.addPhone(phone1);

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
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
    public void testGetall() {
        List<PersonDTO> list = new ArrayList<>();
        list.add(new PersonDTO(p1));
        PersonsDTO pdto = facade.getAll();
        assertEquals(list.size(), pdto.getAll().size());
    }

    @Test
    public void testGetById() {
        int expected = p1.getId();
        PersonDTO actual = facade.getById(p1.getId());
        assertEquals(expected, actual.getId());
    }

    @Test
    public void testAddPerson() {
        //setup
        PersonDTO p;
        Person person = new Person("Nu", "firstname", "lastname");
        Phone phone1 = new Phone("1234567", "insane number");
        Phone phone2 = new Phone("21314221", "even better number");
        person.addPhone(phone1);
        person.addPhone(phone2);

        PersonDTO createdPerson = new PersonDTO(person);
        p = facade.create(createdPerson);

        assertEquals("Nu", p.getEmail());
        for (PhoneDTO phoneDTO : p.getPhones()) {
            assertNotNull(phoneDTO);
        }
    }


    @Test
    public void testEditPerson() {
        p1.setLastName("Hansen");
        p1.addPhone(new Phone("4321", "this is a description"));
        PersonDTO p1New = facade.editPerson(new PersonDTO(p1));
        assertEquals(p1New.getLastName(), p1.getLastName());
        assertNotEquals(p1.getLastName(), "Jensen");
    }


}
