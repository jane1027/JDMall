package com.m520it.jdmall03.listener;

public interface IModeChangeListener {

	/**
	 * onModeChanged 跟UI说界面需要修改
	 * 
	 * 
	 * @param action 返回处理不同UI的action
	 */
	public void onModeChanged(int action,Object... values);
	
}
