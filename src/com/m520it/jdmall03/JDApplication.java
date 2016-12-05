package com.m520it.jdmall03;

import com.m520it.jdmall03.bean.RLoginResult;

import android.app.Application;

public class JDApplication extends Application {

	public RLoginResult mRLoginResult;
	
	@Override
	public void onCreate() {
		super.onCreate();
	}

	public void setRLoginResult(RLoginResult bean) {
		mRLoginResult=bean;
	}
	
}
