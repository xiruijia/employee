package com.bandaoti.employee.service;

import java.util.List;

import com.bandaoti.employee.entity.Employee;
import com.bandaoti.employee.entity.Role;

public interface RoleService {
	List<Role> getRoleByEmpId(Integer id);
	List<Employee> getEmployeeByRoleId(Integer id);
}
