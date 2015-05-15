package com.pactera.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pactera.web.common.Pagination;
import com.pactera.web.common.Token;
import com.pactera.web.exception.ServiceException;
import com.pactera.web.model.Department;
import com.pactera.web.service.DepartmentService;
import com.pactera.web.validation.DepartmentValidator;

@Controller
@RequestMapping(value = "dept")
public class DepartmentController extends BaseController {

	Logger log = Logger.getLogger(DepartmentController.class);

	private final String paginationURL = "dept/list";

	@Autowired
	DepartmentService deptService;

	@Autowired
	DepartmentValidator validator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@RequestMapping(value = "list")
	public ModelAndView list(String pageNo) throws ServiceException {
		final String METHOD_NAME = "list";
		log.debug(METHOD_NAME + " begin");

		ModelAndView mav = new ModelAndView("deptList");

		Pagination pagination = new Pagination();
		pagination.setPageNo(StringUtils.isNumeric(pageNo) ? new Integer(pageNo) : new Integer(1));

		List<Department> deptList = deptService.findAll(pagination);
		if (!CollectionUtils.isEmpty(deptList)) {
			mav.addObject("deptList", deptList);
			mav.addObject("pagination", pagination);
			mav.addObject("paginationURL", paginationURL);
		}

		log.debug(METHOD_NAME + " end");
		return mav;
	}

	@Token(save = true)
	@RequestMapping(value = "showCreate")
	public ModelAndView showCreate(HttpServletRequest request) throws ServiceException {
		final String METHOD_NAME = "showCreate";
		log.debug(METHOD_NAME + " begin");

		ModelAndView mav = new ModelAndView("deptNew");

		Department dept = new Department();
		mav.addObject("department", dept);

		request.getSession(false).setAttribute("reSubmitWhenError", null);

		log.debug(METHOD_NAME + " end");
		return mav;
	}

	@Token(remove = true)
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public ModelAndView create(@Valid @ModelAttribute Department dept, BindingResult result, HttpServletRequest request)
			throws ServiceException {
		final String METHOD_NAME = "create";
		log.debug(METHOD_NAME + " begin");

		ModelAndView mav = null;
		if (result.hasErrors()) {
			mav = new ModelAndView("deptNew");
			mav.addObject("department", dept);

			request.getSession(false).setAttribute("reSubmitWhenError", Boolean.TRUE);
			log.debug(METHOD_NAME + " end caused by input error");

			return mav;
		}

		deptService.save(dept);

		mav = list("0");

		log.debug(METHOD_NAME + " end");
		return mav;
	}

	@RequestMapping(value = "showEdit")
	public ModelAndView showEdit(@RequestParam Integer deptno) throws ServiceException {
		final String METHOD_NAME = "showEdit";
		log.debug(METHOD_NAME + " begin");

		ModelAndView mav = new ModelAndView("deptEdit");

		Department dept = deptService.findById(Department.class, deptno);

		mav.addObject("department", dept);

		log.debug(METHOD_NAME + " end");
		return mav;
	}

	@RequestMapping(value = "edit")
	public ModelAndView edit(@Valid @ModelAttribute Department dept, BindingResult result, HttpServletRequest request)
			throws ServiceException {
		final String METHOD_NAME = "edit";
		log.debug(METHOD_NAME + " begin");

		ModelAndView mav = null;

		if (result.hasErrors()) {
			mav = new ModelAndView("deptEdit");
			mav.addObject("department", dept);

			log.debug(METHOD_NAME + " end caused by input error");

			return mav;
		}

		deptService.update(dept);

		mav = new ModelAndView("redirect:list");

		log.debug(METHOD_NAME + " end");
		return mav;
	}

	@RequestMapping(value = "delete")
	public ModelAndView delete(@RequestParam Integer deptno) throws ServiceException {
		final String METHOD_NAME = "delete";
		log.debug(METHOD_NAME + " begin");

		ModelAndView mav = null;

		deptService.delete(Department.class, deptno);

		mav = new ModelAndView("redirect:list");

		log.debug(METHOD_NAME + " end");
		return mav;
	}
}
