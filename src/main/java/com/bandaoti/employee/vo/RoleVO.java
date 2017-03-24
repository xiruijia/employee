package com.bandaoti.employee.vo;

import org.springframework.beans.BeanUtils;

import com.bandaoti.employee.entity.Role;

public class RoleVO extends Role {
	
	public RoleVO(Role role) {
		BeanUtils.copyProperties(role, this);
	}

	private Integer myStatus;

	public Integer getMyStatus() {
		return myStatus;
	}

	public void setMyStatus(Integer myStatus) {
		this.myStatus = myStatus;
	}

}
