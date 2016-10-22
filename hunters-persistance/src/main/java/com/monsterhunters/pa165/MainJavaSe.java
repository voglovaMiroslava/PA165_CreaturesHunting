package com.monsterhunters.pa165;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public class MainJavaSe {
	private static EntityManagerFactory emf;

	public static void main(String[] args) throws SQLException {
		// The following line is here just to start up a in-memory database
		new AnnotationConfigApplicationContext(InMemoryDatabaseSpring.class);

		emf = Persistence.createEntityManagerFactory("default");


		emf.close();
	}



}
