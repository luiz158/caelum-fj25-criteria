package br.com.caelum.project.builder;

import br.com.caelum.project.model.Department;
import br.com.caelum.project.model.Employee;

public class EmployeeBuilder {

	private Employee employee;

	public EmployeeBuilder() {
		employee = new Employee();
	}

	public EmployeeBuilder named(String name) {
		this.employee.setName(name);
		return this;
	}

	public EmployeeBuilder withEmail(String email) {
		this.employee.setEmail(email);
		return this;
	}

	public EmployeeBuilder withPhone(String phone) {
		this.employee.setPhone(phone);
		return this;
	}

	public EmployeeBuilder inDepartment(Department department) {
		this.employee.setDepartment(department);
		return this;
	}

	public EmployeeBuilder inCity(String string) {
		this.employee.setCity(string);
		return this;
	}
	
	public Employee buid() {
		return this.employee;
	}
	
}
