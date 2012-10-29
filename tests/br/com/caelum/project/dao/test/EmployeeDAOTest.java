package br.com.caelum.project.dao.test;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.project.dao.EmployeeDAO;
import br.com.caelum.project.infra.JPAUtil;
import br.com.caelum.project.model.Employee;

public class EmployeeDAOTest {

	private EntityManager entityManager;

	@Before
	public void setUp() {
		entityManager = new JPAUtil().getEntityManager();
	}
	
	@Test
	public void shouldBeReturnedAnEmployeeUsingJPQL() throws Exception {
		EmployeeDAO employeeDAO = new EmployeeDAO(entityManager);
		
		Employee john = employeeDAO.getByNameWithJPQL("John");
		
		assertEquals("John", john.getName());
	}
	
	@Test
	public void shouldBeReturnedAnEmployeeUsingCriteria() throws Exception {
		EmployeeDAO employeeDAO = new EmployeeDAO(entityManager);
		
		Employee john = employeeDAO.getByNameWithCriteria("John");
		
		assertEquals("John", john.getName());
	}
}
