package com.bandaoti.employee;

public enum ReturnCode {
	SUCCESS(0, "成功"), 
	FAILE(1, "失败"), 
	WX_USERINFO_FAILE(2, "微信用户信息解析失败"), 
	USER_NOT_LOGIN(3, "用户未登录"), 
	ACCOUNT_PASSWORD_ERROR(4, "帐号或密码错误"),
	ACCOUNT_EXIST_ERROR(100,"帐号已存在"),
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
