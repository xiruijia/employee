package com.bandaoti.employee.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bandaoti.employee.BaseController;
import com.bandaoti.employee.ControllerException;
import com.bandaoti.employee.ControllerResult;
import com.bandaoti.employee.annotations.EmpAuthority;
import com.bandaoti.employee.entity.Role;
import com.bandaoti.employee.service.RoleService;
import com.bandaoti.employee.vo.EmployeeVO;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("role")
public class RoleController extends BaseController {
	@Autowired
	private RoleService roleService;
	/**
	 * 查询所有角色
	 * @return
	 */
	@GetMapping("findRole")
	public ControllerResult findRole(String roleName,Integer pageNum,Integer pageSize){
		if(pageNum==null||pageNum<1)pageNum=1;
		if(pageSize==null||pageSize<1)pageSize=10;
		PageInfo<Role> roles=roleService.getRoles(roleName,pageNum,pageSize);
		Map<String,Object> result=new HashMap<>();
		result.put("roles", roles);
		EmployeeVO empVo=getUserNotError();
		if(empVo!=null){
			result.put("myRoles", roleService.getRoleByEmpId(empVo.getId()));
		}
		return success(result);
	}
	/**
	 * 新增角色
	 * @return
	 * @throws ControllerException 
	 */
	@GetMapping("addRole")
	public ControllerResult addRole() throws ControllerException{
		Role role=new Role();
		role.setCode(getParamNotNull("code"));
		role.setName(getParamNotNull("name"));
		role.setStatus(1);
		roleService.addRole(role);
		return success();
	}
	/**
	 * 删除角色
	 * @return
	 * @throws ControllerException 
	 */
	@GetMapping("delRole")
	public ControllerResult delRole() throws ControllerException{
		Integer roleId=getParamNotInteger("roleId");
		roleService.delRoleById(roleId);
		return success();
	}
	/**
	 * 禁用角色
	 * @return
	 * @throws ControllerException
	 */
	@GetMapping("disableRole")
	public ControllerResult disableRole() throws ControllerException{
		Integer roleId=getParamNotInteger("roleId");
		roleService.disableRoleById(roleId,0);
		return success();
	}
	/**
	 * 给员工添加角色
	 * @return
	 */
	@EmpAuthority("admin")
	@GetMapping("addRoleToEmp")
	public ControllerResult addRoleToEmp(){
		return success();
	}
	/**
	 * 申请权限
	 * @return
	 * @throws ControllerException 
	 */
	@EmpAuthority
	@GetMapping("reqRole")
	public ControllerResult reqRole() throws ControllerException{
		Integer roleId=getParamNotInteger("roleId");
		Integer empId=getParamNotInteger("empId");
		roleService.reqRole(getUser().getId(),empId,roleId);
		return success();
	}
	/**
	 * 删除员工角色
	 * @return
	 * @throws ControllerException 
	 */
	@EmpAuthority
	@GetMapping("delRoleFormEmp")
	public ControllerResult delRoleFormEmp() throws ControllerException{
		Integer roleId=getParamNotInteger("id");
		roleService.delRoleByFormEmp(roleId);
		return success();
	}
}
