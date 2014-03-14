package com.tx.framework.web.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.tx.framework.web.common.enums.ServiceCode;
import com.tx.framework.web.manage.errlog.ServiceExceptionLogger;
import com.tx.framework.web.webservice.errlog.RestExceptionLogger;



/**
 * 全局异常处理
 * 
 * @author tangx
 * 
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	Object handleException(WebRequest request, Exception ex) {
		// ajax异常处理
		if (request.getHeader("x-requested-with") != null
				&& "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// rest异常处理
		if (ex instanceof RestException) {
			return handleRestException((RestException)ex, request);
		}
		// 业务异常处理
		if (ex instanceof ServiceException) {
			return handleServiceException((ServiceException) ex);
		}
		// 默认处理
		return handleDefaultException(ex);
	}

	
	private ModelAndView handleServiceException(ServiceException ex) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error/500");
		// modelAndView.addObject("name", ex.getClass().getSimpleName());
		// modelAndView.addObject("user", userDao.readUserName());
		return modelAndView;
	}
	
	private ResponseEntity<?> handleRestException(RestException ex, WebRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Object> jsonException = new HashMap<String, Object>();
		jsonException.put("code", ServiceCode.FAIL.getValue());
		jsonException.put("msg", ex.getMessage());
		return new ResponseEntity<Object>(jsonException, headers, ex.status);
	}
	
	private ModelAndView handleDefaultException(Exception ex) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error/500");
		return modelAndView;
	}

}
