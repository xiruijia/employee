package com.bandaoti.employee.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.bandaoti.employee.BandaotiConstant;
import com.bandaoti.employee.BaseController;
import com.bandaoti.employee.ControllerException;
import com.bandaoti.employee.ControllerResult;
import com.bandaoti.employee.MD5Util;
import com.bandaoti.employee.ReturnCode;
import com.bandaoti.employee.annotations.EmpAuthority;
import com.bandaoti.employee.entity.Employee;
import com.bandaoti.employee.service.EmployeeService;
import com.bandaoti.employee.service.RoleService;
import com.bandaoti.employee.vo.EmployeeVO;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("employee")
public class EmployeeController extends BaseController {
	@Autowired
	private EmployeeService empService;
	@Autowired
	private RoleService roleService;
	/**
	 * 查询用户
	 * @return
	 */
	@EmpAuthority
	@GetMapping("findEmployee")
	public ControllerResult findEmployee(Integer pageNum){
		PageInfo<Employee> emps=empService.getEmployee(pageNum,5);
		return success(emps);
	}
	@EmpAuthority
	@GetMapping("delEmployee")
	public ControllerResult delEmployee(Employee emp){
		empService.delEmployeeById(emp.getId());
		return success();
	}
	@EmpAuthority
	@GetMapping(value="getEmployee")
	public ControllerResult getEmployee() throws ControllerException {
		EmployeeVO empVo=getUser();
		empVo.setRoles(roleService.getRoleByEmpId(empVo.getId()));
		getSession().setAttribute(BandaotiConstant.LOGIN_REMEMBER_ME, empVo);
		return success(empVo);
	}
	@EmpAuthority
	@GetMapping("getEmployeeByRole")
	public ControllerResult getEmployeeByRole(Integer pageNum) throws ControllerException{
		if(pageNum==null)pageNum=1;
		String roleCode=getParamNotNull("roleCode");
		return success(roleService.getEmployeeByRole(roleCode,pageNum));
	}
	@EmpAuthority
	@GetMapping("updateEmployee")
	public ControllerResult updateEmployee() throws ControllerException{
		String gender=getParam("gender");
		String email=getParam("email");
		String mobile=getParam("mobile");
		String idcard=getParam("idcard");
		EmployeeVO ev=getUser();
		Employee emp=new Employee();
		emp.setId(ev.getId());
		if(!StringUtils.isEmpty(gender)){
			if(ev.getGender()==null||ev.getGender()==0){
				emp.setGender(Integer.parseInt(gender));
			}else{
				throw new ControllerException(ReturnCode.ACCOUNT_GENDER_EXIST);
			}
		}
		if(!StringUtils.isEmpty(email)){
			if(ev.getEmail()==null){
				emp.setEmail(email);
			}else{
				throw new ControllerException(ReturnCode.ACCOUNT_EMAIL_EXIST);
			}
		}
		if(!StringUtils.isEmpty(mobile)){
			if(ev.getMobile()==null){
				emp.setMobile(mobile);
			}else{
				throw new ControllerException(ReturnCode.ACCOUNT_MOBILE_EXIST);
			}
		}
		if(!StringUtils.isEmpty(idcard)){
			if(ev.getIdcard()==null){
				emp.setIdcard(idcard);
			}else{
				throw new ControllerException(ReturnCode.ACCOUNT_IDCARD_EXIST);
			}
		}
		empService.updateEmployee(emp);
		EmployeeVO empVo=new EmployeeVO();
		emp = empService.getEmployee(emp.getId());
		empVo.setEmployee(emp);
		getSession().setAttribute(BandaotiConstant.LOGIN_REMEMBER_ME, empVo);
		return success();
	}
	
	@GetMapping("register")
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
		emp.setGender(0);
		empService.addEmployee(emp);
		EmployeeVO empVo=new EmployeeVO();
		empVo.setEmployee(emp);
		getSession().setAttribute(BandaotiConstant.LOGIN_REMEMBER_ME, empVo);
		return success(empVo);
	}
	@GetMapping("login")
	public ControllerResult login(HttpServletResponse response) throws ControllerException {
		String username = getParamNotNull("name");
		String password = getParamNotNull("password");
		String rememberMe = getParam("rememberMe");
		getSession().setAttribute(BandaotiConstant.LOGIN_REMEMBER_ME, null);
		getSession().setAttribute(BandaotiConstant.SESSION_USER_ROLES, null);
		getSession().setAttribute(BandaotiConstant.SESSION_USER_ROLES_STRING, null);
		EmployeeVO empVo=new EmployeeVO();
		Employee emp = empService.getEmployee(username);
		if (emp == null) {
			throw new ControllerException(ReturnCode.ACCOUNT_PASSWORD_ERROR);
		} else if (!emp.getPassword().equals(password)) {
			throw new ControllerException(ReturnCode.ACCOUNT_PASSWORD_ERROR);
		}
		empVo.setEmployee(emp);
		empVo.setRoles(roleService.getRoleByEmpId(emp.getId()));
		if ("true".equalsIgnoreCase(rememberMe)) {
			// 记住我
			String value=MD5Util.charEnCoding(username+"#|"+emp.getPassword());
			Cookie cookie=new Cookie(BandaotiConstant.LOGIN_REMEMBER_ME,value);
			cookie.setMaxAge(604800);// 秒：60*60*24*7//记住7天
			cookie.setPath("/");
			response.addCookie(cookie);
			getSession().setAttribute(BandaotiConstant.LOGIN_REMEMBER_ME, empVo);
		} else {
			getSession().setAttribute(BandaotiConstant.LOGIN_REMEMBER_ME, empVo);
		}
		return success(empVo);
	}
	@GetMapping("logout")
	public ControllerResult logout(){
		getSession().setAttribute(BandaotiConstant.LOGIN_REMEMBER_ME, null);
		getSession().setAttribute(BandaotiConstant.SESSION_USER_ROLES, null);
		getSession().setAttribute(BandaotiConstant.SESSION_USER_ROLES_STRING, null);
		return success();
	}
}
