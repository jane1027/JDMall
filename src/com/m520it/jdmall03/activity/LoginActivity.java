package com.m520it.jdmall03.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall03.JDApplication;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.RLoginResult;
import com.m520it.jdmall03.bean.RResult;
import com.m520it.jdmall03.cons.IdiyMessage;
import com.m520it.jdmall03.controller.UserController;
import com.m520it.jdmall03.db.UserDao.UserInfo;
import com.m520it.jdmall03.util.ActivityUtil;

public class LoginActivity extends BaseActivity {

	private EditText mNameEt;
	private EditText mPwdEt;

	@Override
	protected void handlerMessage(Message msg) {
		switch (msg.what) {
		case IdiyMessage.LOGIN_ACTION_RESULT:
			handleLoginResult(msg);
			break;
		case IdiyMessage.SAVE_USERTODB_RESULT:
			handleSaveUser2Db((Boolean) msg.obj);
			break;
		case IdiyMessage.GET_USER_ACTION_RESULT:
			handlerGetUser(msg.obj);
			break;
		}
	}

	private void handlerGetUser(Object c) {
		if (c != null) {
			UserInfo userInfo = (UserInfo) c;
			mNameEt.setText(userInfo.name);
			mPwdEt.setText(userInfo.pwd);
		}
	}

	private void handleSaveUser2Db(boolean ifSuccess) {
		if (ifSuccess) {
			ActivityUtil.start(this, MainActivity.class, true);
		} else {
			tip("登录异常");
		}
	}

	private void handleLoginResult(Message msg) {
		RResult rResult = (RResult) msg.obj;
		if (rResult.isSuccess()) {
			// 1.将用户的信息保存到Application里面
			RLoginResult bean = JSON.parseObject(rResult.getResult(),
					RLoginResult.class);
			((JDApplication) getApplication()).setRLoginResult(bean);
			// 2.将账号密码保存到数据库 传递一个账号密码
			String name = mNameEt.getText().toString();
			String pwd = mPwdEt.getText().toString();
			mController.sendAsyncMessage(IdiyMessage.SAVE_USERTODB, name, pwd);
		} else {
			tip("登录失败:" + rResult.getErrorMsg());
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initController();
		initUI();
	}

	public void loginClick(View v) {
		String name = mNameEt.getText().toString();
		String pwd = mPwdEt.getText().toString();
		if (ifValueWasEmpty(name, pwd)) {
			tip("请输入账号密码");
			return;
		}
		// 发送一个网络请求 去请求网络数据
		mController.sendAsyncMessage(IdiyMessage.LOGIN_ACTION, name, pwd);
	}

	public void registClick(View v) {
		ActivityUtil.start(this, RegistActivity.class, false);
	}

	@Override
	protected void initController() {
		mController = new UserController(this);
		mController.setIModeChangeListener(this);
	}

	@Override
	protected void initUI() {
		mNameEt = (EditText) findViewById(R.id.name_et);
		mPwdEt = (EditText) findViewById(R.id.pwd_et);
		// 2.如果数据库表里面有数据 应该读取账号密码出来
		mController.sendAsyncMessage(IdiyMessage.GET_USER_ACTION, 0);
	}

}
