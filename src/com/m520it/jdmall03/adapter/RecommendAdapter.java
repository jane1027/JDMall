package com.m520it.jdmall03.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.RRecommndProduct;
import com.m520it.jdmall03.cons.NetworkConst;

public class RecommendAdapter extends JDBaseAdapter<RRecommndProduct> {

	public RecommendAdapter(Context c) {
		super(c);
	}

	class ViewHolder{
		SmartImageView smIv;
		TextView nameTv;
		TextView priceTv;
		TextView allPriceTv;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holer=null;
		if (convertView==null) {
			convertView=mInflater.inflate(R.layout.recommend_gv_item, parent,false);
			holer=new ViewHolder();
			holer.smIv=(SmartImageView) convertView.findViewById(R.id.image_iv);
			holer.nameTv=(TextView) convertView.findViewById(R.id.name_tv);
			holer.priceTv=(TextView) convertView.findViewById(R.id.price_tv);
			convertView.setTag(holer);
		}else {
			holer=(ViewHolder) convertView.getTag();
		}
		RRecommndProduct bean = mDatas.get(position);
		holer.smIv.setImageUrl(NetworkConst.BASE_URL+bean.getIconUrl());
		holer.nameTv.setText(bean.getName());
		holer.priceTv.setText("¥ "+bean.getPrice());
		return convertView;
	}
	
	@Override
	public long getItemId(int position) {
		return mDatas!=null?mDatas.get(position).getProductId():0;
	}

}
