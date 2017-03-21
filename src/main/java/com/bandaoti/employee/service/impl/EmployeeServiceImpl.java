package com.bandaoti.employee.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandaoti.employee.ListUtil;
import com.bandaoti.employee.dao.EmployeeMapper;
import com.bandaoti.employee.entity.Employee;
import com.bandaoti.employee.entity.EmployeeExample;
import com.bandaoti.employee.service.EmployeeService;
import com.github.pagehelper.util.StringUtil;
@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired private EmployeeMapper empMapper;
	public final List<Integer> empStatus=ListUtil.asList(1,2);
	
	@Override
	public Employee addEmployee(Employee emp) {
		empMapper.insertSelective(emp);
		return emp;
	}

	@Override
	public Boolean delEmployeeByMobile(String mobile) {
		EmployeeExample employeeExample=new EmployeeExample();
		employeeExample.createCriteria().andMobileEqualTo(mobile);
		if(empMapper.deleteByExample(employeeExample)==1){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Boolean delEmployeeByIdcard(String idCard) {
		EmployeeExample employeeExample=new EmployeeExample();
		employeeExample.createCriteria().andIdcardEqualTo(idCard);
		if(empMapper.deleteByExample(employeeExample)==1){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Boolean delEmployeeByEmail(String email) {
		EmployeeExample employeeExample=new EmployeeExample();
		employeeExample.createCriteria().andEmailEqualTo(email);
		if(empMapper.deleteByExample(employeeExample)==1){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Boolean updateEmployee(Employee emp) {
		EmployeeExample example=new EmployeeExample();
		EmployeeExample.Criteria criteria=example.createCriteria();
		if(!StringUtil.isEmpty(emp.getMobile())){
			criteria.andMobileEqualTo(emp.getMobile());
		}else if(!StringUtil.isEmpty(emp.getEmail())){
			criteria.andEmailEqualTo(emp.getEmail());
		}else if(!StringUtil.isEmpty(emp.getIdcard())){
			criteria.andIdcardEqualTo(emp.getIdcard());
		}else {
			return false;
		}
		empMapper.updateByExampleSelective(emp, example);
		return true;
	}

	@Override
	public Employee getEmployeeByMobile(String mobile) {
		EmployeeExample employeeExample=new EmployeeExample();
		employeeExample.createCriteria().andStatusIn(empStatus).andMobileEqualTo(mobile);
		List<Employee> list=empMapper.selectByExample(employeeExample);
		if(list.size()==1)return list.get(0);
		return null;
	}

	@Override
	public Employee getEmployeeByIdcard(String idCard) {
		EmployeeExample employeeExample=new EmployeeExample();
		employeeExample.createCriteria().andStatusIn(empStatus).andIdcardEqualTo(idCard);
		List<Employee> list=empMapper.selectByExample(employeeExample);
		if(list.size()==1)return list.get(0);
		return null;
	}

	@Override
	public Employee getEmployeeByEmail(String email) {
		EmployeeExample employeeExample=new EmployeeExample();
		employeeExample.createCriteria().andStatusIn(empStatus).andEmailEqualTo(email);
		List<Employee> list=empMapper.selectByExample(employeeExample);
		if(list.size()==1)return list.get(0);
		return null;
	}

	@Override
	public Employee getEmployeeByCode(String code) {
		EmployeeExample employeeExample=new EmployeeExample();
		employeeExample.createCriteria().andStatusIn(empStatus).andCodeEqualTo(code);
		List<Employee> list=empMapper.selectByExample(employeeExample);
		if(list.size()==1)return list.get(0);
		return null;
	}

}