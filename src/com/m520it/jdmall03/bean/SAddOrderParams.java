package com.m520it.jdmall03.bean;

import java.util.ArrayList;

public class SAddOrderParams {

	private long userId;
	private long addrId;//��ַid
	private int payWay=-1;//0����֧�� 1��������
	private ArrayList<SaddOrderProductParams> products;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getAddrId() {
		return addrId;
	}
	public void setAddrId(long addrId) {
		this.addrId = addrId;
	}
	public int getPayWay() {
		return payWay;
	}
	public void setPayWay(int payWay) {
		this.payWay = payWay;
	}
	public ArrayList<SaddOrderProductParams> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<SaddOrderProductParams> products) {
		this.products = products;
	}
	
}
