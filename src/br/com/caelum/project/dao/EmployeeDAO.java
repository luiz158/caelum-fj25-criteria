package br.com.caelum.project.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.caelum.project.model.Employee;

public class EmployeeDAO {

	private EntityManager entityManager;

	private DAO<Employee> dao;
	
	public EmployeeDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.dao = new DAO<Employee>(entityManager, Employee.class);
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

	public List<Employee> findByFilter_WithParse(String departmentName, String cityName) {
		String stringQuery = 
			"select " +
			"	e " +
			"from " +
			"	Employee e " +
			"left join e.department d";
		
		TypedQuery<Employee> query = entityManager.createQuery(stringQuery, Employee.class);
		
		List<String> criteria = new ArrayList<String>();
		if(departmentName != null && !departmentName.isEmpty()) {
			criteria.add("d.name = :departmentName");
		}
		if(cityName != null && !cityName.isEmpty()) {
			criteria.add("e.city = :cityName");
		}
		
		if(criteria.size() > 0) {
			stringQuery += " where ";
			for(int i = 0; i < criteria.size(); i++) {
				if(i > 0) {
					stringQuery += " and ";
				}
				stringQuery += criteria.get(i);
			}
		}

		query = entityManager.createQuery(stringQuery, Employee.class);
		if(criteria.size() > 0) {
			if(departmentName != null && !departmentName.isEmpty()) {
				query.setParameter("departmentName", departmentName);
			}
			if(cityName != null && !cityName.isEmpty()) {
				query.setParameter("cityName", cityName);
			}
		}
		
		return query.getResultList();
	}

	public List<Employee> findByFilter_WithCriteria(String departmentName, String cityName) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
		Root<Employee> emp = cq.from(Employee.class);
		cq.select(emp);
		cq.distinct(true);
		
		List<Predicate> criteria = new ArrayList<Predicate>();
		
		if(cityName != null) {
			ParameterExpression<String> p = cb.parameter(String.class, "city");
			criteria.add(cb.equal(emp.get("city"), p));
		}
		if(departmentName != null) {
			ParameterExpression<String> p = cb.parameter(String.class, "name");
			criteria.add(cb.equal(emp.get("department").get("name"), p));
		}
		
		if (criteria.size() == 0) {
			throw new RuntimeException("no criteria");
		}
		else if(criteria.size() == 1) {
			cq.where(criteria.get(0));
		}
		else {
			cq.where(cb.and(criteria.toArray(new Predicate[0])));
		}
		
		TypedQuery<Employee> query = entityManager.createQuery(cq);
		
		if(departmentName != null) {
			query.setParameter("name", departmentName);
		}
		if(cityName != null) {
			query.setParameter("city", cityName);
		}
			
		return query.getResultList();
	}
	
	public void add(Employee john) {
		this.dao.add(john);
	}


}
