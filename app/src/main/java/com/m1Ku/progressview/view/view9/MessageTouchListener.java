package com.m1Ku.progressview.view.view9;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Author: m1Ku
 * Email: howx172@163.com
 * Create Time: 2018/3/7
 * Description:
 */

public class MessageTouchListener implements View.OnTouchListener {
    //需要拖拽爆炸的view
    private View mView;
    private WindowManager windowManager;
    private MessageBubbleView bubbleView;
    private WindowManager.LayoutParams mParams;

    public MessageTouchListener(View view, Context context) {
        this.mView = view;

        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        bubbleView = new MessageBubbleView(context);
        mParams = new WindowManager.LayoutParams();
        //背景要透明
        mParams.format = PixelFormat.TRANSPARENT;
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:

                //添加bubbleView到WindowManager中
                windowManager.addView(bubbleView, mParams);
                bubbleView.initPoint(motionEvent.getRawX(), motionEvent.getRawY());

                mView.setVisibility(View.INVISIBLE);
                break;
            case MotionEvent.ACTION_MOVE:
                bubbleView.updatePoint(motionEvent.getRawX(), motionEvent.getRawY());
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }
}
