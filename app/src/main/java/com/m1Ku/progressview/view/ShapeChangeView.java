package com.m1Ku.progressview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author: m1Ku
 * Email: howx172@163.com
 * Create Time: 2017/9/22
 * Description: 自动切换形状的View
 */

public class ShapeChangeView extends View {

    //初始形状
    private Shape mCurShape = Shape.CIRCLE;
    private Paint mPaint;
    Path mPath;

    /**
     * 定义形状的枚举类型
     */
    private enum Shape {
        CIRCLE,
        RECTANGLE,
        TRIANGLE,
    }

    public ShapeChangeView(Context context) {
        this(context, null);
    }

    public ShapeChangeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeChangeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int center = getWidth() / 2;
        switch (mCurShape) {
            case CIRCLE:
                mPaint.setColor(Color.BLUE);
                canvas.drawCircle(center, center, center, mPaint);
                break;
            case RECTANGLE:
                mPaint.setColor(Color.BLACK);
                canvas.drawRect(0, 0, getRight(), getBottom(), mPaint);
                break;
            case TRIANGLE:
                mPaint.setColor(Color.RED);
                //指定path的起点
                mPath.moveTo(getWidth() / 2, 0);
                mPath.lineTo(0, (float) (getWidth() / 2 * Math.sqrt(3)));
                mPath.lineTo(getWidth(), (float) (getWidth() / 2 * Math.sqrt(3)));
                canvas.drawPath(mPath, mPaint);
                break;
        }
    }

    public void changeShape() {
        switch (mCurShape) {
            case CIRCLE:
                mCurShape = Shape.RECTANGLE;
                break;
            case RECTANGLE:
                mCurShape = Shape.TRIANGLE;
                break;
            case TRIANGLE:
                mCurShape = Shape.CIRCLE;
                break;
        }
        invalidate();
    }
}
