package com.m1Ku.progressview.view.view2;

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
 * Description: RatingBar评分条
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
        //高度设置为 星星的高度
        int height = mNormalStar.getHeight();
        //宽度设置为 星星总宽度 + 间隔
        int width = mNormalStar.getWidth() * mStarNum + mStarPadding * (mStarNum - 1);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < mStarNum; i++) {
            int x = (mNormalStar.getWidth() + mStarPadding) * i;
            if (mCurrentStarPos > i) {
                //绘制选中
                canvas.drawBitmap(mSelectStar, x, 0, mPaint);
            } else {
                //绘制默认
                canvas.drawBitmap(mNormalStar, x, 0, mPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                //触摸位置
                float dx = event.getX();
                //计算触摸的级别
                int currentStarPos = (int) (dx / (mNormalStar.getWidth() + mStarPadding) + 1);
                if (currentStarPos > mStarNum) {
                    currentStarPos = mStarNum;
                }
                if (currentStarPos <= 0) {
                    mCurrentStarPos = 1;
                }
                //如果滑动级别未发生变化，return，不重绘
                if (currentStarPos == mCurrentStarPos) {
                    return true;
                }
                mCurrentStarPos = currentStarPos;
                invalidate();
                //回调当前选中的级别
                onRateSelectedListener.onRateSelect(mCurrentStarPos);
                break;
        }
        return true;
    }

    /**
     * 初始化级别
     * @param rate
     */
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
