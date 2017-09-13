package com.m1Ku.progressview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.m1Ku.progressview.R;

/**
 * Author: m1Ku
 * Email: howx172@163.com
 * Create Time: 2017/9/13
 * Description:
 */

public class RatingBar extends View {

    private Bitmap mNormalStar;
    private Bitmap mSelectStar;
    private int mStarPadding;
    private int mStarNum;
    private Paint mPaint;
    private int mCurrentStarPos = 1;


    public RatingBar(Context context) {
        this(context, null);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        int normalRes = typedArray.getResourceId(R.styleable.RatingBar_normalStar, 0);
        mNormalStar = BitmapFactory.decodeResource(getResources(), normalRes);

        int selectRes = typedArray.getResourceId(R.styleable.RatingBar_selectStar, 0);
        mSelectStar = BitmapFactory.decodeResource(getResources(), selectRes);

        mStarNum = typedArray.getInt(R.styleable.RatingBar_starNum, 5);
        mStarPadding = typedArray.getDimensionPixelOffset(R.styleable.RatingBar_starPadding, 0);

        typedArray.recycle();
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = mNormalStar.getHeight();
        int width = mNormalStar.getWidth() * mStarNum + mStarPadding * (mStarNum - 1);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < mStarNum; i++) {
            int x = (mNormalStar.getWidth() + mStarPadding) * i;
            if (mCurrentStarPos > i) {
                canvas.drawBitmap(mSelectStar, x, 0, mPaint);
            } else {
                canvas.drawBitmap(mNormalStar, x, 0, mPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                float dx = event.getX();
                int currentStarPos = (int) (dx / (mNormalStar.getWidth() + mStarPadding) + 1);
                if (currentStarPos > mStarNum) {
                    currentStarPos = mStarNum;
                }
                if (currentStarPos <= 0) {
                    mCurrentStarPos = 1;
                }
                if (currentStarPos == mCurrentStarPos) {
                    return true;
                }
                mCurrentStarPos = currentStarPos;
                invalidate();
                onRateSelectedListener.onRateSelect(mCurrentStarPos);
                break;
        }
        return true;
    }

    public void setInitRate(int rate) {
        if (rate > mStarNum) {
            throw new RuntimeException("初始化的Rate值要小于最大值" + mStarNum);
        }
        this.mCurrentStarPos = rate;
        invalidate();
    }

    public OnRateSelectedListener onRateSelectedListener;

    public void setonRateSelectedListener(OnRateSelectedListener listener) {
        this.onRateSelectedListener = listener;
    }

    public interface OnRateSelectedListener {
        void onRateSelect(int rate);
    }
}
