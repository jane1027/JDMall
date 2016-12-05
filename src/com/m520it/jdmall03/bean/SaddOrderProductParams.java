package com.m520it.jdmall03.bean;

public class SaddOrderProductParams {

	private long pid;
	private int buyCount;
	private String type;
	public SaddOrderProductParams() {
		super();
	}
	public SaddOrderProductParams(long pid, int buyCount, String type) {
		this.pid = pid;
		this.buyCount = buyCount;
		this.type = type;
	}
	public long getPid() {
		return pid;
	}
	public void setPid(long pid) {
		this.pid = pid;
	}
	public int getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
