package com.qhc.steigenberger.domain;

import java.io.Serializable;


import com.qhc.steigenberger.util.Page;

public class NotifyInfor extends Page implements Serializable {
	
	private int id;
	
	private int hasSend;
	
	private String msg_to;
	
	private String msg_from;
	
	private String message;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHasSend() {
		return hasSend;
	}

	public void setHasSend(int hasSend) {
		this.hasSend = hasSend;
	}

	public String getMsg_to() {
		return msg_to;
	}

	public void setMsg_to(String msg_to) {
		this.msg_to = msg_to;
	}

	public String getMsg_from() {
		return msg_from;
	}

	public void setMsg_from(String msg_from) {
		this.msg_from = msg_from;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}
