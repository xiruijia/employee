package com.bandaoti.employee.vo;

import org.springframework.beans.BeanUtils;

import com.bandaoti.employee.entity.RequestRole;

public class RequestRoleVO extends RequestRole {
	
	
	public RequestRoleVO() {
		super();
	}

	public RequestRoleVO(RequestRole rr) {
		setRequestRole(rr);
	}

	private String empName;
	private String roleCode;
	private String roleName;
	

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public void setRequestRole(RequestRole rr){
		BeanUtils.copyProperties(rr, this);
	}
	
}
