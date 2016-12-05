package com.m520it.jdmall03.listener;

/**
 *	删除购物车接口
 */
public interface IShopcarDeleteLister {

	/**
	 * @param shopcarId   被删除的购物车表 id
	 */
	public void onItemDelete(long shopcarId);
	
}
