/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import dtos.PersonDTO;
import entities.Address;
import entities.Phone;
import entities.cityinfo.CityInfo;
import entities.hobby.Hobby;
import entities.person.Person;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;

import utils.EMF_Creator;
import utils.ScriptUtils;

/**
 * @author peter
 */
@Disabled
public class PersonResourceTest {


    private static Person p1, p2;
    private static Phone phone1, phone2;
    private static Hobby hobby1, hobby2;
    private static Address address1, address2;
    private static CityInfo cityInfo1, cityInfo2;

    // Used to edit
    private static Hobby hobby3;
    private static CityInfo cityInfo3;

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;

    }

    @AfterAll
    public static void closeTestServer() {
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }


    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        // Person 1 setup
        p1 = new Person("test1", "Test1", "Test1");
        phone1 = new Phone("1234", "number1");
        p1.addPhone(phone1);
        hobby1 = new Hobby("Turisme", "https://en.wikipedia.org/wiki/Tourism", "Generel", "Konkurrence");
        address1 = new Address("street", "Info");
        cityInfo1 = new CityInfo("0894", "Udbetaling");

        // Person 2 setup
        p2 = new Person("test2", "Test2", "Test2");
        phone2 = new Phone("12345", "number2");
        p2.addPhone(phone2);
        hobby2 = new Hobby("Bordtennis", "https://en.wikipedia.org/wiki/Table_tennis","Generel"," Konkurrence");
        address2 = new Address("address2j", "Info");
        cityInfo2 = new CityInfo("2800", "Kongens Lyngby");

        // These are not put on the person
        hobby3 = new Hobby("Hobby3", "https://en.wikipedia.org/wiki/hobby2", "Generel", "Konkurrence");
        cityInfo3 = new CityInfo("0899", "Kommuneservice");

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();

            // Person 1
            em.persist(hobby1);
            em.persist(cityInfo1);
            p1.addHobby(hobby1);
            address1.setCityInfo(cityInfo1);
            p1.setAddress(address1);

            // Person 2
            em.persist(hobby2);
            em.persist(cityInfo2);
            p2.addHobby(hobby2);
            address2.setCityInfo(cityInfo2);
            p2.setAddress(address2);

            em.persist(p1);
            em.persist(p2);
            em.persist(hobby3);
            em.persist(cityInfo3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/persons").then().statusCode(200);
    }

    @Test
    public void testGetAll() {
        List<PersonDTO> foundPersons;
        foundPersons = given()
            .get("/persons")
            .then()
            .statusCode(200)
            .extract().body().jsonPath().getList("all", PersonDTO.class);
        assertThat(foundPersons, hasItem(new PersonDTO(p1)));
    }

    @Test
    public void testCreate() {
        // Setup PersonDTO
        Person person = new Person("Egon", "Keld", "Benny");
        Phone phone = new Phone("654321", "this is an awesome number");
        person.addPhone(phone);
        Hobby hobby = new Hobby("Hobby3", "https://en.wikipedia.org/wiki/hobby2", "Generel", "Konkurrence");
        person.addHobby(hobby);
        Address address = new Address("insanestreet", "this is a street");
        address.setCityInfo(new CityInfo("0899", "Kommuneservice"));
        person.setAddress(address);

        PersonDTO requestBody = new PersonDTO(person);

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("persons")
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK_200.getStatusCode());
    }

    @Test
    public void testUpdate() {
        Person person = new Person("Ego", "Keld", "Benny");
        Phone phone = new Phone("654321", "this is an awesome number");
        person.addPhone(phone);
        Hobby hobby = new Hobby("anotherhobby", "https://en.wikipedia.org/wiki/hobby2", "Generel", "Konkurrence");
        person.addHobby(hobby);
        Address address = new Address("insanestreet", "this is a street");
        address.setCityInfo(new CityInfo("0899", "Kommuneservice"));
        person.setAddress(address);

        PersonDTO requestBody = new PersonDTO(person);

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .put("persons/" + p1.getId())
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK_200.getStatusCode());
    }


}
