package com.m520it.jdmall03.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.activity.SettleActivity;
import com.m520it.jdmall03.adapter.ShopcarAdapter;
import com.m520it.jdmall03.bean.RResult;
import com.m520it.jdmall03.bean.RShopcar;
import com.m520it.jdmall03.cons.IdiyMessage;
import com.m520it.jdmall03.controller.ShopcarController;
import com.m520it.jdmall03.listener.IShopcarCheckChanngeListener;
import com.m520it.jdmall03.listener.IShopcarDeleteLister;
import com.m520it.jdmall03.util.ActivityUtil;

public class ShopcarFragment extends BaseFragment implements
		OnItemClickListener, IShopcarCheckChanngeListener,
		OnCheckedChangeListener, IShopcarDeleteLister, OnClickListener {

	private ListView mShopCarLv;
	private ShopcarAdapter mShopcarAdapter;
	private TextView mSettleTv;
	private TextView mAllMoneyTv;
	private CheckBox mAllItemCbx;
	private double mTotalPrice;

	@Override
	protected void handlerMessage(Message msg) {
		switch (msg.what) {
		case IdiyMessage.SHOPCAR_LIST_ACTION_RESULT:
			handleLoadShopcar((List<RShopcar>) msg.obj);
			break;
		case IdiyMessage.DELET_SHOPCAR_ACTION_RESULT:
			handleDeleteShopcar((RResult) msg.obj);
			break;
		}
	}

	private void handleDeleteShopcar(RResult resultBean) {
		if (resultBean.isSuccess()) {
			// 1.告诉Adapter删除某个item的数据(找到mDatas去删除 对应的sItemChecked也应该删除索引)
			// 外部的提示信息也要更新
			// 2.重新请求数据
			mController.sendAsyncMessage(IdiyMessage.SHOPCAR_LIST_ACTION, 0);
		} else {
			tip("删除失败:" + resultBean.getErrorMsg());
		}
	}

	private void handleLoadShopcar(List<RShopcar> datas) {
		mShopcarAdapter.setDatas(datas);
		mShopcarAdapter.notifyDataSetChanged();
		//界面刷新后 重新修改2个提示
		mSettleTv.setText("去结算(0)");
		mAllMoneyTv.setText("总额: ￥ 0");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_shopcar, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initController();
		initUI();
		mController.sendAsyncMessage(IdiyMessage.SHOPCAR_LIST_ACTION, 0);
	}

	@Override
	protected void initController() {
		mController = new ShopcarController(getActivity());
		mController.setIModeChangeListener(this);
	}

	@Override
	protected void initUI() {
		mShopCarLv = (ListView) getActivity().findViewById(R.id.shopcar_lv);
		mShopcarAdapter = new ShopcarAdapter(getActivity());
		mShopcarAdapter.setIShopcarCheckChanngeListener(this);
		mShopcarAdapter.setIShopcarDeleteLister(this);
		mShopCarLv.setAdapter(mShopcarAdapter);
		mShopCarLv.setOnItemClickListener(this);

		mSettleTv = (TextView) getActivity().findViewById(R.id.settle_tv);
		mSettleTv.setOnClickListener(this);
		mAllMoneyTv = (TextView) getActivity().findViewById(R.id.all_money_tv);
		mAllItemCbx = (CheckBox) getActivity().findViewById(R.id.all_cbx);
		mAllItemCbx.setOnCheckedChangeListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mShopcarAdapter.setCheck(position);
	}

	@Override
	public void onBuyCountChanged(int count) {
		mSettleTv.setText("去结算(" + count + ")");
	}

	@Override
	public void onTotalPriceChanged(double newestPrice) {
		mTotalPrice=newestPrice;
		mAllMoneyTv.setText("总额: ￥ " + newestPrice);
	}

	// 全选按钮的监听器
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		mShopcarAdapter.checkAll(isChecked);
	}

	@Override
	public void onItemDelete(long shopcarId) {
		// 发送删除购物车
		mController.sendAsyncMessage(IdiyMessage.DELET_SHOPCAR_ACTION,
				shopcarId);
	}

	public static final String CHECKDATAS="CHECKDATAS";
	public static final String CHECKTOTALPRICE="CHECKTOTALPRICE";
	
	//TODO 结算的按钮
	@Override
	public void onClick(View v) {
//		判断是否选中
		if (!mShopcarAdapter.ifItemChecked()) {
			tip("请选择购买的商品!");
			return ;
		}
		Intent intent=new Intent(getActivity(),SettleActivity.class);
		ArrayList<RShopcar> checkedDatas = mShopcarAdapter.getCheckedItems();
		intent.putExtra(CHECKDATAS, checkedDatas);
		intent.putExtra(CHECKTOTALPRICE, mTotalPrice);
		startActivity(intent);
	}

}
