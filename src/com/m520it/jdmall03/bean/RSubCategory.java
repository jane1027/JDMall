package com.m520it.jdmall03.bean;

public class RSubCategory {

	private long id;//2级分类的id
	private String name;//2级分类的名称
	private String thirdCategory;//3级分类所有数据 JSON
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getThirdCategory() {
		return thirdCategory;
	}
	public void setThirdCategory(String thirdCategory) {
		this.thirdCategory = thirdCategory;
	}
}
