package com.bandaoti.employee.service;

import com.bandaoti.employee.vo.RequestRoleVO;
import com.github.pagehelper.PageInfo;

public interface RequestRoleService {
	PageInfo<RequestRoleVO> getRequestRoleByEmpId(Integer pagenum,Integer empId);

	void agreeRole(Integer msgId, boolean agree);
}
