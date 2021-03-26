/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;
import dtos.PhoneDTO;
import dtos.cityinfo.CityInfoDTO;
import dtos.hobby.HobbyDTO;
import entities.Address;
import entities.Phone;
import entities.cityinfo.CityInfo;
import entities.hobby.Hobby;
import entities.person.Person;
import entities.person.PersonRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NamedQuery;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
    public PersonDTO create(PersonDTO personDTO) throws WebApplicationException {
        EntityManager em = getEntityManager();
        Person person = new Person(personDTO.getEmail(), personDTO.getFirstName(), personDTO.getLastName());

        if ((person.getFirstName().length() == 0) || (person.getLastName().length() == 0)) {
            throw new WebApplicationException("Name is missing", 400);
        }

        // Add Hobby to person
        for (HobbyDTO hobbyDTO : personDTO.getHobbies()) {
            try {
                Hobby hobby = em.createQuery("SELECT h FROM Hobby h WHERE h.name = :hobby", Hobby.class)
                        .setParameter("hobby", hobbyDTO.getName())
                        .getSingleResult();
                person.addHobby(hobby);
            } catch (NoResultException err) {
                throw new WebApplicationException("Hobby: " + hobbyDTO.getName() + ", does not exist", 400);
            }
        }

        // Add each phone to the person
        for (PhoneDTO phoneDTO : personDTO.getPhones()) {

            try {
                Phone phoneAlreadyInUse = em
                        .createQuery("SELECT p FROM Phone p WHERE p.number = :number", Phone.class)
                        .setParameter("number", phoneDTO.getNumber())
                        .getSingleResult();

                throw new WebApplicationException(
                        "Phone with number: " + phoneAlreadyInUse.getNumber() + ", is already beeing used", 400);
            } catch (NoResultException e) {
                Phone phoneToAdd = new Phone(phoneDTO.getNumber(), phoneDTO.getDescription());
                person.addPhone(phoneToAdd);
            }

        }

        // Create address
        Address address = new Address(personDTO.getAddress().getStreet(),
                personDTO.getAddress().getAdditionalInfo());
        CityInfo cityInfo = em.find(CityInfo.class, personDTO.getAddress().getCityInfo().getZipCode());
        if (cityInfo == null) {
            throw new WebApplicationException(
                    "No such zipCode exists: " + personDTO.getAddress().getCityInfo().getZipCode(), 400);
        }
        address.setCityInfo(cityInfo);
        person.setAddress(address);

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

        // Edit phones
        for (int i = 0; i < p.getPhones().size(); i++) {
            PhoneDTO phoneDTO = p.getPhones().get(i);
            try {
                Phone phone = editPerson.getPhones().get(i);
                phone.setNumber(phoneDTO.getNumber());
                phone.setDescription(phoneDTO.getDescription());
            } catch (IndexOutOfBoundsException e) {
                //If a phone that doesnt already exist has been added, this will be thrown
                editPerson.addPhone(new Phone(phoneDTO));
            }
        }

        editPerson.getHobbies().clear();
        for (int i = 0; i < p.getHobbies().size(); i++) {
            HobbyDTO hobbyDTO = p.getHobbies().get(i);

            try {
                Hobby foundHobby = em
                        .createQuery("SELECT h FROM Hobby h WHERE h.name = :hobby", Hobby.class)
                        .setParameter("hobby", hobbyDTO.getName())
                        .getSingleResult();
                editPerson.addHobby(foundHobby);
            } catch (NoResultException error) {
                throw new WebApplicationException("Hobby: " + hobbyDTO.getName() + ", does not exist",
                        400);
            }
        }

        // Edit address
        Address addressInUse = em.find(Address.class, p.getAddress().getStreet());
        if (addressInUse != null) {
            // use this address
            editPerson.setAddress(addressInUse);
        } else {
            // create a new address
            Address newAddress = new Address(p.getAddress().getStreet(), p.getAddress().getAdditionalInfo());
            CityInfo cityInfo = em.find(CityInfo.class, p.getAddress().getCityInfo().getZipCode());
            if (cityInfo == null) {
                throw new WebApplicationException(
                        "Zipcode: " + p.getAddress().getCityInfo().getZipCode() + ", does not exist", 404);
            }
            newAddress.setCityInfo(cityInfo);
            editPerson.setAddress(newAddress);
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

    public PersonDTO getByNumber(String number) throws WebApplicationException {
        EntityManager em = getEntityManager();

        try {
            Person person = em
                    .createQuery("SELECT p FROM Person p JOIN p.phones phone WHERE phone.number = :number", Person.class)
                    .setParameter("number", number)
                    .getSingleResult();
            return new PersonDTO(person);
        } catch (NoResultException e) {
            throw new WebApplicationException("No number" + number, 404);
        }
    }

    public PersonsDTO getByZip(String zipCode) throws WebApplicationException {
        EntityManager em = emf.createEntityManager();
        try {
            List<Person> persons = em
                    .createQuery("SELECT p FROM Person p JOIN p.address a JOIN a.cityInfo c WHERE c.zipCode = :zipCode", Person.class)
                    .setParameter("zipCode", zipCode)
                    .getResultList();
            if (persons.isEmpty()) {
                throw new WebApplicationException(String.format("City with zip: (%S) not found", zipCode), 404);
            }
            return new PersonsDTO(persons);
        } finally {
            em.close();
        }
    }

    public PersonsDTO getByHobby(String hobby) throws WebApplicationException {
        EntityManager em = emf.createEntityManager();
        try {
            List<Person> persons = em
                    .createQuery("SELECT p FROM Person p JOIN p.hobbies h WHERE h.name = :hobby", Person.class)
                    .setParameter("hobby", hobby)
                    .getResultList();
            if (persons.isEmpty()) {
                throw new WebApplicationException(String.format("No persons with such hobby: (%S) found", hobby), 404);
            }
            return new PersonsDTO(persons);
        } finally {
            em.close();
        }
    }

}
