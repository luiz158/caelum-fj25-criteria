package br.com.caelum.project.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.caelum.project.model.Employee;

public class EmployeeDAO {

	private EntityManager entityManager;

	public EmployeeDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Employee getByNameWithJPQL(String name) {
		TypedQuery<Employee> query = entityManager.createQuery("select e from Employee e where e.name = :name", Employee.class);
		query.setParameter("name", name);
		
		return query.getSingleResult();
	}

	public Employee getByNameWithCriteria(String name) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Employee> criteriaQuery = builder.createQuery(Employee.class);
		Root<Employee> rootEmp = criteriaQuery.from(Employee.class);
		criteriaQuery.select(rootEmp).where(builder.equal(rootEmp.get("name"), name));
		
		TypedQuery<Employee> query = entityManager.createQuery(criteriaQuery);
		
		return query.getSingleResult();
	}

}
