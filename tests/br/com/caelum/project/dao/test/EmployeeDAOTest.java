package br.com.caelum.project.dao.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.project.builder.EmployeeBuilder;
import br.com.caelum.project.dao.DepartmentDAO;
import br.com.caelum.project.dao.EmployeeDAO;
import br.com.caelum.project.infra.JPAUtil;
import br.com.caelum.project.model.Department;
import br.com.caelum.project.model.Employee;

public class EmployeeDAOTest {

	private EntityManager entityManager;

	@Before
	public void setUp() {
		entityManager = new JPAUtil().getEntityManager();
		entityManager.getTransaction().begin();
		
		List<Employee> employees = entityManager.createQuery("select e from Employee e", Employee.class).getResultList();
		for (Employee employee : employees) {
			entityManager.remove(employee);
		}
	}
	
	@Test
	public void shouldBeReturnedAnEmployeeUsingJPQL() throws Exception {
		insertNewEmployee("John");
		
		EmployeeDAO employeeDAO = new EmployeeDAO(entityManager);
		
		Employee john = employeeDAO.getByNameWithJPQL("John");
		
		assertEquals("John", john.getName());
	}
	
	@Test
	public void shouldBeReturnedAnEmployeeUsingCriteria() throws Exception {
		insertNewEmployee("John");
		
		EmployeeDAO employeeDAO = new EmployeeDAO(entityManager);
		
		Employee john = employeeDAO.getByNameWithCriteria("John");
		
		assertEquals("John", john.getName());
	}
	
	@Test
	public void shouldBeReturnedAllEmployeesUsingFilterWithoutInformations() throws Exception {
		Department technologyDepartment = new Department("IT"); 
		Employee john = new EmployeeBuilder().named("John").inDepartment(technologyDepartment).inCity("Irvine").buid();
		Employee alex = new EmployeeBuilder().named("Alex").inDepartment(technologyDepartment).inCity("Berkley").buid();
		
		Department marketingDepartment = new Department("Marketing");
		Employee michael = new EmployeeBuilder().named("Michael").inDepartment(marketingDepartment).inCity("Irvine").buid();
		Employee drake = new EmployeeBuilder().named("Drake").inDepartment(marketingDepartment).inCity("Berkley").buid();
		
		DepartmentDAO departmentDAO = new DepartmentDAO(entityManager);
		departmentDAO.add(technologyDepartment);
		departmentDAO.add(marketingDepartment);
		
		EmployeeDAO employeeDAO = new EmployeeDAO(entityManager);
		employeeDAO.add(john);
		employeeDAO.add(alex);
		employeeDAO.add(michael);
		employeeDAO.add(drake);
		
		List<Employee> employees = employeeDAO.findByFilter_WithParse(null, null);
		
		assertEquals(4, employees.size());
	}
	
	@Test
	public void shouldBeReturnedAnEmployeeUsingFilterCityAndDeparment_UglyWay() throws Exception {
		Department technologyDepartment = new Department("IT"); 
		Employee john = new EmployeeBuilder().named("John").inDepartment(technologyDepartment).inCity("Irvine").buid();
		Employee alex = new EmployeeBuilder().named("Alex").inDepartment(technologyDepartment).inCity("Berkley").buid();
		
		Department marketingDepartment = new Department("Marketing");
		Employee michael = new EmployeeBuilder().named("Michael").inDepartment(marketingDepartment).inCity("Irvine").buid();
		Employee drake = new EmployeeBuilder().named("Drake").inDepartment(marketingDepartment).inCity("Berkley").buid();
		
		DepartmentDAO departmentDAO = new DepartmentDAO(entityManager);
		departmentDAO.add(technologyDepartment);
		departmentDAO.add(marketingDepartment);
		
		EmployeeDAO employeeDAO = new EmployeeDAO(entityManager);
		employeeDAO.add(john);
		employeeDAO.add(alex);
		employeeDAO.add(michael);
		employeeDAO.add(drake);
		
		List<Employee> employees = employeeDAO.findByFilter_WithParse("IT", null);
		
		assertEquals(2, employees.size());
	}
	
	@Test
	public void shouldBeReturnedAnEmployeeUsingFilterCityAndDeparment_NiceWay() throws Exception {
		Department technologyDepartment = new Department("IT"); 
		Employee john = new EmployeeBuilder().named("John").inDepartment(technologyDepartment).inCity("Irvine").buid();
		Employee alex = new EmployeeBuilder().named("Alex").inDepartment(technologyDepartment).inCity("Berkley").buid();
		
		Department marketingDepartment = new Department("Marketing");
		Employee michael = new EmployeeBuilder().named("Michael").inDepartment(marketingDepartment).inCity("Irvine").buid();
		Employee drake = new EmployeeBuilder().named("Drake").inDepartment(marketingDepartment).inCity("Berkley").buid();
		
		DepartmentDAO departmentDAO = new DepartmentDAO(entityManager);
		departmentDAO.add(technologyDepartment);
		departmentDAO.add(marketingDepartment);
		
		EmployeeDAO employeeDAO = new EmployeeDAO(entityManager);
		employeeDAO.add(john);
		employeeDAO.add(alex);
		employeeDAO.add(michael);
		employeeDAO.add(drake);
		
		List<Employee> employees = employeeDAO.findByFilter_WithCriteria("IT", "Irvine");
		
		assertEquals(1, employees.size());
		assertEquals("John", employees.get(0).getName());
	}
	
	@Test
	public void shouldBeReturnedAnEmployeeUsingFilterDeparment_NiceWay() throws Exception {
		Department technologyDepartment = new Department("IT"); 
		Employee john = new EmployeeBuilder().named("John").inDepartment(technologyDepartment).inCity("Irvine").buid();
		Employee alex = new EmployeeBuilder().named("Alex").inDepartment(technologyDepartment).inCity("Berkley").buid();
		
		Department marketingDepartment = new Department("Marketing");
		Employee michael = new EmployeeBuilder().named("Michael").inDepartment(marketingDepartment).inCity("Irvine").buid();
		Employee drake = new EmployeeBuilder().named("Drake").inDepartment(marketingDepartment).inCity("Berkley").buid();
		
		DepartmentDAO departmentDAO = new DepartmentDAO(entityManager);
		departmentDAO.add(technologyDepartment);
		departmentDAO.add(marketingDepartment);
		
		EmployeeDAO employeeDAO = new EmployeeDAO(entityManager);
		employeeDAO.add(john);
		employeeDAO.add(alex);
		employeeDAO.add(michael);
		employeeDAO.add(drake);
		
		List<Employee> employees = employeeDAO.findByFilter_WithCriteria("IT", null);
		
		assertEquals(2, employees.size());
	}
	
	private void insertNewEmployee(String name) {
		Employee john = new Employee();
		john.setName(name);
		entityManager.persist(john);
	}

	@After
	public void setDown() {
		entityManager.getTransaction().commit();
	}	
	
}
