package com.m520it.jdmall03.bean;

public class SProductList {

	private long categoryId;
	private int filterType=1;//1-综合 2-新品 3-评价
	private int sortType;//1-销量 2-价格高到低 3-价格低到高
	private int deliverChoose;//0-代表无选择 1代表京东配送 2-代表货到付款 4-代表仅看有货 3代表条件1+2 5代表条件1+4 6代表条件2+4
	private int minPrice;//最低价格
	private int maxPrice;//最高价格
	private long brandId;//最高价格
	
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public int getFilterType() {
		return filterType;
	}
	public void setFilterType(int filterType) {
		this.filterType = filterType;
	}
	public int getSortType() {
		return sortType;
	}
	public void setSortType(int sortType) {
		this.sortType = sortType;
	}
	public int getDeliverChoose() {
		return deliverChoose;
	}
	public void setDeliverChoose(int deliverChoose) {
		this.deliverChoose = deliverChoose;
	}
	public int getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}
	public int getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}
	public long getBrandId() {
		return brandId;
	}
	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}
	@Override
	public String toString() {
		return "SProductList [categoryId=" + categoryId + ", filterType="
				+ filterType + ", sortType=" + sortType + ", deliverChoose="
				+ deliverChoose + ", minPrice=" + minPrice + ", maxPrice="
				+ maxPrice + ", brandId=" + brandId + "]";
	}
	
}
