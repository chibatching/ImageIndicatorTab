package com.chibatching.imgindicatortab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ImgIndicatorTab extends View implements ViewPager.OnPageChangeListener {

    private int mSelectedTextColor;
    private int mDeselectedTextColor;

    private float mTextSize;
    private Drawable mIndicator;

    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mListener;
    private int mCurrentPage;
    private int mScrollState;
    private float mPositionOffset;

    private Matrix mMatrix = new Matrix();
    private Paint mSelectedTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mDeselectedTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

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

        // Load default value
        float defaultTextSize = getResources().getDimension(R.dimen.iit_default_text_size);
        int defaultSelectedColor = getResources().getColor(R.color.iit_default_selected_text_color);
        int defaultDeselectedColor = getResources().getColor(R.color.iit_default_deselected_text_color);

        // Load style attributes
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ImgIndicatorTab, defStyle, 0);

        setSelectedTextColor(ta.getColor(R.styleable.ImgIndicatorTab_selectedTextColor, defaultSelectedColor));
        setDeselectedTextColor(ta.getColor(R.styleable.ImgIndicatorTab_deselectedTextColor, defaultDeselectedColor));
        setIndicatorDrawable(ta.getDrawable(R.styleable.ImgIndicatorTab_indicatorDrawable));
        setTextSize(ta.getDimension(R.styleable.ImgIndicatorTab_android_textSize, defaultTextSize));

        int background = ta.getResourceId(R.styleable.ImgIndicatorTab_android_background, 0);
        if (background != 0) {
            setBackgroundResource(background);
        }

        ta.recycle();
    }

    public void setViewPager(ViewPager viewPager) {
        if (mViewPager != null) {
            // Clear old listener
            mViewPager.setOnPageChangeListener(null);
        }
        mViewPager = viewPager;
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
        invalidate();
    }

    public int getSelectedTextColor() {
        return mSelectedTextColor;
    }

    public void setSelectedTextColor(int selectedTextColor) {
        this.mSelectedTextColor = selectedTextColor;
        mSelectedTextPaint.setColor(mSelectedTextColor);
    }

    public int getDeselectedTextColor() {
        return mDeselectedTextColor;
    }

    public void setDeselectedTextColor(int deselectedTextColor) {
        this.mDeselectedTextColor = deselectedTextColor;
        mDeselectedTextPaint.setColor(mDeselectedTextColor);
    }

    public Drawable getIndicatorDrawable() {
        return mIndicator;
    }

    public void setIndicatorDrawable(Drawable indicatorDrawable) {
        this.mIndicator = indicatorDrawable;
    }

    public float getTextSize() {
        return mTextSize;
    }

    public void setTextSize(float textSize) {
        mTextSize = textSize;
        mSelectedTextPaint.setTextSize(mTextSize);
        mDeselectedTextPaint.setTextSize(mTextSize);
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mListener = listener;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mScrollState = state;

        if (mListener != null) {
            mListener.onPageScrollStateChanged(state);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mCurrentPage = position;
        mPositionOffset = positionOffset;
        invalidate();

        if (mListener != null) {
            mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
            mCurrentPage = position;
            invalidate();
        }
        if (mListener != null) {
            mListener.onPageSelected(position);
        }
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

        float tabWidth = (float)(getWidth() - getPaddingLeft() - getPaddingRight()) / tabCount;
        float centerY = getPaddingTop() + (float)(getHeight() - getPaddingTop() - getPaddingBottom()) / 2;

        for (int i = 0; i < tabCount; i++) {
            float tabCenterX = (tabWidth * i) + getPaddingLeft() + tabWidth / 2;
            CharSequence text = mViewPager.getAdapter().getPageTitle(i);

            Paint textPaint;
            if (mCurrentPage == i) {
                textPaint = mSelectedTextPaint;
            } else {
                textPaint = mDeselectedTextPaint;
            }

            Paint.FontMetrics metrics = textPaint.getFontMetrics();
            float textWidth = textPaint.measureText(text, 0, text.length());
            float textX = tabCenterX - textWidth / 2;
            float textY = centerY - (metrics.ascent + metrics.descent) / 2;

            canvas.drawText(text, 0, text.length(), textX, textY, textPaint);
        }

        Bitmap indicatorBitmap = ((BitmapDrawable) mIndicator).getBitmap();
        int bitmapWidth = indicatorBitmap.getWidth();
        int bitmapHeight = indicatorBitmap.getHeight();

        float scale = 1f;
        if (bitmapWidth > tabWidth) {
            scale = tabWidth / bitmapWidth;
        }
        if (bitmapHeight * scale > getHeight()) {
            scale = getHeight() / bitmapHeight;
        }

        float tabCenterX = tabWidth * (mCurrentPage + mPositionOffset) + getPaddingLeft() + tabWidth / 2;
        float bitmapX = tabCenterX - (bitmapWidth * scale) / 2;
        float bitmapY = getHeight() - bitmapHeight * scale;

        // Set matrix for bitmap
        mMatrix.reset();
        mMatrix.postScale(scale, scale);
        mMatrix.postTranslate(bitmapX, bitmapY);

        canvas.drawBitmap(indicatorBitmap, mMatrix, mIndicatorPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int tabCount = mViewPager.getAdapter().getCount();
                float tabWidth = (float)(getWidth() - getPaddingLeft() - getPaddingRight()) / tabCount;

                for (int i = 0; i < tabCount; i++) {
                    if (event.getX() < tabWidth * (i + 1)) {
                        mViewPager.setCurrentItem(i);
                        return false;
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
