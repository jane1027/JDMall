package com.m520it.jdmall03.fragment;

import java.util.List;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.adapter.TopCategoryAdapter;
import com.m520it.jdmall03.bean.RTopCategory;
import com.m520it.jdmall03.cons.IdiyMessage;
import com.m520it.jdmall03.controller.CategoryController;
import com.m520it.jdmall03.ui.SubCategoryView;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CategoryFragment extends BaseFragment implements OnItemClickListener {

	private ListView mTopCategoryLv;
	private TopCategoryAdapter mTopCategoryAdapter;
	private SubCategoryView mSubCategoryView;

	@Override
	protected void handlerMessage(Message msg) {
		switch (msg.what) {
			case IdiyMessage.TOPCATEGORY_ACTION_RESULT:
				handleTopCategory((List<RTopCategory>) msg.obj);
				break;
		}
	}
	
	private void handleTopCategory(List<RTopCategory> datas){
		mTopCategoryAdapter.setDatas(datas);
		mTopCategoryAdapter.notifyDataSetChanged();
		mTopCategoryLv.performItemClick(null, 0, 0);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_category, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initController();
		initUI();
		mController.sendAsyncMessage(IdiyMessage.TOPCATEGORY_ACTION, 0);
	}

	@Override
	protected void initController() {
		mController=new CategoryController(getActivity());
		mController.setIModeChangeListener(this);
	}

	@Override
	protected void initUI() {
		mTopCategoryLv = (ListView) getActivity().findViewById(R.id.top_lv);
		mTopCategoryAdapter = new TopCategoryAdapter(getActivity());
		mTopCategoryLv.setAdapter(mTopCategoryAdapter);
		mTopCategoryLv.setOnItemClickListener(this);
		
		mSubCategoryView=(SubCategoryView)getActivity().findViewById(R.id.subcategory);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
//		1.记录当前哪个位置被点击了
		mTopCategoryAdapter.mCurrentTabPosition=position;
//		2.刷新列表
		mTopCategoryAdapter.notifyDataSetChanged();
//		将数据添加到SubCategory
		RTopCategory topCategoryBean=(RTopCategory) mTopCategoryAdapter.getItem(position);
//		点击左边的列表 告诉右边的View说 你可以刷新界面
		mSubCategoryView.onShow(topCategoryBean);
	}
	
}
