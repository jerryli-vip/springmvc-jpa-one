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
import com.pactera.web.service.DepartmentService;

public class DepartmentTest extends BaseJunit4Test {

	@Autowired
	DepartmentService deptService;

	@Test
	public void testFindAll() {
		List<Department> deptList = null;
		try {
			deptList = deptService.findAll();
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		Assert.notNull(deptList);
		Assert.isTrue(deptList.size() > 0);
	}

	@Test
	@Transactional
	@Rollback(false)
	public void testSave() {
		Department dept = new Department();

		dept.setDeptName("Development");
		dept.setLocation("Nan shan");

		Employee emp = new Employee();
		emp.setEmpName("Obama");
		emp.setGender("M");
		emp.setHiredate(new Date());
		emp.setSalary(1000);
		Set<Employee> emps = new HashSet<Employee>();
		emps.add(emp);
		emp.setDept(dept);

		dept.setEmployees(emps);

		boolean b = false;
		try {
			b = deptService.save(dept);
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
			Department dept = deptService.findById(Department.class, 150);
			dept.setDeptName("Test");
			b = deptService.save(dept);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		Assert.isTrue(b);
	}

	@Test
	@Rollback(false)
	public void testDelete() {
		try {
			deptService.delete(200);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Assert.isTrue(true);
	}

}
