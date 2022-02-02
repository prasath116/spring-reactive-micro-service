/*
 * package com.prs.services.employee;
 * 
 * import java.util.List;
 * 
 * import org.junit.jupiter.api.Test; import org.springframework.util.Assert;
 * 
 * import com.prs.services.employee.model.Employee; import
 * com.prs.services.employee.repository.EmployeeRepository;
 * 
 * public class EmployeeRepositoryTest {
 * 
 * private static EmployeeRepository repository = new EmployeeRepository();
 * 
 * @Test public void testAddEmployee() { Employee employee = new Employee(1L,
 * 1L, "Test Test", 100, "Test"); employee = repository.add(employee);
 * Assert.notNull(employee, "Employee is null."); Assert.isTrue(employee.getId()
 * == 1L, "Employee bad id."); }
 * 
 * @Test public void testFindAll() { List<Employee> employees =
 * repository.findAll(); Assert.isTrue(employees.size() == 1,
 * "Employees size is wrong."); Assert.isTrue(employees.get(0).getId() == 1L,
 * "Employee bad id."); }
 * 
 * @Test public void testFindByDepartment() { List<Employee> employees =
 * repository.findByDepartment(1L); Assert.isTrue(employees.size() == 1,
 * "Employees size is wrong."); Assert.isTrue(employees.get(0).getId() == 1L,
 * "Employee bad id."); }
 * 
 * @Test public void testFindByCollege() { List<Employee> employees =
 * repository.findByCollege(1L); Assert.isTrue(employees.size() == 1,
 * "Employees size is wrong."); Assert.isTrue(employees.get(0).getId() == 1L,
 * "Employee bad id."); }
 * 
 * @Test public void testFindById() { Employee employee =
 * repository.findById(1L); Assert.notNull(employee, "Employee not found.");
 * Assert.isTrue(employee.getId() == 1L, "Employee bad id."); }
 * 
 * }
 */