package com.m520it.jdmall03.ui.pop;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.ROrderParams;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AddOrderDialog extends AlertDialog implements
		android.view.View.OnClickListener {

	private ROrderParams mBean;

	public AddOrderDialog(Context context, ROrderParams bean) {
		super(context, R.style.CustomDialog);
		mBean = bean;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.build_order_pop_view);
		// 1.获取所有的子控件
		TextView orderNOTv = (TextView) findViewById(R.id.order_no_tv);
		TextView totalPriceTv = (TextView) findViewById(R.id.total_price_tv);
		TextView freightTv = (TextView) findViewById(R.id.freight_tv);
		TextView actualPpriceTv = (TextView) findViewById(R.id.actual_price_tv);
		findViewById(R.id.sure_btn).setOnClickListener(this);
		// 2.为其赋值
		orderNOTv.setText("订单编号:" + mBean.getOrderNum());
		totalPriceTv.setText("总价: ¥" + mBean.getAllPrice());
		freightTv.setText("运费: ¥" + mBean.getFreight());
		actualPpriceTv.setText("实付: ¥" + mBean.getTotalPrice());
	}

	@Override
	public void onClick(View v) {
		//TODO 确定按钮
	}

}
