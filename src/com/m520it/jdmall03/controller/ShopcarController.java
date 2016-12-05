package com.m520it.jdmall03.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall03.bean.RArea;
import com.m520it.jdmall03.bean.RReceiver;
import com.m520it.jdmall03.bean.RResult;
import com.m520it.jdmall03.bean.RShopcar;
import com.m520it.jdmall03.bean.SAddOrderParams;
import com.m520it.jdmall03.bean.SAddReceiverParams;
import com.m520it.jdmall03.cons.IdiyMessage;
import com.m520it.jdmall03.cons.NetworkConst;
import com.m520it.jdmall03.util.NetworkUtil;

public class ShopcarController extends UserController {

	public ShopcarController(Context c) {
		super(c);
	}

	@Override
	protected void handleMessage(int action, Object... values) {
		switch (action) {
		case IdiyMessage.SHOPCAR_LIST_ACTION:
			mListener.onModeChanged(IdiyMessage.SHOPCAR_LIST_ACTION_RESULT,
					loadShopcarList());
			break;
		case IdiyMessage.DELET_SHOPCAR_ACTION:
			RResult resultBean = deleteShopcar((Long) values[0]);
			mListener.onModeChanged(IdiyMessage.DELET_SHOPCAR_ACTION_RESULT,
					resultBean);
			break;
		case IdiyMessage.GET_DEFAULT_RECEIVER_ACTION:
			mListener.onModeChanged(
					IdiyMessage.GET_DEFAULT_RECEIVER_ACTION_RESULT,
					loadDefaultReceiver((Boolean) values[0]));
			break;
		case IdiyMessage.PROVINCE_ACTION:
			mListener.onModeChanged(IdiyMessage.PROVINCE_ACTION_RESULT,
					loadArea(NetworkConst.PROVINCE_URL, null));
			break;
		case IdiyMessage.CITY_ACTION:
			mListener.onModeChanged(IdiyMessage.CITY_ACTION_RESULT,
					loadArea(NetworkConst.CITY_URL, (String) values[0]));
			break;
		case IdiyMessage.AREA_ACTION:
			mListener.onModeChanged(IdiyMessage.AREA_ACTION_RESULT,
					loadArea(NetworkConst.AREA_URL, (String) values[0]));
			break;
		case IdiyMessage.ADD_RECEIVER_ACTION:
			mListener.onModeChanged(IdiyMessage.ADD_RECEIVER_ACTION_RESULT,
					addReceiver((SAddReceiverParams) values[0]));
			break;
		case IdiyMessage.CHOOSE_RECEIVER_ACTION:
			mListener.onModeChanged(IdiyMessage.CHOOSE_RECEIVER_ACTION_RESULT,
					loadReceivers());
			break;
		case IdiyMessage.ADD_ORDER_ACTION:
			mListener.onModeChanged(IdiyMessage.ADD_ORDER_ACTION_RESULT,
					addOrder((SAddOrderParams) values[0]));
			break;
		}
	}

	private RResult addOrder(SAddOrderParams paramsBean) {
		// 1 补全UserId
		paramsBean.setUserId(mUserId);
		// 2.转换对象为JSON
		String jsonParams = JSON.toJSONString(paramsBean);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("detail", jsonParams);
		String jsonStr = NetworkUtil.doPost(NetworkConst.ADDORDER_URL, params);
		return JSON.parseObject(jsonStr, RResult.class);
	}

	private List<RReceiver> loadReceivers() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", mUserId + "");
		params.put("isDefault", "false");
		String jsonStr = NetworkUtil.doPost(NetworkConst.RECEIVEADDRESS_URL,
				params);
		RResult resultBean = JSON.parseObject(jsonStr, RResult.class);
		if (resultBean.isSuccess()) {
			return JSON.parseArray(resultBean.getResult(), RReceiver.class);
		}
		return new ArrayList<RReceiver>();
	}

	private RResult addReceiver(SAddReceiverParams bean) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", mUserId + "");
		params.put("name", bean.name);
		params.put("phone", bean.phone);
		params.put("provinceCode", bean.provinceCode);
		params.put("cityCode", bean.cityCode);
		params.put("distCode", bean.distCode);
		params.put("addressDetails", bean.addressDetails);
		params.put("isDefault", bean.isDefault + "");
		String jsonStr = NetworkUtil
				.doPost(NetworkConst.ADDADDRESS_URL, params);
		return JSON.parseObject(jsonStr, RResult.class);
	}

	private List<RArea> loadArea(String urlPath, String fcode) {
		String realUrl = urlPath;
		if (fcode != null) {
			realUrl += ("?fcode=" + fcode);
		}
		String jsonStr = NetworkUtil.doGet(realUrl);
		RResult resultBean = JSON.parseObject(jsonStr, RResult.class);
		if (resultBean.isSuccess()) {
			return JSON.parseArray(resultBean.getResult(), RArea.class);
		}
		return new ArrayList<RArea>();
	}

	/**
	 * 获取默认的收货人信息
	 */
	private RReceiver loadDefaultReceiver(boolean flag) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", mUserId + "");
		params.put("isDefault", flag + "");
		String jsonStr = NetworkUtil.doPost(NetworkConst.RECEIVEADDRESS_URL,
				params);
		RResult resultBean = JSON.parseObject(jsonStr, RResult.class);
		if (resultBean.isSuccess()) {
			List<RReceiver> receivers = JSON.parseArray(resultBean.getResult(),
					RReceiver.class);
			if (receivers.size() > 0) {
				return receivers.get(0);
			}
		}
		return null;
	}

	private RResult deleteShopcar(long shopcarId) {
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("userId", mUserId + "");
		param.put("id", shopcarId + "");
		String jsonStr = NetworkUtil.doPost(NetworkConst.DELSHOPCAR_URL, param);
		return JSON.parseObject(jsonStr, RResult.class);
	}

	private List<RShopcar> loadShopcarList() {
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("userId", mUserId + "");
		String jsonStr = NetworkUtil.doPost(NetworkConst.SHOPCAR_URL, param);
		RResult resultBean = JSON.parseObject(jsonStr, RResult.class);
		if (resultBean.isSuccess()) {
			return JSON.parseArray(resultBean.getResult(), RShopcar.class);
		}
		return new ArrayList<RShopcar>();
	}

}
