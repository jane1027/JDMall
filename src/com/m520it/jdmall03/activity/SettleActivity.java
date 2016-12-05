package com.m520it.jdmall03.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.image.SmartImageView;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.ROrderParams;
import com.m520it.jdmall03.bean.RReceiver;
import com.m520it.jdmall03.bean.RResult;
import com.m520it.jdmall03.bean.RShopcar;
import com.m520it.jdmall03.bean.SAddOrderParams;
import com.m520it.jdmall03.bean.SaddOrderProductParams;
import com.m520it.jdmall03.cons.IdiyMessage;
import com.m520it.jdmall03.cons.NetworkConst;
import com.m520it.jdmall03.controller.ShopcarController;
import com.m520it.jdmall03.fragment.ShopcarFragment;
import com.m520it.jdmall03.ui.pop.AddOrderDialog;

public class SettleActivity extends BaseActivity implements OnClickListener {

	private RelativeLayout mNoReceiverRl;
	private RelativeLayout mHasReceiverRl;
	private TextView mReceiverNameTv;
	private TextView mReceiverPhoneTv;
	private TextView mReceiverAddressTv;
	private ArrayList<RShopcar> mCheckedDatas;
	private LinearLayout mProductContainerLl;
	private TextView mTotalSizeTv;
	private double mTotalPrice;
	private TextView mTotalPriceTv;
	private TextView mPayMoneyTv;
	private Button mPayOnlineTv;
	private Button mPayWhenGetTv;
	private long mAddressId;
	private static final int ADD_RECEIVER_REQ = 0x001;
	private static final int CHOOSE_RECEIVER_REQ = 0x002;

	@Override
	protected void handlerMessage(Message msg) {
		switch (msg.what) {
			case IdiyMessage.GET_DEFAULT_RECEIVER_ACTION_RESULT:
				handleDefaultReciver(msg.obj);
				break;
			case IdiyMessage.ADD_ORDER_ACTION_RESULT:
				handlAddOrder((RResult) msg.obj);
				break;
		}
	}
	
	//处理下单的结果 成功的情况下会弹出一个对话框  删除购物车数据
	private void handlAddOrder(RResult resultBean){
		if (resultBean.isSuccess()) {
			//1.取出数据
			ROrderParams bean = JSON.parseObject(resultBean.getResult(),ROrderParams.class);
			//弹出一个对话框
			AddOrderDialog addOrderDialog = new AddOrderDialog(this,bean);
			addOrderDialog.show();
		}else {
			tip("订单添加失败:"+resultBean.getErrorMsg());
		}
	}

	/**
	 * 判断有没默认的收货人地址决定是否显示有收货人地址的模块
	 */
	private void handleDefaultReciver(Object obj) {
		mNoReceiverRl.setVisibility(obj != null ? View.GONE : View.VISIBLE);
		mHasReceiverRl.setVisibility(obj != null ? View.VISIBLE : View.GONE);
		if (obj != null) {
			RReceiver bean = (RReceiver) obj;
			mAddressId = bean.getId();
			// 为mHasReceiverRl内部控件添加数据
			mReceiverNameTv.setText(bean.getReceiverName());
			mReceiverPhoneTv.setText(bean.getReceiverPhone());
			mReceiverAddressTv.setText(bean.getReceiverAddress());
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settle);
		initData();
		initController();
		initUI();
		mController.sendAsyncMessage(IdiyMessage.GET_DEFAULT_RECEIVER_ACTION,
				true);
	}

	@Override
	protected void initData() {
		Intent intent = getIntent();
		mCheckedDatas = (ArrayList<RShopcar>) intent
				.getSerializableExtra(ShopcarFragment.CHECKDATAS);
		mTotalPrice = intent.getDoubleExtra(ShopcarFragment.CHECKTOTALPRICE, 0);
		if (mCheckedDatas == null || mCheckedDatas.size() == 0
				|| mTotalPrice == 0) {
			finish();
		}
	}

	@Override
	protected void initController() {
		mController = new ShopcarController(this);
		mController.setIModeChangeListener(this);
	}

	@Override
	protected void initUI() {
		mHasReceiverRl = (RelativeLayout) findViewById(R.id.has_receiver_rl);
		mReceiverNameTv = (TextView) findViewById(R.id.name_tv);
		mReceiverPhoneTv = (TextView) findViewById(R.id.phone_tv);
		mReceiverAddressTv = (TextView) findViewById(R.id.address_tv);
		mNoReceiverRl = (RelativeLayout) findViewById(R.id.no_receiver_rl);
		initBuyProductModel();
		mTotalSizeTv = (TextView) findViewById(R.id.total_psize_tv);
		mTotalSizeTv.setText("共" + mCheckedDatas.size() + "件");
		mTotalPriceTv = (TextView) findViewById(R.id.all_price_val_tv);
		mTotalPriceTv.setText("¥ " + mTotalPrice);
		mPayMoneyTv = (TextView) findViewById(R.id.pay_money_tv);
		mPayMoneyTv.setText("实付款:¥ " + mTotalPrice);

		mPayOnlineTv = (Button) findViewById(R.id.pay_online_tv);
		mPayOnlineTv.setOnClickListener(this);
		mPayWhenGetTv = (Button) findViewById(R.id.pay_whenget_tv);
		mPayWhenGetTv.setOnClickListener(this);
	}

