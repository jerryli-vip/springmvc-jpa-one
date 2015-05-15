package com.pactera.web.validation;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.pactera.web.common.Constant;
import com.pactera.web.common.DateUtil;
import com.pactera.web.common.Pagination;
import com.pactera.web.model.Employee;

@Component
public class EmployeeValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Employee.class.isAssignableFrom(clazz) || Pagination.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Employee emp = (Employee) target;
		String hireDateStr = emp.getHireDateStr();
		Integer salary = emp.getSalary();

		ValidationUtils.rejectIfEmpty(errors, "empName", "emp.name.empty");
		ValidationUtils.rejectIfEmpty(errors, "hireDateStr", "hire.date.empty");
		ValidationUtils.rejectIfEmpty(errors, "salary", "salary.empty");

		Integer deptno = emp.getDept().getDeptno();
		if (deptno == null) {
			errors.rejectValue("dept.deptno", "dept.deptno.empty");
		}

		Pattern datePattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
		if (!StringUtils.isEmpty(hireDateStr)) {
			if (datePattern.matcher(hireDateStr).find()) {
				try {
					Date hireDate = DateUtil.parse(hireDateStr, Constant.DATE_FORMAT);
					if (hireDate != null && hireDate.after(new Date())) {
						errors.rejectValue("hireDateStr", "hireDate.can.not.furture.day");
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else {
				errors.rejectValue("hireDateStr", "hire.date.format.error");
			}
		}

		if (salary != null) {
			final String sal = String.valueOf(salary);
			Pattern numPattern = Pattern.compile("^([1-9][0-9]*)$");
			if (!StringUtils.isEmpty(sal) && !numPattern.matcher(sal).find()) {
				errors.rejectValue("salary", "salary.should.be.number");
			}
		}
	}

}
