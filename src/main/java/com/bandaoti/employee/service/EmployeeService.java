package com.bandaoti.employee.service;

import com.bandaoti.employee.EmployeeType;
import com.bandaoti.employee.entity.Employee;

public interface EmployeeService {
	Employee addEmployee(Employee emp);
	
	Boolean delEmployeeByMobile(String mobile);
	Boolean delEmployeeByIdcard(String idCard);
	Boolean delEmployeeByEmail(String email);
	default EmployeeType delEmployee(String mobileOrIdcardOrEmail){
		if(delEmployeeByMobile(mobileOrIdcardOrEmail)){
			return EmployeeType.MOBILE;
		}else if(delEmployeeByIdcard(mobileOrIdcardOrEmail)){
			return EmployeeType.IDCARD;
		}else if(delEmployeeByEmail(mobileOrIdcardOrEmail)){
			return EmployeeType.EMAIL;
		}
		return null;
	}
	
	Boolean updateEmployee(Employee emp);
	
	Employee getEmployeeByMobile(String mobile);
	Employee getEmployeeByIdcard(String idCard);
	Employee getEmployeeByEmail(String email);
	Employee getEmployeeByCode(String code);
	default Employee getEmployee(String mobileOrIdcardOrEmail){
		Employee emp=getEmployeeByIdcard(mobileOrIdcardOrEmail);
		if(emp==null){
			emp=getEmployeeByEmail(mobileOrIdcardOrEmail);
		}
		if(emp==null){
			emp=getEmployeeByMobile(mobileOrIdcardOrEmail);
		}
		if(emp==null){
			emp=getEmployeeByCode(mobileOrIdcardOrEmail);
		}
		return emp;
	}
}