package com.m520it.jdmall03.bean;

public class RProductInfo {

	private long id;
	private String name;
	private String imgUrls;//JSON 所有图片地址
	private double price;
	private boolean ifSaleOneself;//是否自营
	private String typeList;//版本的JSON
	private int stockCount;//库存
	
	private int commentCount;//评论数
	private int favcomRate;//好评率
    private long recomProductId;//推荐商品id
    private String recomProduct;//推荐商品标题
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
	public String getImgUrls() {
		return imgUrls;
	}
	public void setImgUrls(String imgUrls) {
		this.imgUrls = imgUrls;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public boolean isIfSaleOneself() {
		return ifSaleOneself;
	}
	public void setIfSaleOneself(boolean ifSaleOneself) {
		this.ifSaleOneself = ifSaleOneself;
	}
	public String getTypeList() {
		return typeList;
	}
	public void setTypeList(String typeList) {
		this.typeList = typeList;
	}
	public int getStockCount() {
		return stockCount;
	}
	public void setStockCount(int stockCount) {
		this.stockCount = stockCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public int getFavcomRate() {
		return favcomRate;
	}
	public void setFavcomRate(int favcomRate) {
		this.favcomRate = favcomRate;
	}
	public long getRecomProductId() {
		return recomProductId;
	}
	public void setRecomProductId(long recomProductId) {
		this.recomProductId = recomProductId;
	}
	public String getRecomProduct() {
		return recomProduct;
	}
	public void setRecomProduct(String recomProduct) {
		this.recomProduct = recomProduct;
	}
    
}
