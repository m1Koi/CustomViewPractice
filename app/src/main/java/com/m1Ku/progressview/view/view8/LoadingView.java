package com.m1Ku.progressview.view.view8;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.m1Ku.progressview.R;
import com.m1Ku.progressview.Utils;

/**
 * Author: m1Ku
 * Email: howx172@163.com
 * Create Time: 2017/10/19
 * Description: 加载动画效果
 */

public class LoadingView extends RelativeLayout {

    private CircleView mLeftView, mMiddleView, mRightView;
    private int translationDistance = 20;
    private long mDuration = 500;
    private boolean isStopAnimate = false;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        translationDistance = Utils.dp2px(getContext(), translationDistance);
        mLeftView = getCircleView();
        mLeftView.changeColor(R.color.circle_color);
        mMiddleView = getCircleView();
        mMiddleView.changeColor(R.color.rect_color);
        mRightView = getCircleView();
        mRightView.changeColor(R.color.triangle_color);

        addView(mLeftView);
        addView(mRightView);
        addView(mMiddleView);

        post(new Runnable() {
            @Override
            public void run() {
                startExpandAnimation();

            }
        });
    }

    private void startExpandAnimation() {
        if (isStopAnimate){
            return;
        }
        Log.e("m1Ku","startExpandAnimation");
        ObjectAnimator leftAnimator = ObjectAnimator.ofFloat(mLeftView, "translationX", 0, -translationDistance);

        ObjectAnimator rightAnimator = ObjectAnimator.ofFloat(mRightView, "translationX", 0, translationDistance);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(leftAnimator, rightAnimator);
        animatorSet.setDuration(mDuration);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                startCollapseAnimation();
            }
        });

    }

    private void startCollapseAnimation() {
        if (isStopAnimate){
            return;
        }
        Log.e("m1Ku","startCollapseAnimation");
        ObjectAnimator leftAnimator = ObjectAnimator.ofFloat(mLeftView, "translationX", -translationDistance, 0);

        ObjectAnimator rightAnimator = ObjectAnimator.ofFloat(mRightView, "translationX", translationDistance, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(leftAnimator, rightAnimator);
        animatorSet.setDuration(mDuration);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //获取三个部分的颜色
                int leftColor = mLeftView.getColor();
                int middleColor = mMiddleView.getColor();
                int rightColor = mRightView.getColor();
                //轮询切换三个部分的颜色
                mMiddleView.changeColor(leftColor);
                mRightView.changeColor(middleColor);
                mLeftView.changeColor(rightColor);
                startExpandAnimation();
            }
        });
    }

    public CircleView getCircleView() {
        CircleView circleView = new CircleView(getContext());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                Utils.dp2px(getContext(), 10), Utils.dp2px(getContext(), 10));
        params.addRule(CENTER_IN_PARENT);
        circleView.setLayoutParams(params);
        return circleView;
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);

        if (visibility == View.GONE){
            isStopAnimate = true;
        }
        if (visibility == View.VISIBLE){
            isStopAnimate = false;
            startExpandAnimation();
        }
    }
}
