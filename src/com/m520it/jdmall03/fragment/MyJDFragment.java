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
//		1.�ҵ����е��û��ؼ�
		mUserNameTv =(TextView) getActivity().findViewById(R.id.user_name_tv);
		mUserLevelTv =(TextView) getActivity().findViewById(R.id.user_level_tv);
		mWaitPayTv =(TextView) getActivity().findViewById(R.id.wait_pay_tv);
		mWaitReciveTv =(TextView) getActivity().findViewById(R.id.wait_receive_tv);
//		2.ִ��һ����̨������¿ؼ�
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
//			1ע���Ա2ͭ�ƻ�Ա3���ƻ�Ա4���ƻ�Ա5��ʯ��Ա
			case 1:
				text="ע���Ա";
				break;
			case 2:
				text="ͭ�ƻ�Ա";
				break;
			case 3:
				text="���ƻ�Ա";
				break;
			case 4:
				text="���ƻ�Ա";
				break;
			case 5:
				text="��ʯ��Ա";
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
