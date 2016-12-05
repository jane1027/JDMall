package com.m520it.jdmall03.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.adapter.BrandAdapter;
import com.m520it.jdmall03.adapter.ProductListAdapter;
import com.m520it.jdmall03.bean.RBrand;
import com.m520it.jdmall03.bean.RProductList;
import com.m520it.jdmall03.bean.SProductList;
import com.m520it.jdmall03.cons.IdiyMessage;
import com.m520it.jdmall03.controller.CategoryController;
import com.m520it.jdmall03.listener.IProductSortChanegListener;
import com.m520it.jdmall03.ui.SubCategoryView;
import com.m520it.jdmall03.ui.pop.ProductSortPopWindow;
import com.m520it.jdmall03.util.FixedViewUtil;

public class ProductListActivity extends BaseActivity 
					implements OnClickListener,IProductSortChanegListener, OnItemClickListener {

	private long mTopCategoryId;//1级分类id   品牌列表的参数
	private long mCategoryId;//3级分类id		商品列表
	private GridView mBrandGv;
	private BrandAdapter mBrandAdapter;
	private TextView mAllIndicatorTv;
	private ProductSortPopWindow mProductSorPop;
	private SProductList mSendArgs;
	private TextView mSaleIndicatorTv;
	private TextView mPriceIndicatoTv;
	private TextView mJDTakeTv;
	private TextView mPayWhenReceiveTv;
	private TextView mJustHashStockTv;
	private TextView mChooseIndicatorTv;
	private DrawerLayout mDrawerLayout;
	private View mSliderView;
	private EditText mMinPriceTv;
	private EditText mMaxPriceTv;
	private ListView mProductLv;
	private ProductListAdapter mAdapter;
	public static final String TODETAILSKEY="TODETAILSKEY";
	
	@Override
	protected void handlerMessage(Message msg) {
		switch (msg.what) {
			case IdiyMessage.BRAND_ACTION_RESULT:
				handleBrands((List<RBrand>) msg.obj);
				break;
			case IdiyMessage.PRODUCT_LIST_ACTION_RESULT:
				handleProductList((List<RProductList>) msg.obj);
				break;
		}
	}
	
	private void handleProductList(List<RProductList> datas){
		mAdapter.setDatas(datas);
		mAdapter.notifyDataSetChanged();
	}
	
//	处理品牌的信息
	private void handleBrands(List<RBrand> datas){
		mBrandAdapter.setDatas(datas);
		mBrandAdapter.notifyDataSetChanged();
//		重置品牌列表的高度
		FixedViewUtil.setGridViewHeightBasedOnChildren(mBrandGv, 3);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_list);
		initData();
		initController();
		initUI();
		mController.sendAsyncMessage(IdiyMessage.BRAND_ACTION, mTopCategoryId);
//		TODO 发送了请求
		mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION, mSendArgs);
	}
	
	@Override
	protected void initController() {
		mController=new CategoryController(this);
		mController.setIModeChangeListener(this);
	}
	
	@Override
	protected void initData() {
		Intent intent = getIntent();
		mCategoryId = intent.getLongExtra(SubCategoryView.TOPRODUCTLISTKEY,0);
		mTopCategoryId = intent.getLongExtra(SubCategoryView.TOPCATEGORY_ID,0);
		if (mCategoryId==0||mTopCategoryId==0) {
			tip("数据异常");
			finish();
		}
		mSendArgs=new SProductList();
		mSendArgs.setCategoryId(mCategoryId);
	}


	@Override
	protected void initUI() {
		mDrawerLayout =(DrawerLayout) findViewById(R.id.drawerlayout);
		initMainUI();
		initSlideUI();
	}


	private void initSlideUI() {
		mSliderView =findViewById(R.id.slide_view);
		
		mJDTakeTv =(TextView) findViewById(R.id.jd_take_tv);
		mJDTakeTv.setOnClickListener(this);
		mPayWhenReceiveTv =(TextView) findViewById(R.id.paywhenreceive_tv);
		mPayWhenReceiveTv.setOnClickListener(this);
		mJustHashStockTv =(TextView) findViewById(R.id.justhasstock_tv);
		mJustHashStockTv.setOnClickListener(this);
		
		mMinPriceTv =(EditText) findViewById(R.id.minPrice_et);
		mMaxPriceTv =(EditText) findViewById(R.id.maxPrice_et);
		
		mBrandGv =(GridView) findViewById(R.id.brand_gv);
		mBrandAdapter = new BrandAdapter(this);
		mBrandGv.setAdapter(mBrandAdapter);
		mBrandGv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mBrandAdapter.mCurrentTabPosition=position;
				mBrandAdapter.notifyDataSetChanged();
//				点击 记录下品牌的id
			}
		});
	}
	
	/**
	 * 确定按钮
	 */
	public void chooseSearchClick(View v){
		//1.确定选择服务
		int deliverChoose=0;
		if (mJDTakeTv.isSelected()) {
			deliverChoose+=1;
		}
		if (mPayWhenReceiveTv.isSelected()) {
			deliverChoose+=2;
		}
		if (mJustHashStockTv.isSelected()) {
			deliverChoose+=4;
		}
		mSendArgs.setDeliverChoose(deliverChoose);
		//2.价格区间
		String minPriceStr = mMinPriceTv.getText().toString();
		String maxPriceStr = mMaxPriceTv.getText().toString();
		if (!TextUtils.isEmpty(minPriceStr)&&!TextUtils.isEmpty(maxPriceStr)) {
			double minPrice=Double.parseDouble(minPriceStr);
			double maxPrice=Double.parseDouble(maxPriceStr);
			mSendArgs.setMinPrice((int) minPrice);
			mSendArgs.setMaxPrice((int) maxPrice);
		}
		//3.选择品牌
		if (mBrandAdapter.mCurrentTabPosition!=-1) {
			//获取品牌ID
			long brandId = mBrandAdapter.getItemId(mBrandAdapter.mCurrentTabPosition);
			mSendArgs.setBrandId(brandId);
		}
		mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION, mSendArgs);
		mDrawerLayout.closeDrawer(mSliderView);
	}
	
	/**
	 * 重置按钮
	 */
	public void resetClick(View v){
		mSendArgs=new SProductList();
		mSendArgs.setCategoryId(mCategoryId);
		mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION, mSendArgs);
		mDrawerLayout.closeDrawer(mSliderView);
	}
	

	private void initMainUI() {
		mAllIndicatorTv =(TextView) findViewById(R.id.all_indicator);
		mAllIndicatorTv.setOnClickListener(this);
		mSaleIndicatorTv =(TextView) findViewById(R.id.sale_indicator);
		mSaleIndicatorTv.setOnClickListener(this);
		mPriceIndicatoTv =(TextView) findViewById(R.id.price_indicator);
		mPriceIndicatoTv.setOnClickListener(this);
		mChooseIndicatorTv =(TextView) findViewById(R.id.choose_indicator);
		mChooseIndicatorTv.setOnClickListener(this);
		
		mProductLv =(ListView) findViewById(R.id.product_lv);
		mAdapter = new ProductListAdapter(this);
		mProductLv.setAdapter(mAdapter);
		mProductLv.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.all_indicator:
				if (mProductSorPop==null) {
					mProductSorPop = new ProductSortPopWindow(this);
					mProductSorPop.setListener(this);
				}
				mProductSorPop.onShow(v);
				break;
			case R.id.sale_indicator:
				mSendArgs.setSortType(1);
				mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION, mSendArgs);
				break;
			case R.id.price_indicator:
