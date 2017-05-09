package com.bandaoti.employee;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.bandaoti.employee.entity.Employee;
import com.bandaoti.employee.service.EmployeeService;
import com.bandaoti.employee.vo.EmployeeVO;

public class BaseController {
	@Autowired
	private HttpServletRequest request;

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

	public String getParam(String key) {
		return request.getParameter(key);
	}

	public String getParamNotNull(String key) throws ControllerException {
		String value = request.getParameter(key);
		if (!StringUtils.isEmpty(value))
			return value;
		throw new ControllerException(ReturnCode.FAILE, true, key + "不能为空");
	}

	public Integer getParamNotInteger(String key) throws ControllerException {
		try {
			return Integer.parseInt(getParamNotNull(key));
		} catch (NumberFormatException e) {
			throw new ControllerException(ReturnCode.NUMBER_FORMAT_ERROR);
		}
	}

	public ControllerResult success() {
		return new ControllerResult(ReturnCode.SUCCESS);
	}

	public ControllerResult success(Object data) {
		return new ControllerResult(data);
	}

	public ControllerResult faile() {
		return new ControllerResult(ReturnCode.FAILE);
	}

	public ControllerResult faile(ReturnCode rc) {
		return new ControllerResult(rc);
	}

	public ControllerResult faile(Object data) {
		return new ControllerResult(ReturnCode.FAILE, data);
	}

	public HttpSession getSession() {
		return request.getSession();
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public EmployeeVO getUserNotError() {
		try {
			return getUser();
		} catch (ControllerException e) {
		}
		return null;
	}

	public EmployeeVO getUser() throws ControllerException {
		EmployeeVO user = (EmployeeVO) getSession().getAttribute(BandaotiConstant.LOGIN_REMEMBER_ME);
		if(user==null){
			String value=null;
			if(request.getCookies()!=null){
				for(Cookie c:request.getCookies()){
					if(c.getName().equalsIgnoreCase(BandaotiConstant.LOGIN_REMEMBER_ME)){
						value=c.getValue();
					}
				}
			}
			if(value!=null){
				value=MD5Util.charDeCoding(value);
				String[] cs=value.split("#\\|");
				if(cs.length==2){
					String username=cs[0];
					String password=cs[1];
					Employee emp=Application.application.getBean(EmployeeService.class).getEmployee(username);
					if(password.equals(emp.getPassword())){
						EmployeeVO empv= new EmployeeVO().setEmployee(emp);
						HttpSession session=getSession();
						if(session!=null)
							session.setAttribute(BandaotiConstant.LOGIN_REMEMBER_ME, empv);
						return empv;
					}
				}
			}
		}else{
			return user;
		}
		throw new ControllerException(ReturnCode.USER_NOT_LOGIN);
	}

	public String getCookie(String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(name)) {
					return c.getValue();
				}
			}
		}
		return null;
	}
}
