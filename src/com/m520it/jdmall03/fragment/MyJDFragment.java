package com.m520it.jdmall03.fragment;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.m520it.jdmall03.JDApplication;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.activity.LoginActivity;
import com.m520it.jdmall03.bean.RLoginResult;
import com.m520it.jdmall03.cons.IdiyMessage;
import com.m520it.jdmall03.controller.UserController;
import com.m520it.jdmall03.util.ActivityUtil;

public class MyJDFragment extends BaseFragment implements OnClickListener {

	private TextView mUserNameTv;
	private TextView mUserLevelTv;
	private TextView mWaitPayTv;
	private TextView mWaitReciveTv;

	@Override
	protected void handlerMessage(Message msg) {
		switch (msg.what) {
		case IdiyMessage.CLEAR_USER_ACTION_RESULT:
			ActivityUtil.start(getActivity(), LoginActivity.class, true);
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_myjd, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initController();
		initUI();
	}

	@Override
	protected void initController() {
		mController = new UserController(getActivity());
		mController.setIModeChangeListener(this);
	}

	@Override
	protected void initUI() {
		getActivity().findViewById(R.id.logout_btn).setOnClickListener(this);
//		1.找到所有的用户控件
		mUserNameTv =(TextView) getActivity().findViewById(R.id.user_name_tv);
		mUserLevelTv =(TextView) getActivity().findViewById(R.id.user_level_tv);
		mWaitPayTv =(TextView) getActivity().findViewById(R.id.wait_pay_tv);
		mWaitReciveTv =(TextView) getActivity().findViewById(R.id.wait_receive_tv);
//		2.执行一个后台请求更新控件
		JDApplication applicationInfo = (JDApplication) getActivity().getApplication();
		RLoginResult mRLoginResult = applicationInfo.mRLoginResult;
		mUserNameTv.setText(mRLoginResult.getUserName());
		initUserLevel(mRLoginResult);
		mWaitPayTv.setText(mRLoginResult.getWaitPayCount()+"");
		mWaitReciveTv.setText(mRLoginResult.getWaitReceiveCount()+"");
	}

	private void initUserLevel(RLoginResult mRLoginResult) {
		String text="";
		switch (mRLoginResult.getUserLevel()) {
//			1注册会员2铜牌会员3银牌会员4金牌会员5钻石会员
			case 1:
				text="注册会员";
				break;
			case 2:
				text="铜牌会员";
				break;
			case 3:
				text="银牌会员";
				break;
			case 4:
				text="金牌会员";
				break;
			case 5:
				text="钻石会员";
				break;
		}
		mUserLevelTv.setText(text);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.logout_btn:
			mController.sendAsyncMessage(IdiyMessage.CLEAR_USER_ACTION, 0);
			break;
		}
	}

}
