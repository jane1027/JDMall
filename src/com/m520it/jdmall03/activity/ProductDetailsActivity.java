package com.m520it.jdmall03.activity;

import java.util.ArrayList;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.bean.RResult;
import com.m520it.jdmall03.cons.IdiyMessage;
import com.m520it.jdmall03.controller.ProductDetailsController;
import com.m520it.jdmall03.fragment.ProductCommentFragment;
import com.m520it.jdmall03.fragment.ProductDetailsFragment;
import com.m520it.jdmall03.fragment.ProductIntroduceFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class ProductDetailsActivity extends BaseActivity implements
		OnClickListener, OnPageChangeListener {

	public long mProductId;
	public int mBuyCount = 1;
	public String mProductVersion = "";
	private View mDetailsIndicator;
	private View mIntroduceIndicator;
	private View mCommentIndicator;
	private ViewPager mContainerVp;
	private ContainerAdapter mContainerAdapter;
	
	@Override
	protected void handlerMessage(Message msg) {
//		��ӹ��ﳵ�Ĵ���
//		IdiyMessage.ADD2SHOPCAR_ACTION_RESULT
		RResult bean=(RResult) msg.obj;
		if (bean.isSuccess()) {
			tip("��ӳɹ�");
			finish();
		}else {
			tip("���ʧ��:"+bean.getErrorMsg());
		}
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_details);
		initData();
		initController();
		initUI();
	}

	@Override
	protected void initController() {
		mController = new ProductDetailsController(this);
		mController.setIModeChangeListener(this);
	}

	@Override
	protected void initData() {
		Intent intent = getIntent();
		mProductId = intent.getLongExtra(ProductListActivity.TODETAILSKEY, 0);
		if (mProductId == 0) {
			tip("�����쳣");
			finish();
		}
	}

	@Override
	protected void initUI() {
		findViewById(R.id.introduce_ll).setOnClickListener(this);
		findViewById(R.id.details_ll).setOnClickListener(this);
		findViewById(R.id.comment_ll).setOnClickListener(this);

		mDetailsIndicator = findViewById(R.id.details_view);
		mIntroduceIndicator = findViewById(R.id.introduce_view);
		mCommentIndicator = findViewById(R.id.comment_view);

		mContainerVp = (ViewPager) findViewById(R.id.container_vp);
		mContainerAdapter = new ContainerAdapter(getSupportFragmentManager());
		mContainerVp.setAdapter(mContainerAdapter);
		mContainerVp.setOnPageChangeListener(this);
	}

	public class ContainerAdapter extends FragmentPagerAdapter {

		private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();

		public ContainerAdapter(FragmentManager fm) {
			super(fm);
			ProductIntroduceFragment introduceFragment = new ProductIntroduceFragment();
			mFragments.add(introduceFragment);
			mFragments.add(new ProductDetailsFragment());
			mFragments.add(new ProductCommentFragment());
		}

		@Override
		public Fragment getItem(int arg0) {
			return mFragments.get(arg0);
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

	}

	@Override
	public void onClick(View v) {
		defaultIndicator();
		switch (v.getId()) {
		case R.id.introduce_ll:// ��Ʒ���
			mIntroduceIndicator.setVisibility(View.VISIBLE);
			mContainerVp.setCurrentItem(0);
			break;
		case R.id.details_ll:// ��Ʒ����
			mDetailsIndicator.setVisibility(View.VISIBLE);
			mContainerVp.setCurrentItem(1);
			break;
		case R.id.comment_ll:// ��Ʒ����
			mCommentIndicator.setVisibility(View.VISIBLE);
			mContainerVp.setCurrentItem(2);
			break;
		}
	}

	private void defaultIndicator() {
		mDetailsIndicator.setVisibility(View.INVISIBLE);
		mIntroduceIndicator.setVisibility(View.INVISIBLE);
		mCommentIndicator.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		defaultIndicator();
		switch (position) {
		case 0:
			mIntroduceIndicator.setVisibility(View.VISIBLE);
			break;
		case 1:
			mDetailsIndicator.setVisibility(View.VISIBLE);
			break;
		case 2:
			mCommentIndicator.setVisibility(View.VISIBLE);
			break;
		}
	}

	public void add2ShopCar(View v) {
		// 1.��Ʒid
		// 2.���������
		// 3.��Ʒ���ͺ�
		if (mBuyCount == 0) {
			tip("�����ù��������");
			return;
		}
		if (mProductVersion.equals("")) {
			tip("�����ù�����ͺ�");
			return;
		}
		// ����������ҪmController��������
		mController.sendAsyncMessage(IdiyMessage.ADD2SHOPCAR_ACTION,
				mProductId, mBuyCount, mProductVersion);
	}

}
