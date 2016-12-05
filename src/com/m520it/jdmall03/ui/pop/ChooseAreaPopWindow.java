package com.m520it.jdmall03.ui.pop;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.RArea;
import com.m520it.jdmall03.cons.IdiyMessage;
import com.m520it.jdmall03.controller.ShopcarController;
import com.m520it.jdmall03.listener.IAreaChangeListener;
import com.m520it.jdmall03.listener.IModeChangeListener;

/**
 * 商品搜索的弹出框
 */
public class ChooseAreaPopWindow extends IPopupWindowProtocal implements
		OnClickListener, IModeChangeListener {

	private ListView mProvinceLv;
	private ListView mCityLv;
	private ListView mAreaLv;
	private ArrayAdapter<String> mProvinceAdapter;
	private ShopcarController mController;
	private List<RArea> mProvinceDatas;
	private List<RArea> mCityDatas;
	private ArrayAdapter<String> mCityAdapter;
	private List<RArea> mAreaDatas;
	private ArrayAdapter<String> mAreaAdapter;
	private RArea mProvinceData;//山西
	private RArea mCityData;//阳泉
	private RArea mAreaData;//南郊区
	
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case IdiyMessage.PROVINCE_ACTION_RESULT:
					handleProvince((List<RArea>) msg.obj);
					break;
				case IdiyMessage.CITY_ACTION_RESULT:
					handleCity((List<RArea>) msg.obj);
					break;
				case IdiyMessage.AREA_ACTION_RESULT:
					handleArea((List<RArea>) msg.obj);
					break;
			}
		}
	};
	private IAreaChangeListener mListener;

	private void handleProvince(List<RArea> datas) {
		mProvinceDatas = datas;
		mProvinceAdapter = new ArrayAdapter<String>(mContext,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				initShowData(datas));
		mProvinceLv.setAdapter(mProvinceAdapter);
	}

	private void handleCity(List<RArea> datas) {
		mCityDatas = datas;
		mCityAdapter = new ArrayAdapter<String>(mContext,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				initShowData(datas));
		mCityLv.setAdapter(mCityAdapter);
	}
	
	protected void handleArea(List<RArea> datas) {
		mAreaDatas = datas;
		mAreaAdapter = new ArrayAdapter<String>(mContext,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				initShowData(datas));
		mAreaLv.setAdapter(mAreaAdapter);
	}

	private ArrayList<String> initShowData(List<RArea> datas) {
		ArrayList<String> showDatas = new ArrayList<String>();
		for (int i = 0; i < datas.size(); i++) {
			showDatas.add(datas.get(i).getName());
		}
		return showDatas;
	}

	public ChooseAreaPopWindow(Context c) {
		super(c);
	}

	@Override
	protected void initController() {
		mController = new ShopcarController(mContext);
		mController.setIModeChangeListener(this);
	}

	@Override
	protected void initUI() {
		View contentView = LayoutInflater.from(mContext).inflate(
				R.layout.address_pop_view, null, false);
		contentView.findViewById(R.id.left_v).setOnClickListener(this);
		contentView.findViewById(R.id.submit_tv).setOnClickListener(this);
		mProvinceLv = (ListView) contentView.findViewById(R.id.province_lv);
		mProvinceLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mProvinceData = mProvinceDatas.get(position);
				mCityData=null;
				mAreaData=null;
				//点击省份的时候 获取某个item的所有数据   code(为了注册收货人信息 后台用的)    name(为了展示)
				String fcode = mProvinceData.getCode();
				//1.清空市区列表
				handleCity(new ArrayList<RArea>());
				handleArea(new ArrayList<RArea>());
				//2.重新发送请求
				mController.sendAsyncMessage(IdiyMessage.CITY_ACTION, fcode);
			}
		});
		mController.sendAsyncMessage(IdiyMessage.PROVINCE_ACTION, 0);
		mCityLv = (ListView) contentView.findViewById(R.id.city_lv);
		mCityLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mCityData = mCityDatas.get(position);
				mAreaData=null;
				String fcode = mCityData.getCode();
				mController.sendAsyncMessage(IdiyMessage.AREA_ACTION, fcode);
			}
		});
		
		
		mAreaLv = (ListView) contentView.findViewById(R.id.dist_lv);
		mAreaLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mAreaData = mAreaDatas.get(position);
			}
		});

		mPopWindow = new PopupWindow(contentView,
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);

		// PopWindow内部的控件能否点击
		mPopWindow.setFocusable(true);
		mPopWindow.setOutsideTouchable(true);
		mPopWindow.setBackgroundDrawable(new BitmapDrawable());
		mPopWindow.update();
	}

	@Override
	public void onShow(View anchor) {
		if (mPopWindow != null) {
			mPopWindow.showAtLocation(anchor, Gravity.CENTER, 0, 0);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left_v:
			onDismiss();
			break;
		case R.id.submit_tv:
			// 当点确定的时候 要确保前面都有选择的值
			if (mProvinceData==null||
					mCityData==null||
					mAreaData==null) {
				Toast.makeText(mContext, "请选择省市区", 0).show();
				return ;
			}
			//返回Activity修改文本
			if (mListener!=null) {
				mListener.onAreaChanged(mProvinceData, mCityData, mAreaData);
			}
			onDismiss();
			break;
		}
	}

	@Override
	public void onModeChanged(int action, Object... values) {
		mHandler.obtainMessage(action, values[0]).sendToTarget();
	}

	public void setIAreaChangeListener(IAreaChangeListener listener) {
		mListener=listener;
	}

}
