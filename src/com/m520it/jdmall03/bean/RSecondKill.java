package com.m520it.jdmall03.bean;

public class RSecondKill {

	private long productId;//商品id
	private double allPrice;//原价
	private double pointPrice;//秒杀价格
	private String iconUrl;//商品图片路径
	private int timeLeft;//秒杀剩余时间（分钟）,
	private int type;//秒杀类型（1抢年货，2超值，3热卖）
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public double getAllPrice() {
		return allPrice;
	}
	public void setAllPrice(double allPrice) {
		this.allPrice = allPrice;
	}
	public double getPointPrice() {
		return pointPrice;
	}
	public void setPointPrice(double pointPrice) {
		this.pointPrice = pointPrice;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public int getTimeLeft() {
		return timeLeft;
	}
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
