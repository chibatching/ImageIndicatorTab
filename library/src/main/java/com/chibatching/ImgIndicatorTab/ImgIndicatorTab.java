package com.chibatching.imgindicatortab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
        this(context, null, 0);
    }

    public ImgIndicatorTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImgIndicatorTab(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (isInEditMode()) {
            return;
        }

        // レイアウトファイルから設定値を取得
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ImgIndicatorTab);

        setSelectedTextColor(ta.getColor(R.styleable.ImgIndicatorTab_selectedTextColor, android.R.color.primary_text_light));
        setDeselectedTextColor(ta.getColor(R.styleable.ImgIndicatorTab_deselectedTextColor, android.R.color.primary_text_light));
        setIndicatorBitmap(ta.getDrawable(R.styleable.ImgIndicatorTab_indicatorDrawable));

        int background = ta.getResourceId(R.styleable.ImgIndicatorTab_android_background, 0);
        if (background != 0) {
            setBackgroundResource(background);
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

    public void setCurrentItem(int position) {
        mViewPager.setCurrentItem(position);
        mCurrentPage = position;
    }

    private int getSelectedTextColor() {
        return mSelectedTextColor;
    }

    public void setSelectedTextColor(int selectedTextColor) {
        this.mSelectedTextColor = selectedTextColor;
    }

    private int getDeselectedTextColor() {
        return mDeselectedTextColor;
    }

    public void setDeselectedTextColor(int deselectedTextColor) {
        this.mDeselectedTextColor = deselectedTextColor;
    }

    public Drawable getIndicatorBitmap() {
        return mIndicator;
    }

    public void setIndicatorBitmap(Drawable indicatorDrawable) {
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mViewPager == null) {
            return;
        }

        int tabCount = mViewPager.getAdapter().getCount();
        if (tabCount == 0) {
            return;
        }

        if (mCurrentPage >= tabCount) {
            setCurrentItem(tabCount - 1);
        }

        int paddingLeft = getPaddingLeft();
        float tabWidth = (float)(getWidth() - paddingLeft - getPaddingRight()) / tabCount;

        float centerY = getPaddingTop() + (float)(getHeight() - getPaddingTop() - getPaddingBottom()) / 2;

        for (int i = 0; i < tabCount; i++) {
            float tabCenterX = (tabWidth * i) + paddingLeft + tabWidth / 2;
            CharSequence text = mViewPager.getAdapter().getPageTitle(i);

            Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setTextSize(30);
            textPaint.setColor(Color.BLACK);

            Paint.FontMetrics metrics = textPaint.getFontMetrics();
            float textWidth = textPaint.measureText(text, 0, text.length());
            float textX = tabCenterX - textWidth / 2;
            float textY = centerY - (metrics.ascent + metrics.descent) / 2;

            canvas.drawText(text, 0, text.length(), textX, textY, textPaint);
        }
    }
}
