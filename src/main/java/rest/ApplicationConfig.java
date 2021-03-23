package rest;

import java.util.HashSet;
import javax.ws.rs.ApplicationPath;
import java.util.Set;
import javax.ws.rs.core.Application;

@ApplicationPath("api")
public class ApplicationConfig extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new HashSet<>();
		addRestResourceClasses(resources);
		return resources;
	}

	/**
	 * Do not modify addRestResourceClasses() method. It is automatically
	 * populated with all resources defined in the project. If required, comment
	 * out calling this method in getClasses().
	 */
	private void addRestResourceClasses(Set<Class<?>> resources) {
		resources.add(org.glassfish.jersey.server.wadl.internal.WadlResource.class);
		resources.add(rest.CityInfoResource.class);
		resources.add(rest.PersonResource.class);
		resources.add(rest.RenameMeResource.class);
		resources.add(utils.cors.CorsRequestFilter.class);
		resources.add(utils.cors.CorsResponseFilter.class);
		resources.add(utils.errorhandling.GenericExceptionMapper.class);
	}

}
