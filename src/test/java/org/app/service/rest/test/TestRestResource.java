package org.app.service.rest.test;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.Test;
import org.jboss.resteasy.util.GenericType;

/* Test CRUD on Rest with POST, GET, PUT, DELETE requests
 * EJB-REST: ProjectSprintDataServiceRest
 * 
*/
public class TestRestResource {
	private static Logger logger = Logger.getLogger(TestRestResource.class.getName());
	@Test
	public void testGetMessage() throws Exception{
		RESTfullResource<String> resource = new RESTfullResource("http://localhost:8080/SAM3/service");
		String message = resource.get();
		assertNotNull("Message not returned from service!", message);
		logger.info("DEBUG testGetMessage: " + message);		
	}
}