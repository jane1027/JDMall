package com.m520it.jdmall03.ui;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.image.SmartImageView;
import com.m520it.jdmall03.R;
import com.m520it.jdmall03.activity.ProductListActivity;
import com.m520it.jdmall03.bean.RSubCategory;
import com.m520it.jdmall03.bean.RTopCategory;
import com.m520it.jdmall03.cons.IdiyMessage;
import com.m520it.jdmall03.cons.NetworkConst;
import com.m520it.jdmall03.controller.CategoryController;
import com.m520it.jdmall03.listener.IModeChangeListener;
import com.m520it.jdmall03.listener.IViewContainer;

public class SubCategoryView extends FlexiScrollView implements IViewContainer,
		IModeChangeListener, OnClickListener {

	private RTopCategory mTopCategoryBean;
	private LinearLayout mContainerLl;
	private CategoryController mController;
	private static final int sLinePerSize = 3;
	public static String TOPRODUCTLISTKEY="TOPRODUCTLISTKEY";
	public static String TOPCATEGORY_ID="TOPCATEGORY_ID";
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case IdiyMessage.SUBCATEGORY_ACTION_RESULT:
				handleSubCategory((List<RSubCategory>) msg.obj);
				break;
			}
		}
	};

	private void handleSubCategory(List<RSubCategory> datas) {
		// List<RSubCategory> datas�����ж��������� ��˵���ж��ٸ�2������
		for (int i = 0; i < datas.size(); i++) {
			intSecondCategoryNameTv(datas, i);
			// ���3������
			// 1.ȡ��ĳ��2������
			RSubCategory secondCategory = datas.get(i);
			// 2.��ȡ���е�3������
			List<RTopCategory> thridCategorys = JSON.parseArray(
					secondCategory.getThirdCategory(), RTopCategory.class);
			// 3.��������
			int totalSize = thridCategorys.size();
			int lines = totalSize / sLinePerSize;
			// ������û����
			int remainder = totalSize % sLinePerSize;
			lines += (remainder == 0 ? 0 : 1);
			for (int j = 0; j < lines; j++) {
				// �е�����
				LinearLayout lineLl = new LinearLayout(getContext());
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				params.setMargins(0, 10, 0, 0);
				lineLl.setLayoutParams(params);
				mContainerLl.addView(lineLl);
				// 4.����ÿһ��Ӧ��չʾ��������
				// 4.1 sLinePerSizeÿһ��������*�е�����=����3���������������
				// �����Ƿ���Ҫ��ӵ�һ������
				if (sLinePerSize * j <= totalSize - 1) {
					initThirdCategoryItem(thridCategorys, sLinePerSize * j,
							lineLl);
				}
				// �����Ƿ���Ҫ��ӵ�2������
				if (sLinePerSize * j + 1 <= totalSize - 1) {
					initThirdCategoryItem(thridCategorys, sLinePerSize * j + 1,
							lineLl);
				}
				// �����Ƿ���Ҫ��ӵ�2������
				if (sLinePerSize * j + 2 <= totalSize - 1) {
					initThirdCategoryItem(thridCategorys, sLinePerSize * j + 2,
							lineLl);
				}

			}

		}
	}

	private void initThirdCategoryItem(List<RTopCategory> thridCategorys,
			int index, LinearLayout lineLl) {
		RTopCategory thirdCategory = thridCategorys.get(index);
		// 5. ����һ��mColumnLl ��ӵ�lineLl
		LinearLayout mColumnLl = new LinearLayout(getContext());
		LinearLayout.LayoutParams mColumnParams = new LinearLayout.LayoutParams(
				(getWidth() - 16) / 3, LinearLayout.LayoutParams.WRAP_CONTENT);
		mColumnLl.setLayoutParams(mColumnParams);
		mColumnLl.setOrientation(LinearLayout.VERTICAL);
		mColumnLl.setGravity(Gravity.CENTER_HORIZONTAL);
		lineLl.addView(mColumnLl);
		mColumnLl.setOnClickListener(this);
		mColumnLl.setTag(thirdCategory);
		// 6.���е������������ͼƬ
		String imageUrlPath = NetworkConst.BASE_URL
				+ thirdCategory.getBannerUrl();
		SmartImageView bannerIv = new SmartImageView(getContext());
		LinearLayout.LayoutParams bannerIvParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				(getWidth() - 16) / 3);
		bannerIv.setLayoutParams(bannerIvParams);
		bannerIv.setImageUrl(imageUrlPath);
		mColumnLl.addView(bannerIv);
		// 7.���е�������������ı�
		TextView thridCategoryNameTv = new TextView(getContext());
		LinearLayout.LayoutParams thridCategoryNameParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		thridCategoryNameTv.setLayoutParams(thridCategoryNameParams);
		thridCategoryNameTv.setText(thirdCategory.getName());
		thridCategoryNameTv.setTextSize(15);
		mColumnLl.addView(thridCategoryNameTv);
	}

	/**
	 * ���2����������
	 */
	private void intSecondCategoryNameTv(List<RSubCategory> datas, int i) {
		TextView secondCategoryNameTv = new TextView(getContext());
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(8, 16, 0, 4);
		secondCategoryNameTv.setLayoutParams(params);
		RSubCategory bean = datas.get(i);
		secondCategoryNameTv.setText(bean.getName());
		mContainerLl.addView(secondCategoryNameTv);
	}

	public SubCategoryView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		initController();
		initUI();
	}

	private void initController() {
		mController = new CategoryController(getContext());
		mController.setIModeChangeListener(this);
	}

	private void initUI() {
		mContainerLl = (LinearLayout) findViewById(R.id.child_container_ll);
	}

	@Override
	public void onShow(Object... values) {
		mTopCategoryBean = (RTopCategory) values[0];
		// 1.�������
		mContainerLl.removeAllViews();
		// 2.�������������һ�Ź��ͼ
		initBannerIv();
		// 3.����2��3���������������
		mController.sendAsyncMessage(IdiyMessage.SUBCATEGORY_ACTION,
				mTopCategoryBean.getId());
	}

	private void initBannerIv() {
		String imageUrlPath = NetworkConst.BASE_URL
				+ mTopCategoryBean.getBannerUrl();
		SmartImageView bannerIv = new SmartImageView(getContext());
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(8, 8, 8, 8);
		bannerIv.setLayoutParams(params);
		bannerIv.setScaleType(ScaleType.FIT_XY);
		bannerIv.setImageUrl(imageUrlPath);
		mContainerLl.addView(bannerIv);
	}

	@Override
	public void onModeChanged(int action, Object... values) {
		mHandler.obtainMessage(action, values[0]).sendToTarget();
	}

	@Override
	public void onClick(View v) {
//		�����3������ ��Ҫ����3���������ݸ���Ʒ�б�ҳ
		RTopCategory thirdCategory=(RTopCategory) v.getTag();
		Intent intent=new Intent(getContext(),ProductListActivity.class);
		intent.putExtra(TOPRODUCTLISTKEY, thirdCategory.getId());
		intent.putExtra(TOPCATEGORY_ID, mTopCategoryBean.getId());
		getContext().startActivity(intent);
	}

}
