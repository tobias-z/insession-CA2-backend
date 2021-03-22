package rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

import dtos.cityinfo.CityInfoDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.sql.Connection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

class CityInfoResourceTest {

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
        InputStream stream = CityInfoResourceTest.class.getClassLoader().getResourceAsStream("zipScript.sql");
        if (stream == null) {
            System.out.println("Migration file, does not exist: ");
            throw new RuntimeException("zipScript.sql");
        }
        try {
            em.getTransaction().begin();
            Connection connection = em.unwrap(Connection.class);
            ScriptRunner runner = new ScriptRunner(connection);
            runner.runScript(new BufferedReader(new InputStreamReader(stream)));
            em.getTransaction().commit();
            System.out.println("Finished running script");
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
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/city").then().statusCode(200);
    }


    @Test
    void getAll() {
        List<CityInfoDTO> foundCities;

        foundCities = given()
            .contentType(ContentType.JSON)
            .get("/city")
            .then()
            .statusCode(200)
            .extract().body().jsonPath().getList("all", CityInfoDTO.class);

        assertThat(foundCities, hasItem(new CityInfoDTO("0877", "København C")));

    }

    @Test
    void runMigrationScript() {
        given()
            .contentType(ContentType.JSON)
            .get("/city/runscript")
            .then()
            .statusCode(200)
            .body("worked", equalTo("yay!"));
    }
}