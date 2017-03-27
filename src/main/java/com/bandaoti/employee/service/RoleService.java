package com.bandaoti.employee.service;

import java.util.List;

import com.bandaoti.employee.ControllerException;
import com.bandaoti.employee.entity.Employee;
import com.bandaoti.employee.entity.Role;
import com.bandaoti.employee.vo.RoleVO;
import com.github.pagehelper.PageInfo;

public interface RoleService {
	List<RoleVO> getRoleByEmpId(Integer id);
	PageInfo<Role> getRoles(String roleName, Integer pageNum,Integer pageSize);
	PageInfo<Employee> getEmployeeByRole(String roleCode,Integer pageNum) throws ControllerException;
	List<Employee> getEmployeeByRoleId(Integer id);
	void addRole(Role role) throws ControllerException;
	void delRoleById(Integer roleId);
	void disableRoleById(Integer roleId, Integer i) throws ControllerException;
	void reqRole(Integer empId,Integer reqEmpId, Integer roleId) throws ControllerException;
	void delRoleByFormEmp(Integer roleId);
}
