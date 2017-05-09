package com.bandaoti.employee.vo;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.bandaoti.employee.entity.Employee;

public class EmployeeVO extends Employee {
	private List<RoleVO> roles;

	public List<RoleVO> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleVO> roles) {
		this.roles = roles;
	}

	public EmployeeVO setEmployee(Employee emp) {
		if (emp != null)
			BeanUtils.copyProperties(emp, this);
		return this;
	}

	@Override
	public String getPassword() {
		return null;
	}

}
