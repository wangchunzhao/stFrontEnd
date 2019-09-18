package com.qhc.steigenberger.domain;

import java.io.Serializable;


import com.qhc.steigenberger.util.Page;

public class BNotifyInfor extends Page implements Serializable {
	
	private int id;
	
	private int hasSend;
	
	private int msg_to;
	
	private int msg_from;
	
	private int message;

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

	public int getMsg_to() {
		return msg_to;
	}

	public void setMsg_to(int msg_to) {
		this.msg_to = msg_to;
	}

	public int getMsg_from() {
		return msg_from;
	}

	public void setMsg_from(int msg_from) {
		this.msg_from = msg_from;
	}

	public int getMessage() {
		return message;
	}

	public void setMessage(int message) {
		this.message = message;
	}
	
}
