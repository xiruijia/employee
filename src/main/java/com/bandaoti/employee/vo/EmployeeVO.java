package com.bandaoti.employee.vo;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.bandaoti.employee.entity.Employee;
import com.bandaoti.employee.entity.Role;

public class EmployeeVO extends Employee {
	private List<Role> roles;

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public void setEmployee(Employee emp){
		if(emp!=null)
		BeanUtils.copyProperties(emp, this);
	}
	
	@Override
	public String getPassword() {
		return null;
	}
	
}
