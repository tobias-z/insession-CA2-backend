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
import entities.person.Person;
import entities.person.PersonRepository;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import javax.persistence.NoResultException;
import javax.ws.rs.WebApplicationException;
import utils.ScriptUtils;


/**
 * @author peter
 */
public class PersonFacade implements PersonRepository {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    public PersonFacade() {
    }

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public PersonsDTO getAll() throws WebApplicationException {
        EntityManager em = getEntityManager();
        try {
            return new PersonsDTO(em.createNamedQuery("Person.getAllRows", Person.class).getResultList());
        } finally {
            em.close();
        }

    }

    // Create new person
    @Override
    public PersonDTO create(PersonDTO personDTO) {
        EntityManager em = getEntityManager();
        Person person = new Person(personDTO.getEmail(), personDTO.getFirstName(), personDTO.getLastName());

        if ((person.getFirstName().length() == 0) || (person.getLastName().length() == 0)) {
            throw new WebApplicationException("Name is missing", 400);
        }

        // Add each phone to the person
        for (PhoneDTO phoneDTO : personDTO.getPhones()) {

            try {
                Phone phoneAlreadyInUse = em.createQuery("SELECT p FROM Phone p WHERE p.number = :number", Phone.class)
                    .setParameter("number", phoneDTO.getNumber())
                    .getSingleResult();

                throw new WebApplicationException("Phone with number: " + phoneAlreadyInUse.getNumber() + ", is already beeing used", 400);
            } catch (NoResultException e) {
                Phone phoneToAdd = new Phone(phoneDTO.getNumber(), phoneDTO.getDescription());
                person.addPhone(phoneToAdd);
            }

        }

        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }


    // Edit person
    @Override
    public PersonDTO editPerson(PersonDTO p) {
        EntityManager em = getEntityManager();

        Person editPerson = em.find(Person.class, p.getId());

        if (editPerson == null) {
            throw new WebApplicationException(String.format("Person with id: (%d) not found", p.getId()),
                400);
        }

        editPerson.setEmail(p.getEmail());
        editPerson.setFirstName(p.getFirstName());
        editPerson.setLastName(p.getLastName());

        for (PhoneDTO phoneDTO : p.getPhones()) {
            for (Phone phone : editPerson.getPhones()) {
                phone.setNumber(phoneDTO.getNumber());
                phone.setDescription(phoneDTO.getDescription());
            }
        }

        try {
            em.getTransaction().begin();
            em.merge(editPerson);
            em.getTransaction().commit();
            return new PersonDTO(editPerson);
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO getById(int id) throws WebApplicationException {
        EntityManager em = getEntityManager();
        try {
            Person person = em.find(Person.class, id);
            if (person == null) {
                throw new WebApplicationException(String.format("Person with id: (%d) not found", id), 404);
            }
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    @Override
    public void runMigrationScript() throws WebApplicationException {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            // Dropping the table each time because otherwise it may cause multiple entry errors
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            ScriptUtils.runSQLScript("nameScript.sql", em);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }


}


