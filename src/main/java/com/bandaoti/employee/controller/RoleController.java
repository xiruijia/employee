package com.bandaoti.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bandaoti.employee.BaseController;
import com.bandaoti.employee.ControllerException;
import com.bandaoti.employee.ControllerResult;
import com.bandaoti.employee.entity.Role;
import com.bandaoti.employee.service.RoleService;

@RestController
@RequestMapping("role")
public class RoleController extends BaseController {
	@Autowired
	private RoleService roleService;
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
	@GetMapping("addRoleToEmp")
	public ControllerResult addRoleToEmp(){
		return success();
	}
	/**
	 * 删除员工角色
	 * @return
	 */
	@GetMapping("delRoleFormEmp")
	public ControllerResult delRoleFormEmp(){
		return success();
	}
}
