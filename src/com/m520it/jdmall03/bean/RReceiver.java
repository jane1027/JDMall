package com.m520it.jdmall03.bean;

import java.io.Serializable;

public class RReceiver  implements Serializable{

	private long id;
	private boolean isDefault;
	private String receiverName;
	private String receiverPhone;
	private String receiverAddress;//广东省广州天河xxx地区 xxx街
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	
}
