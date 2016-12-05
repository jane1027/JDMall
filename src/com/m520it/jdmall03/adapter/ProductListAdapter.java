package com.m520it.jdmall03.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.RProductList;
import com.m520it.jdmall03.cons.NetworkConst;

public class ProductListAdapter extends JDBaseAdapter<RProductList> {

	public ProductListAdapter(Context c) {
		super(c);
	}

	class ViewHolder{
		SmartImageView smIv;
		TextView nameTv;
		TextView priceTv;
		TextView commentrateTv;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holer=null;
		if (convertView==null) {
			convertView=mInflater.inflate(R.layout.product_lv_item, parent,false);
			holer=new ViewHolder();
			holer.smIv=(SmartImageView) convertView.findViewById(R.id.product_iv);
			holer.nameTv=(TextView) convertView.findViewById(R.id.name_tv);
			holer.priceTv=(TextView) convertView.findViewById(R.id.price_tv);
			holer.commentrateTv=(TextView) convertView.findViewById(R.id.commrate_tv);
			convertView.setTag(holer);
		}else {
			holer=(ViewHolder) convertView.getTag();
		}
		RProductList bean = mDatas.get(position);
		
		holer.smIv.setImageUrl(NetworkConst.BASE_URL+bean.getIconUrl());
		holer.nameTv.setText(bean.getName());
		holer.priceTv.setText("¥ "+bean.getPrice());
		holer.commentrateTv.setText(bean.getCommentCount()+"条评价  好评"+bean.getFavcomRate()+"%");
		return convertView;
	}
	
	@Override
	public long getItemId(int position) {
		return mDatas!=null?mDatas.get(position).getId():0;
	}

}
