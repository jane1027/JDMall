package com.m520it.jdmall03.ui.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.m520it.jdmall03.R;
import com.m520it.jdmall03.listener.IProductSortChanegListener;

/**
 * 商品搜索的弹出框
 */
public class ProductSortPopWindow extends IPopupWindowProtocal implements
		OnClickListener {

	private IProductSortChanegListener mListener;

	public ProductSortPopWindow(Context c) {
		super(c);
	}

	public void setListener(IProductSortChanegListener listener) {
		mListener = listener;
	}

	@Override
	protected void initUI() {
		View contentView = LayoutInflater.from(mContext).inflate(
				R.layout.product_sort_pop_view, null, false);
		contentView.findViewById(R.id.left_v).setOnClickListener(this);

		contentView.findViewById(R.id.all_sort).setOnClickListener(this);
		contentView.findViewById(R.id.new_sort).setOnClickListener(this);
		contentView.findViewById(R.id.comment_sort).setOnClickListener(this);
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
			mPopWindow.showAsDropDown(anchor, 0, 0);
		}
	}

	@Override
	public void onClick(View v) {
		if (mListener != null) {
			switch (v.getId()) {
				case R.id.all_sort:
					mListener.onSortChanged(IProductSortChanegListener.ALLSORT);
					break;
				case R.id.new_sort:
					mListener.onSortChanged(IProductSortChanegListener.NEWSSORT);
					break;
				case R.id.comment_sort:
					mListener.onSortChanged(IProductSortChanegListener.COMMENTSORT);
					break;
			}
		}
		onDismiss();
	}

}
