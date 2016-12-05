package com.m520it.jdmall03.adapter;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.RShopcar;
import com.m520it.jdmall03.cons.NetworkConst;
import com.m520it.jdmall03.listener.IShopcarCheckChanngeListener;
import com.m520it.jdmall03.listener.IShopcarDeleteLister;

public class ShopcarAdapter extends JDBaseAdapter<RShopcar> {

	private static ArrayList<Boolean> sItemChecked=new ArrayList<Boolean>();
	private IShopcarCheckChanngeListener mListener;
	private IShopcarDeleteLister mIShopcarDeleteLister;
	
	public ShopcarAdapter(Context c) {
		super(c);
	}

	public void setIShopcarCheckChanngeListener(IShopcarCheckChanngeListener listener) {
		mListener=listener;
	}

	public void setIShopcarDeleteLister(IShopcarDeleteLister listener) {
		mIShopcarDeleteLister=listener;
	}
	
	@Override
	public void setDatas(List<RShopcar> datas) {
		super.setDatas(datas);
		//初始化sItemChecked
		sItemChecked.clear();
		for (int i = 0; i < datas.size(); i++) {
			sItemChecked.add(false);
		}
	}
	
	//设置item是否选中
	public void setCheck(int position) {
//		1.判断item是否选中 如果选中则取消  如未选中 则选中
		sItemChecked.set(position, !sItemChecked.get(position));
//		2.刷新界面
		notifyDataSetChanged();
//		3.刷新外部的Fragment 
		refreshOuterFragmentTip();
	}
	
	public void checkAll(boolean flag){
		for (int i = 0; i < sItemChecked.size(); i++) {
			sItemChecked.set(i, flag);
		}
		notifyDataSetChanged();
//		3.刷新外部的Fragment 
		refreshOuterFragmentTip();
	}

	private void refreshOuterFragmentTip() {
		if (mListener!=null) {
			int count=0;
			for (int i = 0; i < sItemChecked.size(); i++) {
				if (sItemChecked.get(i)) {
					count++;
				}
			}
			mListener.onBuyCountChanged(count);
			
			double totalPrice = 0;
			for (int i = 0; i < sItemChecked.size(); i++) {
				if (sItemChecked.get(i)) {
					// 如果某一选项被选中了 拿到选项的单价*购买数量
					RShopcar bean = mDatas.get(i);
					totalPrice += (bean.getPprice() * bean.getBuyCount());
				}
			}
			mListener.onTotalPriceChanged(totalPrice);
		}
	}

	public boolean ifItemChecked(){
		for (int i = 0; i < sItemChecked.size(); i++) {
			if (sItemChecked.get(i)) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<RShopcar> getCheckedItems(){
		ArrayList<RShopcar> result=new ArrayList<RShopcar>();
		for (int i = 0; i < sItemChecked.size(); i++) {
			if (sItemChecked.get(i)) {
				result.add(mDatas.get(i));
			}
		}
		return result;
	}
	
	class ViewHolder{
		CheckBox itemCbx;
		SmartImageView smiv;
		TextView productNameTv;
		TextView productVersionTv;
		TextView pPriceTv;
		TextView buyCountTv;
		Button deleteBtn;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if (convertView==null) {
			convertView=mInflater.inflate(R.layout.shopcar_lv_item, parent,false);
			holder=new ViewHolder();
			holder.itemCbx = (CheckBox) convertView.findViewById(R.id.cbx);
			holder.smiv = (SmartImageView) convertView.findViewById(R.id.product_iv);
			holder.productNameTv = (TextView) convertView.findViewById(R.id.pname_tv);
			holder.productVersionTv = (TextView) convertView.findViewById(R.id.pversion_tv);
			holder.pPriceTv = (TextView) convertView.findViewById(R.id.price_tv);
			holder.buyCountTv = (TextView) convertView.findViewById(R.id.buyCount_tv);
			holder.deleteBtn = (Button) convertView.findViewById(R.id.delete_product);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		final RShopcar bean = mDatas.get(position);
		holder.smiv.setImageUrl(NetworkConst.BASE_URL+bean.getPimageUrl());
		holder.productNameTv.setText(bean.getPname());
		holder.productVersionTv.setText(bean.getPversion());
		holder.pPriceTv.setText(" ¥ "+bean.getPprice());
		holder.buyCountTv.setText("x "+bean.getBuyCount());
		holder.itemCbx.setChecked(sItemChecked.get(position));
		holder.deleteBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				需要往后台发送请求
				if (mIShopcarDeleteLister!=null) {
					mIShopcarDeleteLister.onItemDelete(bean.getId());
				}
			}
		});
		return convertView;
	}

}
