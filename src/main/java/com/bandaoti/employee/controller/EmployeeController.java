package com.bandaoti.employee.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.bandaoti.employee.BandaotiConstant;
import com.bandaoti.employee.BaseController;
import com.bandaoti.employee.ControllerException;
import com.bandaoti.employee.ControllerResult;
import com.bandaoti.employee.MD5Util;
import com.bandaoti.employee.ReturnCode;
import com.bandaoti.employee.annotations.EmpAuthority;
import com.bandaoti.employee.entity.Employee;
import com.bandaoti.employee.service.EmployeeService;
import com.github.pagehelper.util.StringUtil;

@RestController
@RequestMapping("employee")
public class EmployeeController extends BaseController {
	@Autowired
	private EmployeeService empService;
	@Autowired
	private StringRedisTemplate sRedis;
	@EmpAuthority({})
	@PostMapping("getEmployee")
	public ControllerResult getEmployee() throws ControllerException {
		Map<String,Object> result=new HashMap<>();
		result.put("user", getUser());
		result.put("roles", getRoles());
		return success(result);
	}
	
	@PostMapping("register")
	public ControllerResult register(HttpServletResponse response) throws ControllerException{
		String username=getParamNotNull("name");
		String password=getParam("password");
		String name=getParamNotNull("username");//真实姓名
		String type=getParamNotNull("type");
		Employee emp=new Employee();
		if("mobile".equalsIgnoreCase(type.trim())){
			emp.setMobile(username);
			if(!("1234".equals(getParamNotNull("smsCode")))){
				return faile(ReturnCode.SMS_CODE_ERROR);
			}
		}else if("email".equalsIgnoreCase(type.trim())){
			emp.setEmail(username);
		}else if("idcard".equalsIgnoreCase(type.trim())){
			emp.setIdcard(username);
		}else if("code".equalsIgnoreCase(type.trim())){
			emp.setCode(username);
		}else{
			return faile();
		}
		emp.setName(name);
		emp.setPassword(password);
		empService.addEmployee(emp);
		getSession().setAttribute(BandaotiConstant.LOGIN_REMEMBER_ME, emp);
		emp.setPassword(null);
		return success(emp);
	}
	@PostMapping("login")
	public ControllerResult login(HttpServletResponse response) throws ControllerException {
		String username = getParamNotNull("name");
		String password = getParamNotNull("password");
		String rememberMe = getParam("rememberMe");
		Employee emp = empService.getEmployee(username);
		if (emp == null) {
			throw new ControllerException(ReturnCode.ACCOUNT_PASSWORD_ERROR);
		} else if (!emp.getPassword().equals(password)) {
			throw new ControllerException(ReturnCode.ACCOUNT_PASSWORD_ERROR);
		}
		emp.setPassword(null);
		emp.setStatus(null);
		if ("true".equalsIgnoreCase(rememberMe)) {
			String redisLoginCookieValue = MD5Util.string2MD5("" + emp.getId() + emp.getCreateTime().getTime()) + new Random().nextInt(1000);
			Cookie cookie = new Cookie(BandaotiConstant.LOGIN_REMEMBER_ME, redisLoginCookieValue);
			sRedis.opsForValue().set(redisLoginCookieValue, JSON.toJSONString(emp));
			sRedis.boundValueOps(redisLoginCookieValue).expire(7, TimeUnit.DAYS);// 记住两周
			cookie.setMaxAge(604800);// 秒：60*60*24*7//记住7天
			cookie.setPath("/");
			response.addCookie(cookie);
			getSession().setAttribute(BandaotiConstant.LOGIN_REMEMBER_ME, emp);
			// 记住我
		} else {
			getSession().setAttribute(BandaotiConstant.LOGIN_REMEMBER_ME, emp);
		}
		Map<String,Object> result=new HashMap<>();
		result.put("user", emp);
		result.put("roles",getRoles());
		return success(result);
	}
	@GetMapping("logout")
	public ControllerResult logout(){
		getSession().setAttribute(BandaotiConstant.LOGIN_REMEMBER_ME, null);
		String key=getCookie(BandaotiConstant.LOGIN_REMEMBER_ME);
		if(!StringUtil.isEmpty(key)&&sRedis.hasKey(key)){
			sRedis.delete(key);
		}
		return success();
	}
}
