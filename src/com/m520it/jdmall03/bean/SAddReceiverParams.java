package com.m520it.jdmall03.bean;

public class SAddReceiverParams {
	
	public String name;
	public String phone;
	public String provinceCode;
	public String cityCode;
	public String distCode;
	public String addressDetails;
	public boolean isDefault;
	public SAddReceiverParams(String name, String phone, String provinceCode,
			String cityCode, String distCode, String addressDetails,
			boolean isDefault) {
		this.name = name;
		this.phone = phone;
		this.provinceCode = provinceCode;
		this.cityCode = cityCode;
		this.distCode = distCode;
		this.addressDetails = addressDetails;
		this.isDefault = isDefault;
	}
	
	
}
