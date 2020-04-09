package com.mtnz.controller.base;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtnz.entity.Page;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.mtnz.entity.Page;
import com.mtnz.util.Const;
import com.mtnz.util.Logger;
import com.mtnz.util.PageData;
import com.mtnz.util.Tools;
import com.mtnz.util.UuidUtil;

public class BaseController {

	protected Logger logger = Logger.getLogger(this.getClass());



	private static final long serialVersionUID = 6357869213649815390L;

	/**
	 * 得到PageData
	 */
	public PageData getPageData() {
		return new PageData(this.getRequest());
	}

	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView() {
		return new ModelAndView();
	}

	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		return request;
	}

	/**
	 * 得到32位的uuid
	 * @return
	 */
	public String get32UUID() {

		return UuidUtil.get32UUID();
	}

	/**
	 * 得到分页列表的信息
	 */
	public Page getPage() {

		return new Page();
	}

	public static void logBefore(Logger logger, String interfaceName) {
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}

	public static void logAfter(Logger logger) {
		logger.info("end");
		logger.info("");
	}

	/**
	 * @param code 错误代码
	 * @param message  错误信息
	 * @return
	 */
	public String getMessage(String code,String message){
		PageData pd = new PageData();
		pd.put("code",code);
		pd.put("message",message);
		ObjectMapper mapper=new ObjectMapper();
		String str="";
		try {
			str= mapper.writeValueAsString(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	Integer pageNumber = 1;

	Integer pageSize = 10;

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
