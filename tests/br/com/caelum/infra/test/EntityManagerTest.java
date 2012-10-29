package br.com.caelum.infra.test;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.junit.Test;

import br.com.caelum.project.infra.JPAUtil;

public class EntityManagerTest {

	@Test
	public void deveriaAbrirOEntityManager() throws Exception {
		EntityManager entityManager = new JPAUtil().getEntityManager();
		
		assertNotNull(entityManager);
	}
	
}
