package com.pactera.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pactera.web.common.Constant;
import com.pactera.web.common.DateUtil;
import com.pactera.web.common.Pagination;
import com.pactera.web.exception.ServiceException;
import com.pactera.web.model.Department;
import com.pactera.web.model.Employee;
import com.pactera.web.service.DepartmentService;
import com.pactera.web.service.EmployeeService;
import com.pactera.web.validation.EmployeeValidator;

@Controller
@RequestMapping(value = "emp")
public class EmployeeController extends BaseController {

	Logger log = Logger.getLogger(EmployeeController.class);

	private final String paginationURL = "emp/list";

	@Autowired
	EmployeeService empService;

	@Autowired
	DepartmentService deptService;

	@Autowired
	EmployeeValidator validator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		DateFormat fmt = new SimpleDateFormat(Constant.DATE_FORMAT);
		CustomDateEditor dateEditor = new CustomDateEditor(fmt, true);
		binder.registerCustomEditor(Date.class, dateEditor);
		// super.initBinder(request, binder);
	}

	@RequestMapping(value = "list")
	public ModelAndView list(String pageNo) throws ServiceException {
		final String METHOD_NAME = "list";
		log.debug(METHOD_NAME + " begin");

		ModelAndView mav = new ModelAndView("empList");

		Pagination pagination = new Pagination();
		pagination.setPageNo(StringUtils.isNumeric(pageNo) ? new Integer(pageNo) : new Integer(1));

		List<Employee> empList = empService.findAll(pagination);

		if (!CollectionUtils.isEmpty(empList)) {
			for (Employee emp : empList) {
				emp.setHireDateStr(DateUtil.format(emp.getHiredate(), Constant.DATE_FORMAT));
			}
			mav.addObject("empList", empList);
			mav.addObject("pagination", pagination);
			mav.addObject("paginationURL", paginationURL);
		}

		log.debug(METHOD_NAME + " end");
		return mav;
	}

	@RequestMapping(value = "showCreate")
	public ModelAndView showCreate(HttpServletRequest request) throws ServiceException {
		final String METHOD_NAME = "showCreate";
		log.debug(METHOD_NAME + " begin");

		ModelAndView mav = new ModelAndView("empNew");

		Employee object = new Employee();
		mav.addObject("employee", object);

		List<Department> deptList = deptService.findAll();
		mav.addObject("deptList", deptList);

		log.debug(METHOD_NAME + " end");
		return mav;
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public ModelAndView create(@Valid @ModelAttribute Employee emp, BindingResult result, HttpServletRequest request)
			throws ServiceException {
		final String METHOD_NAME = "create";
		log.debug(METHOD_NAME + " begin");

		ModelAndView mav = null;
		if (result.hasErrors()) {
			mav = new ModelAndView("empNew");
			mav.addObject("employee", emp);

			List<Department> deptList = deptService.findAll();
			mav.addObject("deptList", deptList);

			log.debug(METHOD_NAME + " end caused by input error");

			return mav;
		}

		try {
			emp.setHiredate(DateUtil.parse(emp.getHireDateStr(), Constant.DATE_FORMAT));
		} catch (ParseException e) {
			log.info("input hire date format error", e);
			throw new ServiceException(e.getMessage());
		}
		empService.save(emp);

		mav = new ModelAndView("redirect:list");

		log.debug(METHOD_NAME + " end");
		return mav;
	}

	@RequestMapping(value = "showEdit")
	public ModelAndView showEdit(@RequestParam Integer empno) throws ServiceException {
		final String METHOD_NAME = "showEdit";
		log.debug(METHOD_NAME + " begin");

		ModelAndView mav = new ModelAndView("empEdit");

		Employee emp = empService.findById(Employee.class, empno);
		emp.setHireDateStr(DateUtil.format(emp.getHiredate(), Constant.DATE_FORMAT));
		mav.addObject("employee", emp);

		List<Department> deptList = deptService.findAll();
		mav.addObject("deptList", deptList);

		log.debug(METHOD_NAME + " end");
		return mav;
	}

	@RequestMapping(value = "edit")
	public ModelAndView edit(@Valid @ModelAttribute Employee emp, BindingResult result, HttpServletRequest request)
			throws ServiceException {
		final String METHOD_NAME = "edit";
		log.debug(METHOD_NAME + " begin");

		ModelAndView mav = null;

		if (result.hasErrors()) {
			mav = new ModelAndView("empEdit");
			mav.addObject("employee", emp);

			List<Department> deptList = deptService.findAll();
			mav.addObject("deptList", deptList);

			log.debug(METHOD_NAME + " end caused by input error");

			return mav;
		}

		try {
			emp.setHiredate(DateUtil.parse(emp.getHireDateStr(), Constant.DATE_FORMAT));
		} catch (ParseException e) {
			log.info("input hire date format error", e);
			throw new ServiceException(e.getMessage());
		}
		empService.update(emp);

		mav = new ModelAndView("redirect:list");

		log.debug(METHOD_NAME + " end");
		return mav;
	}

	@RequestMapping(value = "delete")
	public ModelAndView delete(@RequestParam Integer empno) throws ServiceException {
		final String METHOD_NAME = "delete";
		log.debug(METHOD_NAME + " begin");

		ModelAndView mav = null;

		empService.delete(Employee.class, empno);

		mav = new ModelAndView("redirect:list");

		log.debug(METHOD_NAME + " end");
		return mav;
	}
}
