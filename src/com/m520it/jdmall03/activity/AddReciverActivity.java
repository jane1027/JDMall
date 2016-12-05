package com.m520it.jdmall03.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.RArea;
import com.m520it.jdmall03.bean.RReceiver;
import com.m520it.jdmall03.bean.RResult;
import com.m520it.jdmall03.bean.SAddReceiverParams;
import com.m520it.jdmall03.cons.IdiyMessage;
import com.m520it.jdmall03.controller.ShopcarController;
import com.m520it.jdmall03.listener.IAreaChangeListener;
import com.m520it.jdmall03.ui.pop.ChooseAreaPopWindow;

public class AddReciverActivity extends BaseActivity implements
		IAreaChangeListener {

	private ChooseAreaPopWindow mChooseAreaPopWindow;
	private TextView mChooseAreaTv;
	private View mParentView;
	private EditText mNameEt;
	private EditText mPhoneEt;
	private EditText mAddressEt;
	private CheckBox mDefaultCbx;
	RArea mProvince;
	RArea mCity;
	RArea mArea;
	
	@Override
	protected void handlerMessage(Message msg) {
		switch (msg.what) {
			case IdiyMessage.ADD_RECEIVER_ACTION_RESULT:
				handleAddReceiver((RResult) msg.obj);
				break;
		}
	}
	
	private void handleAddReceiver(RResult resultBean){
		if (resultBean.isSuccess()) {
			RReceiver bean = JSON.parseObject(resultBean.getResult(),RReceiver.class);
			tip("����ջ��˳ɹ�!");
			Intent intent=new Intent();
			intent.putExtra("RReceiverAddress", bean);
			setResult(0,intent);
			finish();
		}else {
			tip("����ջ���ʧ��:"+resultBean.getErrorMsg());
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_receiver);
		initController();
		initUI();
	}

	@Override
	protected void initController() {
		mController = new ShopcarController(this);
		mController.setIModeChangeListener(this);
	}

	@Override
	protected void initUI() {
		mParentView = findViewById(R.id.parent_view);
		mChooseAreaTv = (TextView) findViewById(R.id.choose_province_tv);
		mNameEt = (EditText) findViewById(R.id.name_et);
		mPhoneEt = (EditText) findViewById(R.id.phone_et);
		mAddressEt = (EditText) findViewById(R.id.address_details_et);
		mDefaultCbx = (CheckBox) findViewById(R.id.default_cbx);
	}

	public void reGetAddress(View v) {
		// ����һ��ѡ��Ի���
		if (mChooseAreaPopWindow == null) {
			mChooseAreaPopWindow = new ChooseAreaPopWindow(this);
			mChooseAreaPopWindow.setIAreaChangeListener(this);
		}
		mChooseAreaPopWindow.onShow(mParentView);
	}

	@Override
	public void onAreaChanged(RArea province, RArea city, RArea area) {
		mProvince = province;
		mCity = city;
		mArea = area;
		mChooseAreaTv.setText(province.getName() + city.getName()
				+ area.getName());
	}

	public void saveAddress(View v) {
		String name = mNameEt.getText().toString();
		String phone = mPhoneEt.getText().toString();
		String addressDetails = mAddressEt.getText().toString();
		boolean isDefault = mDefaultCbx.isChecked();
		// ����У��
		if (ifValueWasEmpty(name, phone, addressDetails)) {
			tip("�������������ջ�����Ϣ");
			return;
		}
		if (mProvince == null || mCity == null || mArea == null) {
			tip("��ѡ��ʡ����");
			return;
		}
		// ����һ������
		mController
				.sendAsyncMessage(
						IdiyMessage.ADD_RECEIVER_ACTION,
						new SAddReceiverParams(name, phone,
								mProvince.getCode(), mCity.getCode(), mArea
										.getCode(), addressDetails, isDefault));
	}

}
