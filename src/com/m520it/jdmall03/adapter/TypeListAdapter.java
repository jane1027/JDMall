package com.m520it.jdmall03.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.m520it.jdmall03.R;

public class TypeListAdapter extends JDBaseAdapter<String> {

	public int mCurrentTabPosition=-1;
	
	public TypeListAdapter(Context c) {
		super(c);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView brandBtn;
		if (convertView==null) {
			convertView=mInflater.inflate(R.layout.brand_lv_item_layout, parent,false);
			brandBtn=(TextView) convertView.findViewById(R.id.brand_tv);
			convertView.setTag(brandBtn);
		}else {
			brandBtn=(TextView) convertView.getTag();
		}
		 String productType = mDatas.get(position);
		brandBtn.setText(productType);
		//2.修改样式
		brandBtn.setSelected(position==mCurrentTabPosition);
		return convertView;
	}
	
	@Override
	public Object getItem(int position) {
		return mDatas!=null?mDatas.get(position):"";
	}

}
