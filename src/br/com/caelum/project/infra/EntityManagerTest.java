package br.com.caelum.project.infra;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.junit.Test;

public class EntityManagerTest {

	@Test
	public void deveriaAbrirOEntityManager() throws Exception {
		EntityManager entityManager = new JPAUtil().getEntityManager();
		
		assertNotNull(entityManager);
	}
	
}
