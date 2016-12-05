package com.m520it.jdmall03.controller;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall03.JDApplication;
import com.m520it.jdmall03.bean.RResult;
import com.m520it.jdmall03.cons.IdiyMessage;
import com.m520it.jdmall03.cons.NetworkConst;
import com.m520it.jdmall03.db.UserDao;
import com.m520it.jdmall03.db.UserDao.UserInfo;
import com.m520it.jdmall03.util.AESUtils;
import com.m520it.jdmall03.util.NetworkUtil;

public class UserController extends BaseController {

	private UserDao mUserDao;
	protected long mUserId;
	
	public UserController(Context c) {
		super(c);
		mUserDao=new UserDao(mContext);
		initUserId(c);
	}

	private void initUserId(Context c) {
		Activity activity=(Activity) c;
		JDApplication jdApplication=(JDApplication) activity.getApplication();
		if (jdApplication.mRLoginResult!=null) {
			mUserId=jdApplication.mRLoginResult.getId();
		}
	}

	@Override
	protected void handleMessage(int action, Object... values) {
		switch (action) {
		case IdiyMessage.LOGIN_ACTION:
			// 登录的请求 doPost doGet
			RResult rResult = loginOrRegist(NetworkConst.LOGIN_URL,
					(String) values[0], (String) values[1]);
			// 跟Activity说 数据加载完毕了
			mListener.onModeChanged(IdiyMessage.LOGIN_ACTION_RESULT, rResult);
			break;
		case IdiyMessage.REGIST_ACTION:
			RResult loginOrRegist = loginOrRegist(NetworkConst.REGIST_URL, (String) values[0],
					(String) values[1]);
			mListener.onModeChanged(IdiyMessage.REGIST_ACTION_RESULT, loginOrRegist);
			break;
		case IdiyMessage.SAVE_USERTODB:
			boolean saveUser2Db = saveUser2Db((String)values[0], (String)values[1]);
			mListener.onModeChanged(IdiyMessage.SAVE_USERTODB_RESULT, saveUser2Db);
			break;
		case IdiyMessage.GET_USER_ACTION:
			UserInfo userInfo = aquireUser();
			mListener.onModeChanged(IdiyMessage.GET_USER_ACTION_RESULT, userInfo);
			break;
		case IdiyMessage.CLEAR_USER_ACTION:
			clearUser();
			mListener.onModeChanged(IdiyMessage.CLEAR_USER_ACTION_RESULT, 0);
			break;
		}
	}
	
	private void clearUser(){
		mUserDao.clearUsers();
	}
	
	private UserInfo aquireUser(){
		UserInfo userInfo = mUserDao.aquireLastestUser();
		if (userInfo!=null) {
			try {
				userInfo.name = AESUtils.decrypt(userInfo.name);
				userInfo.pwd = AESUtils.decrypt(userInfo.pwd);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return userInfo;
		}
		return null;
	}
	
	private boolean saveUser2Db(String name,String pwd){
		mUserDao.clearUsers();
//		可逆性加密
		try {
			name=AESUtils.encrypt(name);
			pwd=AESUtils.encrypt(pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mUserDao.saveUser(name, pwd);
	}

	private RResult loginOrRegist(String url, String name, String pwd) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("username", name);
		params.put("pwd", pwd);
		String jsonStr = NetworkUtil.doPost(url, params);
		return JSON.parseObject(jsonStr, RResult.class);
	}

}
