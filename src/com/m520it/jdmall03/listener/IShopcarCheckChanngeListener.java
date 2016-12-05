package com.m520it.jdmall03.listener;

public interface IShopcarCheckChanngeListener {

	public void onBuyCountChanged(int count);
	
	public void onTotalPriceChanged(double newestPrice);
	
}
