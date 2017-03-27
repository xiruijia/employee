package com.bandaoti.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bandaoti.employee.BaseController;
import com.bandaoti.employee.ControllerException;
import com.bandaoti.employee.ControllerResult;
import com.bandaoti.employee.service.RequestRoleService;

@RestController
@RequestMapping("reqrole")
public class RequestRoleController extends BaseController{
	@Autowired
	private RequestRoleService requestRoleService;
	@GetMapping("getRequestRole")
	public ControllerResult getRequestRole(Integer pageNum) throws ControllerException{
		if(pageNum==null)pageNum=1;
		return success(requestRoleService.getRequestRoleByEmpId(pageNum, getUser().getId()));
	}
	
	@GetMapping("agreeRole")
	public ControllerResult argeeRole(boolean agree) throws ControllerException{
		Integer msgId=getParamNotInteger("msgId");
		requestRoleService.agreeRole(msgId,agree);
		return success();
	}
}
