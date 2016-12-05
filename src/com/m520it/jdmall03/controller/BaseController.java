package com.m520it.jdmall03.controller;

import android.content.Context;

import com.m520it.jdmall03.listener.IModeChangeListener;

public abstract class BaseController {

	protected IModeChangeListener mListener;
	protected Context mContext;

	public void setIModeChangeListener(IModeChangeListener listener) {
		mListener=listener;
	}
	
	public BaseController(Context c) {
		mContext=c;
	}
	
	/**
	 * @param action  һ��ҳ������ж����������,����������Щ�����
	 * @param values ���������
	 */
	public void sendAsyncMessage(final int action,final Object... values){
		new Thread(){
			public void run() {
				handleMessage(action, values);
			}
		}.start();
	}
	
	/**
	 * ���ദ�����������ҵ�����
	 */
	protected abstract void handleMessage(int action,Object... values);
	

}
