package com.m520it.jdmall03.ui.pop;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

public abstract class IPopupWindowProtocal {

	protected PopupWindow mPopWindow;
	protected Context mContext;
	
	public IPopupWindowProtocal(Context c) {
		mContext=c;
		initController();
		initUI();
	}
	
	protected void initController(){
		
	}
	
	protected abstract void initUI();
	
	public abstract void onShow(View anchor);
	
	public void onDismiss(){
		if (mPopWindow!=null&&mPopWindow.isShowing()) {
			mPopWindow.dismiss();
		}
	}
	
}
