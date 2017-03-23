package com.bandaoti.employee.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandaoti.employee.ControllerException;
import com.bandaoti.employee.ReturnCode;
import com.bandaoti.employee.dao.EmpRoleMapper;
import com.bandaoti.employee.dao.EmployeeMapper;
import com.bandaoti.employee.dao.RoleMapper;
import com.bandaoti.employee.entity.EmpRoleExample;
import com.bandaoti.employee.entity.Employee;
import com.bandaoti.employee.entity.EmployeeExample;
import com.bandaoti.employee.entity.Role;
import com.bandaoti.employee.entity.RoleExample;
import com.bandaoti.employee.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	public final List<Integer> roleStatus=Arrays.asList(1);
	@Autowired
	private EmpRoleMapper empRoleMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private EmployeeMapper empMapper;

	@Override
	public void addRole(Role role) throws ControllerException {
		RoleExample example=new RoleExample();
		example.createCriteria().andStatusEqualTo(1).andCodeEqualTo(role.getCode());
		if(roleMapper.selectByExample(example).size()>0){
			throw new ControllerException(ReturnCode.ROLE_EXIST_ERROR);
		}
		roleMapper.insertSelective(role);
	}
	@Override
	public void delRoleById(Integer roleId) {
		roleMapper.deleteByPrimaryKey(roleId);
	}
	@Override
	public void disableRoleById(Integer roleId, Integer status) throws ControllerException {
		Role record=new Role();
		record.setId(roleId);
		record.setStatus(status);
		if(roleMapper.updateByPrimaryKeySelective(record)==0){
			throw new ControllerException(ReturnCode.ROLE_NOT_EXIST_ERROR);
		}
	}
	@Override
	public List<Role> getRoleByEmpId(Integer id) {
		EmpRoleExample erExample = new EmpRoleExample();
		erExample.createCriteria().andEmpIdEqualTo(id);
		List<Integer> roleids = new ArrayList<>();
		empRoleMapper.selectByExample(erExample).forEach(a -> {
			roleids.add(a.getRoleId());
		});
		if(roleids.isEmpty())return new ArrayList<>();
		RoleExample roleExample = new RoleExample();
		roleExample.createCriteria().andStatusIn(roleStatus).andIdIn(roleids);
		return roleMapper.selectByExample(roleExample);
	}

	@Override
	public List<Employee> getEmployeeByRoleId(Integer id) {
		EmpRoleExample erExample = new EmpRoleExample();
		erExample.createCriteria().andRoleIdEqualTo(id);
		List<Integer> empids = new ArrayList<>();
		empRoleMapper.selectByExample(erExample).forEach(a -> {
			empids.add(a.getEmpId());
		});
		if(empids.isEmpty())return null;
		EmployeeExample example=new EmployeeExample();
		example.createCriteria().andIdIn(empids);
		return empMapper.selectByExample(example);
	}
}
