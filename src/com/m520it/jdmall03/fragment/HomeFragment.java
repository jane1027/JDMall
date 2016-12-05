package com.m520it.jdmall03.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.loopj.android.image.SmartImageView;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.activity.ProductDetailsActivity;
import com.m520it.jdmall03.activity.ProductListActivity;
import com.m520it.jdmall03.adapter.RecommendAdapter;
import com.m520it.jdmall03.adapter.SecondKillAdapter;
import com.m520it.jdmall03.bean.Banner;
import com.m520it.jdmall03.bean.RRecommndProduct;
import com.m520it.jdmall03.bean.RSecondKill;
import com.m520it.jdmall03.cons.IdiyMessage;
import com.m520it.jdmall03.cons.NetworkConst;
import com.m520it.jdmall03.controller.HomeController;
import com.m520it.jdmall03.ui.HorizontalListView;
import com.m520it.jdmall03.util.FixedViewUtil;

public class HomeFragment extends BaseFragment 
			implements OnPageChangeListener, OnItemClickListener {

	private ViewPager mAdVp;
	private ADAdapter mAdAdapter;
	private LinearLayout mIndicatorLl;
	private Timer mTimer;
	private HorizontalListView mSecondKillLv;
	private SecondKillAdapter mSecondKillAdapter;
	private GridView mRecommendGv;
	private RecommendAdapter mRecommendAdapter;

	@Override
	protected void handlerMessage(Message msg) {
		switch (msg.what) {
			case IdiyMessage.GET_BANNER_ACTION_RESULT:
				handleBannerResult((List<Banner>) msg.obj);
				break;
			case IdiyMessage.SECOND_KILL_ACTION_RESULT:
				handleSecondKillResult((List<RSecondKill>) msg.obj);
				break;
			case IdiyMessage.RECOMMEND_PRODUCT_ACTION_RESULT:
				handleRecommendProductResult((List<RRecommndProduct>) msg.obj);
				break;
		}
	}
	
	private void handleRecommendProductResult(List<RRecommndProduct> datas){
		mRecommendAdapter.setDatas(datas);
		mRecommendAdapter.notifyDataSetChanged();
//		item已经有了 接下来就是计算高度的问题
		FixedViewUtil.setGridViewHeightBasedOnChildren(mRecommendGv, 2);
	}
	
	private void handleSecondKillResult(List<RSecondKill> datas){
		mSecondKillAdapter.setDatas(datas);
		mSecondKillAdapter.notifyDataSetChanged();
	}
	
	private void handleBannerResult(final List<Banner> datas){
		if (datas.size()!=0) {
			mAdAdapter.setDatas(datas);
			mAdAdapter.notifyDataSetChanged();
			//设置指示器
			initBannerIndicator(datas);
			//启动一个定时器
			initBannerTimer(datas);
		}
	}

	private void initBannerTimer(final List<Banner> datas) {
		mTimer=new Timer();
		mTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				changeBannerItem(datas);
			}
		}, 3000, 3000);
	}

	private void initBannerIndicator(final List<Banner> datas) {
		for (int i = 0; i < datas.size(); i++) {
			View view=new View(getActivity());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
			params.setMargins(10,0, 0, 0);
			view.setLayoutParams(params);
			view.setBackgroundResource(R.drawable.ad_indicator_bg);
			view.setEnabled(i==0);
			mIndicatorLl.addView(view);
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mTimer!=null) {
			mTimer.cancel();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initController();
		initUI();
		mController.sendAsyncMessage(IdiyMessage.GET_BANNER_ACTION, 1);
		mController.sendAsyncMessage(IdiyMessage.SECOND_KILL_ACTION, 0);
		mController.sendAsyncMessage(IdiyMessage.RECOMMEND_PRODUCT_ACTION, 0);
	}

	@Override
	protected void initController() {
		mController = new HomeController(getActivity());
		mController.setIModeChangeListener(this);
	}

	@Override
	protected void initUI() {
		mAdVp = (ViewPager) getActivity().findViewById(R.id.ad_vp);
		mAdAdapter = new ADAdapter();
		mAdVp.setAdapter(mAdAdapter);
		mAdVp.setOnPageChangeListener(this);
		mIndicatorLl =(LinearLayout) getActivity().findViewById(R.id.ad_indicator);
//		初始化横向的ListView
		mSecondKillLv =(HorizontalListView) getActivity().findViewById(R.id.horizon_listview);
		mSecondKillAdapter = new SecondKillAdapter(getActivity());
		mSecondKillLv.setAdapter(mSecondKillAdapter);
//		猜你喜欢 推荐商品的模块
		mRecommendGv =(GridView) getActivity().findViewById(R.id.recommend_gv);
		mRecommendAdapter = new RecommendAdapter(getActivity());
		mRecommendGv.setAdapter(mRecommendAdapter);
		mRecommendGv.setOnItemClickListener(this);
	}

	public class ADAdapter extends PagerAdapter {
		
		private List<Banner> mDatas;
		private List<SmartImageView> mChildViews;
		
		@Override
		public int getCount() {
			return mDatas!=null?mDatas.size():0;
		}

		public void setDatas(List<Banner> datas) {
			mDatas=datas;
			mChildViews=new ArrayList<SmartImageView>(mDatas.size());
			for (Banner banner : mDatas) {
				SmartImageView smiv=new SmartImageView(getActivity());
//				LayoutParams 添加到哪个容器 就应该使用哪个容器的LayoutParams
				smiv.setLayoutParams(new ViewGroup.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.MATCH_PARENT));
				smiv.setScaleType(ScaleType.FIT_XY);
				smiv.setImageUrl(NetworkConst.BASE_URL+banner.getAdUrl());
				mChildViews.add(smiv);
			}
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			SmartImageView smiv = mChildViews.get(position);
			container.addView(smiv);
			return smiv;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			SmartImageView smiv = mChildViews.get(position);
			container.removeView(smiv);
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int arg0) {
//		修改指示器的样式
		int childCount = mIndicatorLl.getChildCount();
		for (int i = 0; i < childCount; i++) {
			mIndicatorLl.getChildAt(i).setEnabled(i==arg0);
		}
	}

	private void changeBannerItem(final List<Banner> datas) {
		getActivity().runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				int currentItem = mAdVp.getCurrentItem();
				currentItem++;
				if (currentItem>datas.size()-1) {
					currentItem=0;
				}
				mAdVp.setCurrentItem(currentItem);
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		long pId = mRecommendAdapter.getItemId(position);
		Intent intent=new Intent(getActivity(), ProductDetailsActivity.class);
		intent.putExtra(ProductListActivity.TODETAILSKEY, pId);
		startActivity(intent);
	}

}
