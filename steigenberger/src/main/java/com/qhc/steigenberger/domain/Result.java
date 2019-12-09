package com.qhc.steigenberger.domain;


import java.io.Serializable;

public class Result<T> implements Serializable {
	
	private static final long serialVersionUID = -8130395036916467013L;
	
	String status = "ok";
	String msg = "";
	T data = null;
	
	public static final Result<String> error(String msg) {
		Result<String> error = new Result<String>();
		error.setStatus("error");
		error.setMsg(msg);
		return error;
	}
	
	public static final <T> Result<T> ok(T content) {
		Result<T> ok = new Result<T>();
		ok.setStatus("ok");
		ok.setData(content);
		return ok;
	}
	
	public static final Result<String> logout() {
		Result<String> logout = new Result<String>();
		logout.setStatus("logout");
		logout.setMsg("没有登录或长期没有操作！请重新登录！");
		return logout;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Result [status=" + status + ", msg=" + msg + ", data=" + data + "]";
	}
	
}