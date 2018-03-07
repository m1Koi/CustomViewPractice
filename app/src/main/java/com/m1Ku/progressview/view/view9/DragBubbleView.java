package com.m1Ku.progressview.view.view9;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.m1Ku.progressview.Utils;

/**
 * Author: m1Ku
 * Email: howx172@163.com
 * Create Time: 2018/3/6
 * Description:气泡拖拽的View
 */

public class DragBubbleView extends View {

    private PointF mDragPoint, mFixationPoint;
    private float mDragPointRadius = 15;
    private float mFixationRadius;
    //固定圆的最大半径
    private float mFixationRadiusMax = 12;
    private float mFixationRadiusMin = 5;

    private Paint mPointPaint;

    public DragBubbleView(Context context) {
        this(context, null);
    }

    public DragBubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragPoint = new PointF();
        mFixationPoint = new PointF();
        mFixationRadiusMax = Utils.dp2px(context, mFixationRadiusMax);
        mDragPointRadius = Utils.dp2px(context, mDragPointRadius);
        mFixationRadiusMin = Utils.dp2px(context, mFixationRadiusMin);
        initPaint();
    }

    private void initPaint() {
        mPointPaint = new Paint();
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setAntiAlias(true);
        mPointPaint.setDither(true);
        mPointPaint.setColor(Color.RED);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDragPoint == null || mFixationPoint == null) {
            return;
        }

        canvas.drawCircle(mDragPoint.x, mDragPoint.y, mDragPointRadius, mPointPaint);
        if (mFixationRadius > mFixationRadiusMin) {
            canvas.drawCircle(mFixationPoint.x, mFixationPoint.y, mFixationRadius, mPointPaint);
        }
        Path bezierPath = getBezierPath();
        if (bezierPath != null) {
            canvas.drawPath(bezierPath, mPointPaint);
        }
    }

    /**
     * 获取贝塞尔曲线
     *
     * @return
     */
    private Path getBezierPath() {

        double pointDistance = getDistance(mDragPoint, mFixationPoint);
        mFixationRadius = (float) (mFixationRadiusMax - pointDistance / 14);
        if (mFixationRadius < mFixationRadiusMin) {
            return null;
        }
        Path path = new Path();
        //斜率
        double tana = (mDragPoint.y - mFixationPoint.y) / (mDragPoint.x - mFixationPoint.x);
        //求角度
        double angleA = Math.atan(tana);

        //起始点p0
        float p0X = (float) (mFixationPoint.x + mFixationRadius * Math.sin(angleA));
        float p0Y = (float) (mFixationPoint.y - mFixationRadius * Math.cos(angleA));

        //结束点p1
        float p1X = (float) (mDragPoint.x + mDragPointRadius * Math.sin(angleA));
        float p1Y = (float) (mDragPoint.y - mDragPointRadius * Math.cos(angleA));

        //起始点p2
        float p2X = (float) (mDragPoint.x - mDragPointRadius * Math.sin(angleA));
        float p2Y = (float) (mDragPoint.y + mDragPointRadius * Math.cos(angleA));

        //结束点 p3
        float p3X = (float) (mFixationPoint.x - mFixationRadius * Math.sin(angleA));
        float p3Y = (float) (mFixationPoint.y + mFixationRadius * Math.cos(angleA));

        //拼装贝塞尔曲线
        path.moveTo(p0X, p0Y); //起始点
        //获取控制点(控制点是取的两个圆的中心点)
        PointF controlPoint = getControlPoint();
        //画了第一条
        path.quadTo(controlPoint.x, controlPoint.y, p1X, p1Y); //结束点

        //画第二条
        path.lineTo(p2X, p2Y);
        path.quadTo(controlPoint.x, controlPoint.y, p3X, p3Y); //结束点
        path.close();

        return path;

    }

    private double getDistance(PointF point1, PointF point2) {
        return Math.sqrt((point1.x - point2.x) * (point1.x - point2.x) + (point1.y - point2.y) * (point1.y - point2.y));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                initPoint(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                updatePoint(moveX, moveY);
                break;
        }
        invalidate();
        return true;
    }

    /**
     * 更新拖拽的点
     *
     * @param moveX
     * @param moveY
     */
    private void updatePoint(float moveX, float moveY) {
        mDragPoint.x = moveX;
        mDragPoint.y = moveY;
    }

    /**
     * 初始化点
     *
     * @param x
     * @param y
     */
    private void initPoint(float x, float y) {
        mFixationPoint.x = x;
        mFixationPoint.y = y;
        mDragPoint.x = x;
        mDragPoint.y = y;
    }

    public PointF getControlPoint() {
        return new PointF((mDragPoint.x + mFixationPoint.x) / 2, (mDragPoint.y + mFixationPoint.y) / 2);
    }
}
