package com.m520it.jdmall03.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.adapter.ReceiverListAdapter;
import com.m520it.jdmall03.bean.RReceiver;
import com.m520it.jdmall03.cons.IdiyMessage;
import com.m520it.jdmall03.controller.ShopcarController;

public class ChooseReceiverActivity extends BaseActivity implements OnItemClickListener {

	private ListView mReceiverLv;
	private ReceiverListAdapter mAdapter;
	
	@Override
	protected void handlerMessage(Message msg) {
		switch (msg.what) {
			case IdiyMessage.CHOOSE_RECEIVER_ACTION_RESULT:
				handleReceiverList((List<RReceiver>) msg.obj);
				break;
		}
	}
	
	private void handleReceiverList(List<RReceiver> datas){
		mAdapter.setDatas(datas);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_receiver);
		initController();
		initUI();
		mController.sendAsyncMessage(IdiyMessage.CHOOSE_RECEIVER_ACTION, 0);
	}
	
	@Override
	protected void initController() {
		mController=new ShopcarController(this);
		mController.setIModeChangeListener(this);
	}
	
	@Override
	protected void initUI() {
		mReceiverLv =(ListView) findViewById(R.id.receive_lv);
		mAdapter = new ReceiverListAdapter(this);
		mReceiverLv.setAdapter(mAdapter);
		mReceiverLv.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
//		点击item 将数据返回到原来的Activity
//		1.点击item 获取某个item内部的RReceiver
		RReceiver bean = (RReceiver) mAdapter.getItem(position);
//		2.返回到原来的Activity
		Intent intent=new Intent();
		intent.putExtra("CHOOSERECEIVER", bean);
		setResult(0, intent);
		finish();
	}


}
