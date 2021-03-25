    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package facades;

    import dtos.PersonDTO;
    import dtos.PersonsDTO;
    import dtos.PhoneDTO;
    import dtos.hobby.HobbyDTO;
    import entities.Address;
    import entities.Phone;
    import entities.cityinfo.CityInfo;
    import entities.hobby.Hobby;
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
        private static Hobby hobby1;
        private static Hobby hobby2;
        private static Address address1;
        private static CityInfo cityInfo1;
        private static CityInfo cityInfo2;

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
            hobby1 = new Hobby("Turisme", "https://en.wikipedia.org/wiki/Tourism", "Generel", "Konkurrence");
            address1 = new Address("street", "Info");
            cityInfo1 = new CityInfo("0894", "Udbetaling");

            // These are not put on the person
            hobby2 = new Hobby("Hobby2", "https://en.wikipedia.org/wiki/hobby2", "Generel", "Konkurrence");
            cityInfo2 = new CityInfo("0899", "Kommuneservice");

            p1.addPhone(phone1);

            try {
                em.getTransaction().begin();
                em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
                em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
                em.createNamedQuery("Person.deleteAllRows").executeUpdate();
                em.createNamedQuery("Address.deleteAllRows").executeUpdate();
                em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
                em.persist(hobby1);
                em.persist(cityInfo1);
                p1.addHobby(hobby1);

                address1.setCityInfo(cityInfo1);
                p1.setAddress(address1);

                em.persist(p1);
                em.persist(hobby2);
                em.persist(cityInfo2);
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
            Phone phone3 = new Phone("1234567", "insane number");
            Phone phone2 = new Phone("21314221", "even better number");
            person.addPhone(phone3);
            person.addPhone(phone2);
            Hobby hobby = new Hobby("Turisme", "https://en.wikipedia.org/wiki/Tourism", "Generel",
                "Konkurrence");
            person.addHobby(hobby);
            Address address = new Address("insanestreet", "this is a street");
            address.setCityInfo(new CityInfo("0899", "Kommuneservice"));
            person.setAddress(address);

            PersonDTO createdPerson = new PersonDTO(person);
            p = facade.create(createdPerson);

            for (HobbyDTO hobbyDTO : p.getHobbies()) {
                assertNotNull(hobbyDTO);
            }

            assertEquals("Nu", p.getEmail());
            for (PhoneDTO phoneDTO : p.getPhones()) {
                assertNotNull(phoneDTO);
            }

            assertEquals(address.getStreet(), createdPerson.getAddress().getStreet());
        }


        @Test
        public void testEditPerson() {
            // Setup
            p1.setLastName("Hansen");
            p1.addPhone(new Phone("4321", "this is a description"));
            Hobby hobby = new Hobby("Hobby2", "https://en.wikipedia.org/wiki/hobby2", "Generel", "Konkurrence");
            p1.addHobby(hobby);
            Address address = new Address("Anotherstreeet", "Kommuneservice");
            address.setCityInfo(cityInfo2);
            p1.setAddress(address);

            PersonDTO p1New = facade.editPerson(new PersonDTO(p1));
            assertEquals(p1New.getLastName(), p1.getLastName());
            assertNotEquals(p1.getLastName(), "Jensen");

            assertEquals(p1New.getPhones().size(), 2);

            for (int i = 0; i < p1.getHobbies().size(); i++) {
                assertEquals(p1.getHobbies().get(i).getName(), p1New.getHobbies().get(i).getName());
            }

            assertEquals(address.getStreet(), p1New.getAddress().getStreet());

        }


    }
