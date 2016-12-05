package com.m520it.jdmall03.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

public abstract class JDBaseAdapter<T> extends BaseAdapter {

	protected List<T> mDatas;
	protected Context mContext;
	protected LayoutInflater mInflater;
	
	public JDBaseAdapter(Context c) {
		mContext=c;
		mInflater = LayoutInflater.from(mContext);
	}

	public void setDatas(List<T> datas) {
		mDatas = datas;
	}

	@Override
	public int getCount() {
		return mDatas!=null?mDatas.size():0;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
