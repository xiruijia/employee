package com.bandaoti.employee;

public class ControllerException extends Exception{
	private static final long serialVersionUID = 1L;
	
	private Integer code;
	private String msg;
	private Object data;
	
	public ControllerException(ReturnCode type) {
		super(type.getValue());
		setCode(type.getKey());
		setMsg(type.getValue());
	}
	
	public ControllerException(ReturnCode type,Object data) {
		super(type.getValue());
		setCode(type.getKey());
		setMsg(type.getValue());
		setData(data);
	}
	public ControllerException(ReturnCode type,Boolean replace,String data) {
		super(type.getValue());
		setCode(type.getKey());
		if(replace!=null && replace){
			setMsg(data);
		}else{
			setMsg(type.getValue());
			setData(data);
		}
	}
	
	public ControllerException() {
		super();
	}
	public ControllerException(Integer code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
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
	
	public ControllerResult getResult(){
		ControllerResult result=new ControllerResult(getCode(),getMsg(),getData());
		return result;
	}
	
}
