package com.m1Ku.progressview.view.view8;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.m1Ku.progressview.R;

/**
 * Author: m1Ku
 * Email: howx172@163.com
 * Create Time: 2017/10/19
 * Description:
 */

public class CircleView extends View {

    private Paint mPaint;
    private int mColor;

    public CircleView(Context context) {
        this(context,null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int cx = getWidth()/2;
        canvas.drawCircle(cx,cx,cx,mPaint);

    }

    public void changeColor(int color){
        mColor = color;
        mPaint.setColor(ContextCompat.getColor(getContext(),color));
        invalidate();
    }

    public int getColor(){
        return mColor;
    }
}
