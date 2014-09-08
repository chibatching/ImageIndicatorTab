package com.chibatching.ImgIndicatorTab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;

public class ImgIndicatorTab extends View implements ViewPager.OnPageChangeListener {

	private int mSelectedTextColor;
	private int mDeselectedTextColor;
	private Drawable mIndicator;

	private ViewPager mViewPager;
	private int mTouchSlop;
	private int mCurrentPage;

	public ImgIndicatorTab(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ImgIndicatorTab(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (isInEditMode()) {
			return;
		}

		// レイアウトファイルから設定値を取得
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ImgIndicatorTab);

		setSelectedTextColor(ta.getColor(R.styleable.ImgIndicatorTab_selectedTextColor, android.R.color.holo_blue_bright));
		setDeselectedTextColor(ta.getColor(R.styleable.ImgIndicatorTab_deselectedTextColor, android.R.color.darker_gray));
		setIndicatorBitmap(ta.getDrawable(R.styleable.ImgIndicatorTab_indicatorDrawable));

		Drawable background = ta.getDrawable(R.styleable.ImgIndicatorTab_android_background);
		if (background != null) {
			setBackgroundDrawable(background);
		}

		ta.recycle();

		mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(context));
	}

	public void setViewPager(ViewPager viewPager) {
		this.mViewPager = viewPager;
		mViewPager.setOnPageChangeListener(this);
		invalidate();
	}

	public void setViewPager(ViewPager viewPager, int defaultPosition) {
		setViewPager(viewPager);
		setCurrentItem(defaultPosition);
	}

	private void setCurrentItem(int position) {
		mViewPager.setCurrentItem(position);
		mCurrentPage = position;
	}

	private int getSelectedTextColor() {
		return mSelectedTextColor;
	}

	private void setSelectedTextColor(int selectedTextColor) {
		this.mSelectedTextColor = selectedTextColor;
	}

	private int getDeselectedTextColor() {
		return mDeselectedTextColor;
	}

	private void setDeselectedTextColor(int deselectedTextColor) {
		this.mDeselectedTextColor = deselectedTextColor;
	}

	private Drawable getIndicatorBitmap() {
		return mIndicator;
	}

	private void setIndicatorBitmap(Drawable indicatorDrawable) {
		this.mIndicator = indicatorDrawable;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	}
}
