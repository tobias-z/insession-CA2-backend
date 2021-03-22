package utils;

import facades.CityInfoFacade;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import javax.persistence.EntityManager;
import javax.ws.rs.WebApplicationException;
import org.apache.ibatis.jdbc.ScriptRunner;

public class ScriptUtils {

    /**
     * @param fileName the file has to be located in the resources directory
     * @param em method has to be called inside a transaction
     * @throws WebApplicationException will be handled by the GenericExceptionMapper
     */
    public static void runSQLScript(String fileName, EntityManager em) throws WebApplicationException {
        InputStream stream = ScriptUtils.class.getClassLoader().getResourceAsStream(fileName);
        if (stream == null) {
            throw new WebApplicationException("File: " + fileName + ", was not found", 500);
        }

        Connection connection = em.unwrap(Connection.class);
        ScriptRunner runner = new ScriptRunner(connection);

        System.out.println("Running script: " + fileName);
        runner.runScript(new BufferedReader(new InputStreamReader(stream)));
        System.out.println("Finished running script: " + fileName);
    }

}
