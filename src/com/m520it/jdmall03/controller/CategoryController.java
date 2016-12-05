package com.m520it.jdmall03.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmall03.bean.RBrand;
import com.m520it.jdmall03.bean.RProductList;
import com.m520it.jdmall03.bean.RResult;
import com.m520it.jdmall03.bean.RSubCategory;
import com.m520it.jdmall03.bean.RTopCategory;
import com.m520it.jdmall03.bean.SProductList;
import com.m520it.jdmall03.cons.IdiyMessage;
import com.m520it.jdmall03.cons.NetworkConst;
import com.m520it.jdmall03.util.NetworkUtil;

public class CategoryController extends BaseController {

	public CategoryController(Context c) {
		super(c);
	}

	@Override
	protected void handleMessage(int action, Object... values) {
		switch (action) {
			case IdiyMessage.TOPCATEGORY_ACTION:
				mListener.onModeChanged(IdiyMessage.TOPCATEGORY_ACTION_RESULT,
						loadTopCategory());
				break;
			case IdiyMessage.SUBCATEGORY_ACTION:
				mListener.onModeChanged(IdiyMessage.SUBCATEGORY_ACTION_RESULT,
						loadSubCategory((Long) values[0]));
				break;
			case IdiyMessage.BRAND_ACTION:
				mListener.onModeChanged(IdiyMessage.BRAND_ACTION_RESULT, 
						loadBrand((Long) values[0]));
				break;
			case IdiyMessage.PRODUCT_LIST_ACTION:
				mListener.onModeChanged(IdiyMessage.PRODUCT_LIST_ACTION_RESULT, 
						loadProductList((SProductList) values[0]));
				break;
		}
	}
	
	private List<RProductList> loadProductList(SProductList sendArgs){
		Log.v("520it", sendArgs.toString());
		HashMap<String, String> params = initProductListParams(sendArgs);
		String jsonStr = NetworkUtil.doPost(NetworkConst.PRODUCTLIST_URL, params);
		RResult resultBean = JSON.parseObject(jsonStr,RResult.class);
		if (resultBean.isSuccess()) {
			try {
				JSONObject jsonObj = new JSONObject(resultBean.getResult());
				String rowsJson = jsonObj.getString("rows");
				return JSON.parseArray(rowsJson,RProductList.class);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return new ArrayList<RProductList>();
	}

	private HashMap<String, String> initProductListParams(SProductList sendArgs) {
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("categoryId", sendArgs.getCategoryId()+"");
		params.put("filterType", sendArgs.getFilterType()+"");
		if (sendArgs.getSortType()!=0) {
			params.put("sortType", sendArgs.getSortType()+"");
		}
		params.put("deliverChoose", sendArgs.getDeliverChoose()+"");
		if (sendArgs.getMinPrice()!=0&&sendArgs.getMaxPrice()!=0) {
			params.put("minPrice", sendArgs.getMinPrice()+"");
			params.put("maxPrice", sendArgs.getMaxPrice()+"");
		}
		if (sendArgs.getBrandId()!=0) {
			params.put("brandId", sendArgs.getBrandId()+"");
		}
		return params;
	}
	
	private List<RBrand> loadBrand(long topCategoryId){
		String jsonStr = NetworkUtil.doGet(NetworkConst.BRAND_URL+"?categoryId="+topCategoryId);
		RResult resultBean = JSON.parseObject(jsonStr,RResult.class);
		if (resultBean.isSuccess()) {
			return JSON.parseArray(resultBean.getResult(),RBrand.class);
		}
		return new ArrayList<RBrand>();
	}
	
	private List<RSubCategory> loadSubCategory(long parentId) {
		String jsonStr = NetworkUtil.doGet(NetworkConst.CATEGORY_URL+"?parentId="+parentId);
		RResult resultBean = JSON.parseObject(jsonStr, RResult.class);
		if (resultBean.isSuccess()) {
//			Log.v("520it",resultBean.getResult());
			return JSON.parseArray(resultBean.getResult(),RSubCategory.class);
		}
		return new ArrayList<RSubCategory>();
	}

	private List<RTopCategory> loadTopCategory() {
		String jsonStr = NetworkUtil.doGet(NetworkConst.CATEGORY_URL);
		RResult resultBean = JSON.parseObject(jsonStr, RResult.class);
		if (resultBean.isSuccess()) {
			return JSON.parseArray(resultBean.getResult(), RTopCategory.class);
		}
		return new ArrayList<RTopCategory>();
	}

}
