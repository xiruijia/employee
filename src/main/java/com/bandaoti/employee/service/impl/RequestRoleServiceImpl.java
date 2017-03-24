package com.bandaoti.employee.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bandaoti.employee.dao.EmpRoleMapper;
import com.bandaoti.employee.dao.EmployeeMapper;
import com.bandaoti.employee.dao.RequestRoleMapper;
import com.bandaoti.employee.dao.RoleMapper;
import com.bandaoti.employee.entity.EmpRole;
import com.bandaoti.employee.entity.EmpRoleExample;
import com.bandaoti.employee.entity.Employee;
import com.bandaoti.employee.entity.EmployeeExample;
import com.bandaoti.employee.entity.RequestRole;
import com.bandaoti.employee.entity.RequestRoleExample;
import com.bandaoti.employee.entity.Role;
import com.bandaoti.employee.entity.RoleExample;
import com.bandaoti.employee.service.RequestRoleService;
import com.bandaoti.employee.vo.RequestRoleVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class RequestRoleServiceImpl implements RequestRoleService {
	@Autowired
	private RequestRoleMapper requestRoleMapper;
	@Autowired
	private EmployeeMapper empMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private EmpRoleMapper empRoleMapper;
	@Override
	public PageInfo<RequestRoleVO> getRequestRoleByEmpId(Integer pageNum,Integer empId) {
		PageHelper.startPage(pageNum,5);
		RequestRoleExample rrExample=new RequestRoleExample();
		rrExample.createCriteria().andReqEmpIdEqualTo(empId).andStatusEqualTo(1);
		List<RequestRoleVO> list=new ArrayList<>();
		// TODO 这里遍历查询还需要优化
		for(RequestRole rr:requestRoleMapper.selectByExample(rrExample)){
			RequestRoleVO rrv=new RequestRoleVO(rr);
			EmployeeExample employeeExample=new EmployeeExample();
			employeeExample.createCriteria().andIdEqualTo(rr.getEmpId()).andStatusIn(EmployeeServiceImpl.empStatus);
			List<Employee> emps=empMapper.selectByExample(employeeExample);
			if(emps.isEmpty()){
				
			}else{
				rrv.setEmpName(emps.get(0).getName());
				list.add(rrv);
			}
			
			RoleExample example=new RoleExample();
			example.createCriteria().andStatusIn(RoleServiceImpl.roleStatus).andIdEqualTo(rr.getRoleId());
			List<Role> roles=roleMapper.selectByExample(example);
			if(!roles.isEmpty()){
				rrv.setRoleCode(roles.get(0).getCode());
				rrv.setRoleName(roles.get(0).getName());
			}
		}
		return new PageInfo<RequestRoleVO>(list);
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void agreeRole(Integer msgId, boolean agree) {
		RequestRole rr=requestRoleMapper.selectByPrimaryKey(msgId);
		if(agree){
			rr.setStatus(0);
		}else{
			rr.setStatus(2);
		}
		requestRoleMapper.updateByPrimaryKey(rr);
		EmpRoleExample example=new EmpRoleExample();
		example.createCriteria().andEmpIdEqualTo(rr.getEmpId()).andRoleIdEqualTo(rr.getRoleId()).andStatusEqualTo(2);
		List<EmpRole> ers=empRoleMapper.selectByExample(example);
		if(!ers.isEmpty()){
			for(EmpRole er:ers){
				er.setStatus(agree?1:3);
				empRoleMapper.updateByPrimaryKey(er);
			}
		}
	}

}
