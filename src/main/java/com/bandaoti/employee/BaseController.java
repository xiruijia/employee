package com.bandaoti.employee;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.bandaoti.employee.entity.Employee;
import com.github.pagehelper.util.StringUtil;

public class BaseController {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private StringRedisTemplate sRedis;

	public Map<String, String> getParams() {
		Map<String, String> params = new HashMap<>();
		request.getParameterMap().forEach((key, val) -> {
			String value = null;
			for (String v : val)
				if (value == null)
					value = v;
				else
					value += "," + v;
			params.put(key, value);
		});
		return params;
	}
	public String getParam(String key){
		return request.getParameter(key);
	}
	public String getParamNotNull(String key) throws ControllerException{
		String value=request.getParameter(key);
		if(!StringUtils.isEmpty(value))return value;
		throw new ControllerException(ReturnCode.FAILE,true,key+"不能为空");
	}
	public ControllerResult success(){
		return new ControllerResult(ReturnCode.SUCCESS);
	}
	public ControllerResult success(Object data){
		return new ControllerResult(data);
	}
	public ControllerResult faile(){
		return new ControllerResult(ReturnCode.FAILE);
	}
	public ControllerResult faile(ReturnCode rc){
		return new ControllerResult(rc);
	}
	public ControllerResult faile(Object data){
		return new ControllerResult(ReturnCode.FAILE,data);
	}
	public HttpSession getSession(){
		return request.getSession();
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public Employee getUser() throws ControllerException{
		Employee user=(Employee) getSession().getAttribute(BandaotiConstant.LOGIN_REMEMBER_ME);
		if(user==null){
			String redisKey=getCookie(BandaotiConstant.LOGIN_REMEMBER_ME);
			if(!StringUtil.isEmpty(redisKey)){
				String userJson=sRedis.opsForValue().get(redisKey);
				if(!StringUtil.isEmpty(userJson)){
					user= JSON.parseObject(userJson, Employee.class);
					getSession().setAttribute(BandaotiConstant.LOGIN_REMEMBER_ME, user);
					return user;
				}
			}
			
		}else{
			return user;
		}
		throw new ControllerException(ReturnCode.USER_NOT_LOGIN);
	}
	public String getCookie(String name){
		Cookie[] cookies=request.getCookies();
		if(cookies!=null){
			for(Cookie c:cookies){
				if(c.getName().equals(name)){
					return c.getValue();
				}
			}
		}
		return null;
	}
	
}
