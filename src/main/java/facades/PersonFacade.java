/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PersonDTO;
import entities.Person;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.WebApplicationException;
import utils.EMF_Creator;


/**
 *
 * @author peter
 */
public class PersonFacade {
    
    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    public PersonFacade() {}
    
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }
    
    private EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("pu");
        return emf.createEntityManager();
    }
    
    // Create new person
    public PersonDTO addPerson(String email, String firstname, String lastName ) {
        EntityManager em = getEntityManager();
        Person person = new Person(email, firstname, lastName);
        
        try {
        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
        
        } finally {
          em.close();
        }
        return new PersonDTO();
    }
    
    
    // Edit person
    public PersonDTO editPerson(PersonDTO p) {
        EntityManager em = getEntityManager();
       
        Person editPerson = em.find(Person.class, p.getId());
        
        if (editPerson == null) {
            throw new WebApplicationException(String.format("Person with id: (%d) not found", p.getId()), 400);
        }
        
        editPerson.setEmail(p.getEmail());
        editPerson.setFirstName(p.getFirstName());
        editPerson.setLastName(p.getLastName());        
        
        try {
            em.getTransaction().begin();
            em.merge(editPerson);
            em.getTransaction().commit();
            return new PersonDTO(editPerson);
        } finally {  
          em.close();
        }   
    }
    
    public static void main(String[] args) {
        PersonFacade x = new PersonFacade();
        x.addPerson("Hans", "Jørgen", "Tumlesen");
        
    }
    
   
}


