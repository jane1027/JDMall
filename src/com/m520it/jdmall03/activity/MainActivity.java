package com.m520it.jdmall03.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.fragment.CategoryFragment;
import com.m520it.jdmall03.fragment.HomeFragment;
import com.m520it.jdmall03.fragment.MyJDFragment;
import com.m520it.jdmall03.fragment.ShopcarFragment;
import com.m520it.jdmall03.listener.IBottomBarClickListener;
import com.m520it.jdmall03.ui.BottomBar;

public class MainActivity extends BaseActivity implements
		IBottomBarClickListener {

	private BottomBar mBottomBar;
	private FragmentManager mFragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mFragmentManager = getSupportFragmentManager();
		initUI();
	}

	@Override
	protected void initUI() {
		// 1.��ʼ���ײ���
		mBottomBar = (BottomBar) findViewById(R.id.bottom_bar);
		mBottomBar.setIBottomBarClickListener(this);
		// ��ʼ��������Fragment
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		transaction.replace(R.id.top_bar, new HomeFragment());
		transaction.commit();
	}

//	�л�Fragment
	@Override
	public void onItemClick(int action) {
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		switch (action) {
			case R.id.frag_main_ll:
				transaction.replace(R.id.top_bar, new HomeFragment());
				break;
			case R.id.frag_category_ll:
				transaction.replace(R.id.top_bar, new CategoryFragment());
				break;
			case R.id.frag_shopcar_ll:
				transaction.replace(R.id.top_bar, new ShopcarFragment());
				break;
			case R.id.frag_mine_ll:
				transaction.replace(R.id.top_bar, new MyJDFragment());
				break;
		}
		transaction.commit();
	}

}
