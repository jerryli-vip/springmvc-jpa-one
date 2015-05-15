package com.pactera.web.dao.test;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.pactera.web.exception.ServiceException;
import com.pactera.web.model.Department;
import com.pactera.web.model.Employee;
import com.pactera.web.service.EmployeeService;

public class EmployeeTest extends BaseJunit4Test {

	@Autowired
	EmployeeService service;

	@Test
	public void testFindAll() {
		List<Employee> list = null;
		try {
			list = service.findAll();
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		Assert.notNull(list);
		Assert.isTrue(list.size() > 0);
	}

	@Test
	@Transactional
	@Rollback(false)
	public void testSave() {
		Department dept = new Department();

		dept.setDeptName("Market2");
		dept.setLocation("Shen zhen");

		Employee emp = new Employee();
		emp.setEmpName("Michelle");
		emp.setGender("F");
		emp.setHiredate(new Date());
		emp.setSalary(1500);
		Set<Employee> emps = new HashSet<Employee>();
		emps.add(emp);
		emp.setDept(dept);

		dept.setEmployees(emps);

		boolean b = false;
		try {
			b = service.save(emp);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		Assert.isTrue(b);
	}

	@Test
	@Transactional
	@Rollback(false)
	public void testUpdate() {
		boolean b = false;
		try {
			Employee emp = service.findById(Employee.class, 30);
			emp.setSalary(2000);
			b = service.save(emp);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		Assert.isTrue(b);
	}

	@Test
	@Rollback(false)
	public void testDelete() {
		try {
			service.delete(30);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Assert.isTrue(true);
	}

}
