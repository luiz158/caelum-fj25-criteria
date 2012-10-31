package br.com.caelum.project.dao;

import javax.persistence.EntityManager;

import br.com.caelum.project.model.Department;

public class DepartmentDAO {

	private DAO<Department> dao;
	
	public DepartmentDAO(EntityManager entityManager) {
		this.dao = new DAO<Department>(entityManager, Department.class);
	}

	public void add(Department department) {
		this.dao.add(department);
	}

}
