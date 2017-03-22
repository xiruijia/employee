package com.bandaoti.employee.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandaoti.employee.ListUtil;
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
	public final List<Integer> roleStatus=ListUtil.asList(1);
	@Autowired
	private EmpRoleMapper empRoleMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private EmployeeMapper empMapper;

	@Override
	public List<Role> getRoleByEmpId(Integer id) {
		EmpRoleExample erExample = new EmpRoleExample();
		erExample.createCriteria().andEmpIdEqualTo(id);
		List<Integer> roleids = new ArrayList<>();
		empRoleMapper.selectByExample(erExample).forEach(a -> {
			roleids.add(a.getRoleId());
		});
		if(roleids.isEmpty())return null;
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
