package br.com.caelum.project.infra;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("database-criteria");
	
	public EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
	
}