//				排序  0-默认  1-销量 2-价格高到低  3-价格低到高
				int sortType = mSendArgs.getSortType();
				if (sortType==0||sortType==1||sortType==3) {
					mSendArgs.setSortType(2);
				}
				if (sortType==0||sortType==1||sortType==2) {
					mSendArgs.setSortType(3);
				}
				mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION, mSendArgs);
				break;
			case R.id.choose_indicator:
				mDrawerLayout.openDrawer(mSliderView);
				break;
			case R.id.jd_take_tv:
			case R.id.paywhenreceive_tv:
			case R.id.justhasstock_tv:
				v.setSelected(!v.isSelected());
				break;
		}
	}

	@Override
	public void onSortChanged(int action) {
		switch (action) {
			case IProductSortChanegListener.ALLSORT:
				mAllIndicatorTv.setText("综合");
				mSendArgs.setFilterType(1);
				mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION, mSendArgs);
				break;
			case IProductSortChanegListener.NEWSSORT:
				mAllIndicatorTv.setText("新品");
				mSendArgs.setFilterType(2);
				mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION, mSendArgs);
				break;
			case IProductSortChanegListener.COMMENTSORT:
				mAllIndicatorTv.setText("评价");
				mSendArgs.setFilterType(3);
				mController.sendAsyncMessage(IdiyMessage.PRODUCT_LIST_ACTION, mSendArgs);
				break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
//		怎么获取列表的id   getItemId
//		如果想获取  		  getItem
		long pId = mAdapter.getItemId(position);
		Intent intent=new Intent(this, ProductDetailsActivity.class);
		intent.putExtra(TODETAILSKEY, pId);
		startActivity(intent);
	}

}
