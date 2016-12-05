package com.m520it.jdmall03.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.image.SmartImageView;
import com.m520it.jdmall03.cons.NetworkConst;

public class ProductAdAdapter extends PagerAdapter {

	private List<String> mImageUrList;
	private List<SmartImageView> mSmartImageViews;

	public void setDatas(Context c,List<String> imageUrList) {
		mImageUrList=imageUrList;
		//当数据有了之后 就有对应的图片
		mSmartImageViews=new ArrayList<SmartImageView>();
		for (int i = 0; i < imageUrList.size(); i++) {
//			创建控件 设置宽高 设置数据源 添加到View容器中
//			如果创建的控件看不见: 添加到容器中被调用   控件不可见 控件大小没设置--->可以给控件一个颜色值
			SmartImageView smiv=new SmartImageView(c);
			ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			smiv.setLayoutParams(params);
			smiv.setImageUrl(NetworkConst.BASE_URL+mImageUrList.get(i));
			mSmartImageViews.add(smiv);
		}
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		SmartImageView smiv = mSmartImageViews.get(position);
		container.addView(smiv);
		return smiv;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		SmartImageView smiv = mSmartImageViews.get(position);
		container.removeView(smiv);
	}
	
	@Override
	public int getCount() {
		return mSmartImageViews!=null?mSmartImageViews.size():0;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}

	

}
