package com.bandaoti.employee.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bandaoti.employee.ControllerException;
import com.bandaoti.employee.ReturnCode;
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
import com.bandaoti.employee.service.RoleService;
import com.bandaoti.employee.vo.RoleVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class RoleServiceImpl implements RoleService {
	public static final List<Integer> roleStatus=Arrays.asList(1);
	@Autowired
	private EmpRoleMapper empRoleMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private EmployeeMapper empMapper;
	@Autowired
	private RequestRoleMapper reqRoleMapper;

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
	public List<RoleVO> getRoleByEmpId(Integer id) {
		EmpRoleExample erExample = new EmpRoleExample();
		erExample.createCriteria().andEmpIdEqualTo(id);
		List<Integer> roleids = new ArrayList<>();
		Map<Integer,Integer> roleStatusMap=new HashMap<>();
		empRoleMapper.selectByExample(erExample).forEach(a -> {
			roleids.add(a.getRoleId());
			roleStatusMap.put(a.getRoleId(), a.getStatus());
		});
		if(roleids.isEmpty())return new ArrayList<>();
		RoleExample roleExample = new RoleExample();
		roleExample.createCriteria().andStatusIn(roleStatus).andIdIn(roleids);
		List<Role> roles= roleMapper.selectByExample(roleExample);
		List<RoleVO> roleVos=new ArrayList<>();
		for(Role role:roles){
			RoleVO roleVo=new RoleVO(role);
			roleVo.setMyStatus(roleStatusMap.get(role.getId()));
			roleVos.add(roleVo);
		}
		return roleVos;
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
	@Override
	public PageInfo<Role> getRoles(String roleName, Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		RoleExample example=new RoleExample();
		RoleExample.Criteria criteria=example.createCriteria().andStatusIn(roleStatus);
		if(roleName!=null){
			criteria.andNameLike(roleName+"%");
		}
		return new PageInfo<Role>(roleMapper.selectByExample(example));
	}
	@Override
	public PageInfo<Employee> getEmployeeByRole(String roleCode,Integer pageNum) throws ControllerException {
		RoleExample roleExample=new RoleExample();
		RoleExample.Criteria criteria=roleExample.createCriteria().andStatusIn(roleStatus);
		if(roleCode!=null){
			criteria.andCodeEqualTo(roleCode);
		}
		List<Role> roles=roleMapper.selectByExample(roleExample);
		List<Employee> emps=null;
		if(!roles.isEmpty()){
			List<Integer> roleids=new ArrayList<>();
			roles.forEach(a->roleids.add(a.getId()));
			EmpRoleExample empRoleExample=new EmpRoleExample();
			empRoleExample.createCriteria().andStatusEqualTo(1).andRoleIdIn(roleids);
			List<Integer> empids=new ArrayList<>();
			List<EmpRole> empRoles=empRoleMapper.selectByExample(empRoleExample);
			if(!empRoles.isEmpty()){
				empRoles.forEach(a->empids.add(a.getEmpId()));
				EmployeeExample employeeExample=new EmployeeExample();
				employeeExample.createCriteria().andStatusIn(EmployeeServiceImpl.empStatus).andIdIn(empids);
				PageHelper.startPage(pageNum,10);
				emps=empMapper.selectByExample(employeeExample);
				for(Employee emp:emps){
					emp.setPassword(null);
					emp.setIdcard(null);
				}
			}else{
				throw new ControllerException(ReturnCode.ROLE_NOT_EXIST_ERROR);
			}
		}else{
			throw new ControllerException(ReturnCode.ROLE_NOT_EXIST_ERROR);
		}
		return new PageInfo<Employee>(emps);
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void reqRole(Integer empId,Integer reqEmpId, Integer roleId) throws ControllerException {
		//查询是否正在申请
		RequestRoleExample example=new RequestRoleExample();
		example.createCriteria().andEmpIdEqualTo(empId).andRoleIdEqualTo(roleId).andStatusEqualTo(1);
		List<RequestRole> reqRoles=reqRoleMapper.selectByExample(example);
		if(reqRoles.isEmpty()){
			//添加申请记录
			RequestRole reqRole=new RequestRole();
			reqRole.setEmpId(empId);
			reqRole.setReqEmpId(reqEmpId);
			reqRole.setRoleId(roleId);
			reqRoleMapper.insertSelective(reqRole);
		}else{
			throw new ControllerException(ReturnCode.ROLE_REQUESTING_ERROR);
		}
		//查询用户是否有角色
		EmpRoleExample empRoleExample=new EmpRoleExample();
		empRoleExample.createCriteria().andEmpIdEqualTo(empId).andRoleIdEqualTo(roleId);
		List<EmpRole> empRoles=empRoleMapper.selectByExample(empRoleExample);
		if(empRoles.isEmpty()){
			//新增角色
			EmpRole empRole=new EmpRole();
			empRole.setEmpId(empId);
			empRole.setRoleId(roleId);
			empRole.setStatus(2);
			empRoleMapper.insertSelective(empRole);
		}else{
			//删除多余角色状态
			if(empRoles.size()>1){
				for(int i=1;i<empRoles.size();i++){
					empRoleMapper.deleteByPrimaryKey(empRoles.get(i).getId());
				}
			}
			//修改角色状态
			EmpRole empRole=empRoles.get(0);
			empRole.setStatus(2);
			empRoleMapper.updateByPrimaryKey(empRole);
		}
		
	}
	@Override
	public void delRoleByFormEmp(Integer roleId) {
		EmpRoleExample erExample=new EmpRoleExample();
		erExample.createCriteria().andRoleIdEqualTo(roleId).andStatusEqualTo(1);
		empRoleMapper.deleteByExample(erExample);
	}
}
