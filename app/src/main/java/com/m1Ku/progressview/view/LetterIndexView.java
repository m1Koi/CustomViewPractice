package com.m1Ku.progressview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.m1Ku.progressview.R;
import com.m1Ku.progressview.Utils;

/**
 * Author: m1Ku
 * Email: howx172@163.com
 * Create Time: 2017/9/16
 * Description:
 */

public class LetterIndexView extends View {

    private Paint mDefaultPaint, mSelectPaint;
    private int mDefaultTextColor = Color.BLACK;
    private int mSelectTextColor = Color.RED;
    private int mLetterTextSize = 16;
    private int itemHeight;
    private int mTouchIndex = -1;
    private OnLetterTouchListener onLetterTouchListener;

    private String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};

    public LetterIndexView(Context context) {
        this(context, null);
    }

    public LetterIndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterIndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LetterIndexView);
        mDefaultTextColor = ta.getColor(R.styleable.LetterIndexView_defaultTextColor, mDefaultTextColor);
        mSelectTextColor = ta.getColor(R.styleable.LetterIndexView_selectTextColor, mSelectTextColor);
        mLetterTextSize = ta.getDimensionPixelSize(R.styleable.LetterIndexView_letterTextSize, Utils.sp2px(context, mLetterTextSize));
        ta.recycle();

        mDefaultPaint = new Paint();
        mDefaultPaint.setAntiAlias(true);
        mDefaultPaint.setColor(mDefaultTextColor);
        mDefaultPaint.setTextSize(mLetterTextSize);

        mSelectPaint = new Paint();
        mSelectPaint.setAntiAlias(true);
        mSelectPaint.setColor(mSelectTextColor);
        mSelectPaint.setTextSize(mLetterTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //其中字母宽度和画笔有关，用画笔测量其宽度
        int oneLetterWidth = (int) mDefaultPaint.measureText("A");
        int width = getPaddingLeft() + getPaddingRight() + oneLetterWidth;
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width, height);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        itemHeight = (getHeight() - getPaddingBottom() - getPaddingTop()) / letters.length;
        for (int i = 0; i < letters.length; i++) {

            //每个字母x的坐标
            int x = (int) (getWidth() / 2 - mDefaultPaint.measureText(letters[i]) / 2);
            Paint.FontMetricsInt fontInt = mDefaultPaint.getFontMetricsInt();
            //字母中心位置
            int letterCenterY = itemHeight * i + itemHeight / 2 + getPaddingTop();
            int dy = (fontInt.bottom - fontInt.top) / 2 - fontInt.bottom;
            int baseLine = letterCenterY + dy;

            if (i == mTouchIndex) {
                canvas.drawText(letters[i], x, baseLine, mSelectPaint);
            } else {
                canvas.drawText(letters[i], x, baseLine, mDefaultPaint);
            }
            Log.d("m1Ku", letters[i]);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                float y = event.getY();
                int index = (int) (y / itemHeight);
                if (index < 0) {
                    index = 0;
                }
                if (index > letters.length - 1) {
                    index = letters.length - 1;
                }
                Log.d("m1Ku", "index = " + index);

                if (index == mTouchIndex) {
                    return true;
                }
                mTouchIndex = index;
                if (onLetterTouchListener != null)
                    onLetterTouchListener.onTouchIndex(letters[mTouchIndex], true);
                Log.e("m1Ku", "mTouchIndex = " + mTouchIndex);
                invalidate();

                break;
            case MotionEvent.ACTION_UP:
                if (onLetterTouchListener != null)
                    onLetterTouchListener.onTouchIndex(letters[mTouchIndex], false);
                break;
        }
        return true;
    }

    public interface OnLetterTouchListener {
        void onTouchIndex(String letter, boolean isShow);
    }

    public void setOnLetterTouchListener(OnLetterTouchListener onLetterTouchListener) {
        this.onLetterTouchListener = onLetterTouchListener;
    }


}
