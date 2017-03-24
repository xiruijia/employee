package com.bandaoti.employee.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bandaoti.employee.BaseController;
import com.bandaoti.employee.ControllerException;
import com.bandaoti.employee.ReturnCode;
import com.bandaoti.employee.annotations.EmpAuthority;
import com.bandaoti.employee.vo.RoleVO;

public class UserInterceptor extends BaseController implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		setRequest(request);
		if(handler instanceof org.springframework.web.method.HandlerMethod){
			org.springframework.web.method.HandlerMethod method=(org.springframework.web.method.HandlerMethod)handler;
			EmpAuthority ea=method.getMethodAnnotation(EmpAuthority.class);
			if(ea!=null){
				//有标记，就标识需要有权限
				List<String> roles=new ArrayList<>();
				List<RoleVO> roless=getUser().getRoles();
				if(roless!=null){
					roless.forEach(a->roles.add(a.getCode()));
				}
				boolean tag=ea.value().length==0;
				for(String role:ea.value()){
					if(roles.contains(role)){
						return true;
					}
				}
				for(String role:ea.reject()){
					if(roles.contains(role)){
						throw new ControllerException(ReturnCode.EMP_ROLE_NOT_FOUND);
					}
				}
				if(!tag){
					throw new ControllerException(ReturnCode.EMP_ROLE_NOT_FOUND);
				}
				return tag;
			}else{
				return true;
			}
		}else{
			return true;
		}
	}

//	System.out.println(">>>MyInterceptor2>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

//	System.out.println(">>>MyInterceptor2>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}

}