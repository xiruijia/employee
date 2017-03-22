package com.bandaoti.employee;

public enum ReturnCode {
	SUCCESS(0, "成功"), 
	FAILE(1, "失败"), 
	WX_USERINFO_FAILE(2, "微信用户信息解析失败"), 
	USER_NOT_LOGIN(3, "用户未登录"), 
	ACCOUNT_PASSWORD_ERROR(4, "帐号或密码错误"),
	
	ACCOUNT_EXIST_ERROR(100,"帐号已存在"),
	
	SMS_CODE_ERROR(200,"短信验证码错误"),
	
	ROLE_EXIST_ERROR(300,"角色已存在"),
	ROLE_NOT_EXIST_ERROR(301,"角色已存在"),
	
	NUMBER_FORMAT_ERROR(400,"数字转换错误"),
	;

	private Integer key;
	private String value;

	private ReturnCode(Integer key, String value) {
		this.key = key;
		this.value = value;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
