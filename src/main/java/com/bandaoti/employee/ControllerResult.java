package com.bandaoti.employee;

import java.io.Serializable;

public class ControllerResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7513365585697750996L;

	private Integer code;
	private String msg;
	private Object data;
	

	public ControllerResult(Integer code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	

	public ControllerResult(Integer code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	
	public ControllerResult(ReturnCode returnCode) {
		setCode(returnCode.getKey());
		setMsg(returnCode.getValue());
	}
	public ControllerResult(ReturnCode returnCode,Object data) {
		setCode(returnCode.getKey());
		setMsg(returnCode.getValue());
		setData(data);
	}
	public ControllerResult(Object data){
		setCode(ReturnCode.SUCCESS.getKey());
		setMsg(ReturnCode.SUCCESS.getValue());
		setData(data);
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