	private void initBuyProductModel() {
		// 展示购买的商品
		mProductContainerLl = (LinearLayout) findViewById(R.id.product_container_ll);
		// mCheckedDatas
		// 容器内部的控件个数
		int childCount = mProductContainerLl.getChildCount();
		// 获取数据的个数
		int dataSize = mCheckedDatas.size();
		// 取最小
		int realSize = Math.min(childCount, dataSize);
		// 填充数据
		for (int i = 0; i < realSize; i++) {
			// 取出子项数据
			RShopcar data = mCheckedDatas.get(i);
			// 取出子项的布局
			LinearLayout mIemLl = (LinearLayout) mProductContainerLl
					.getChildAt(i);
			SmartImageView smiv = (SmartImageView) mIemLl
					.findViewById(R.id.piv);
			smiv.setImageUrl(NetworkConst.BASE_URL + data.getPimageUrl());
			TextView psizeTv = (TextView) mIemLl.findViewById(R.id.psize);
			psizeTv.setText("x " + data.getBuyCount());
		}
	}

	public void addAddress(View v) {
		// 启动一个新的界面
		Intent intent = new Intent(this, AddReciverActivity.class);
		startActivityForResult(intent, ADD_RECEIVER_REQ);
	}

	public void chooseAddress(View v) {
		// 启动一个新的界面
		Intent intent = new Intent(this, ChooseReceiverActivity.class);
		startActivityForResult(intent, CHOOSE_RECEIVER_REQ);
	}

	@Override
	protected void onActivityResult(int reqCode, int resCode, Intent data) {
		if (reqCode == ADD_RECEIVER_REQ) {
			// 1.决定要显示什么模块
			if (data != null) {
				RReceiver bean = (RReceiver) data
						.getSerializableExtra("RReceiverAddress");
				// 2.应该往有数据的界面进行数据填充
				handleDefaultReciver(bean);
			}
		} else if (reqCode == CHOOSE_RECEIVER_REQ) {
			// 1.决定要显示什么模块
			if (data != null) {
				RReceiver bean = (RReceiver) data
						.getSerializableExtra("CHOOSERECEIVER");
				// 2.应该往有数据的界面进行数据填充
				handleDefaultReciver(bean);
			}
		}
		super.onActivityResult(reqCode, resCode, data);
	}

	@Override
	public void onClick(View v) {
		mPayOnlineTv.setSelected(v.getId() == R.id.pay_online_tv);
		mPayWhenGetTv.setSelected(v.getId() == R.id.pay_whenget_tv);
		switch (v.getId()) {
		case R.id.pay_online_tv:// 在线支付

			break;
		case R.id.pay_whenget_tv:// 货到付款

			break;
		}
	}

	public void submitClick(View v) {
		// 发票
		// 金额 以最后产生订单的金额为准 (11秒杀价 根据路途的变化 需要物流运费)
		// 提交订单
		// 1.必须有收货人的信息
		if (mAddressId == 0) {
			tip("请选择收货人信息");
			return;
		}
		// 2.支付方式必须选一个
		if (!mPayOnlineTv.isSelected() && !mPayWhenGetTv.isSelected()) {
			tip("请选择支付方式");
			return;
		}
		// 3.把所有的信息构建成SAddOrderParams对象
		SAddOrderParams params = initAddOrderParams();
		mController.sendAsyncMessage(IdiyMessage.ADD_ORDER_ACTION, params);
	}

	private SAddOrderParams initAddOrderParams() {
		SAddOrderParams paramBean = new SAddOrderParams();
		paramBean.setAddrId(mAddressId);
		paramBean.setPayWay(mPayOnlineTv.isSelected() ? 0 : 1);
		// mCheckedDatas--->products
		ArrayList<SaddOrderProductParams> products = new ArrayList<SaddOrderProductParams>();// 从哪里来?
		for (int i = 0; i < mCheckedDatas.size(); i++) {
			RShopcar oldData = mCheckedDatas.get(i);
			products.add(new SaddOrderProductParams(oldData.getPid(), oldData
					.getBuyCount(), oldData.getPversion()));
		}
		paramBean.setProducts(products);
		return paramBean;
	}

}
