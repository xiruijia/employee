package com.bandaoti.employee.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bandaoti.employee.ControllerException;
import com.bandaoti.employee.ControllerResult;
/**
 * 异常拦截
 * @author XiRuiQiang
 *
 */
@ControllerAdvice
public class ExceptionHandlers {
	@ExceptionHandler(ControllerException.class)
	@ResponseBody
	public ControllerResult exceptionHandel(ControllerException ex, HttpServletRequest req) {
		return ex.getResult();
	}
}
