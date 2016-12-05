package com.m520it.jdmall03.adapter;


import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.RSecondKill;
import com.m520it.jdmall03.cons.NetworkConst;

public class SecondKillAdapter extends JDBaseAdapter<RSecondKill> {

	public SecondKillAdapter(Context c) {
		super(c);
	}

	class ViewHolder{
		SmartImageView smIv;
		TextView pointPriceTv;
		TextView allPriceTv;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holer=null;
		if (convertView==null) {
			convertView=mInflater.inflate(R.layout.home_seckill_item, parent,false);
			holer=new ViewHolder();
			holer.smIv=(SmartImageView) convertView.findViewById(R.id.image_iv);
			holer.pointPriceTv=(TextView) convertView.findViewById(R.id.nowprice_tv);
			holer.allPriceTv=(TextView) convertView.findViewById(R.id.normalprice_tv);
			convertView.setTag(holer);
		}else {
			holer=(ViewHolder) convertView.getTag();
		}
		RSecondKill bean = mDatas.get(position);
		holer.smIv.setImageUrl(NetworkConst.BASE_URL+bean.getIconUrl());
		holer.pointPriceTv.setText("¥ "+bean.getPointPrice());
		holer.allPriceTv.setText(" ¥ "+bean.getAllPrice()+" ");
		holer.allPriceTv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
		return convertView;
	}

}
