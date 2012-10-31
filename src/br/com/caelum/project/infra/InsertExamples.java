package br.com.caelum.project.infra;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Test;

import br.com.caelum.project.model.Employee;

public class InsertExamples {

	@Test
	public void insertExamples() throws Exception {
		EntityManager entityManager = new JPAUtil().getEntityManager();
		entityManager.getTransaction().begin();
		
		List<Employee> employees = entityManager.createQuery("select e from Employee e", Employee.class).getResultList();
		for (Employee employee : employees) {
			entityManager.remove(employee);
		}
		
		Employee john = new Employee();
		john.setName("John");
		john.setEmail("john@gmail.com");
		john.setPhone("12345678");
		
		Employee michael = new Employee();
		michael.setName("michael");
		michael.setEmail("michael@gmail.com");
		michael.setPhone("87654321");
		
		entityManager.persist(john);
		entityManager.persist(michael);
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
}
