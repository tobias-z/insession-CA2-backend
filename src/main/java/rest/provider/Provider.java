package rest.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

// Will make sure that all resources produce and consume json
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public abstract class Provider implements RestRepository {

    //Add any helper here
    protected static EntityManagerFactory EMF;
    protected static Gson GSON;

    static {
        EMF = EMF_Creator.createEntityManagerFactory();
        GSON = new GsonBuilder().setPrettyPrinting().create();
    }
}

