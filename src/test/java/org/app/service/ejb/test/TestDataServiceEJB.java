package org.app.service.ejb.test;

import java.util.logging.Logger;

import org.app.service.ejb.DataService;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestDataServiceEJB {
	private static Logger logger = Logger.getLogger(TestDataServiceEJB.class.getName());
	private static DataService service;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		service = DataServiceEJBFactory.getScrumProjectRepositoryService();
	}
	
	@Test
	public void test() {
		logger.info("DEBUG: Junit TESTING ...");
		String response = service.sayRest();
		assertNotNull("Data Service failed!", response);
		logger.info("DEBUG: EJB Response ..." + response);
	}

}
