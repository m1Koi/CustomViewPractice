package com.m1Ku.progressview.view.view4;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.m1Ku.progressview.R;
import com.m1Ku.progressview.Utils;

/**
 * Author: m1Ku
 * Email: howx172@163.com
 * Create Time: 2017/9/23
 * Description:
 */

public class LoadingView extends LinearLayout {

    private ShapeChangeView mShapeChangeView;
    private View mShadowView;
    private int translateDistance;
    private long animateTime = 800;
    private boolean isStopAnimation = false;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        translateDistance = Utils.dp2px(getContext(), 88);
        initLayout();
    }

    /**
     * 加载布局
     */
    private void initLayout() {

        inflate(getContext(), R.layout.layout_loading_view, this);
        mShapeChangeView = findViewById(R.id.shapeChangeView);
        mShadowView = findViewById(R.id.shadowView);

        post(new Runnable() {
            @Override
            public void run() {
                startFallAnimation();
            }
        });
    }

    /**
     * 分析：1.布局分为三块，分别为最上面ShapeChangeView,中间的阴影mShadowView,和下面的TextView
     * 2.ShapeChangeView 和 mShadowView有动画效果
     * 3.ShapeChangeView的动画效果是，下落和弹起。而当ShapeChangeView下落时伴随着阴影mShadowView
     * 的变小;弹起时阴影变大，如此往复
     * 4.当ShapeChangeView弹起时，为其添加旋转动画
     */

    /**
     * 定义 下落动画 和 阴影缩小动画
     */
    private void startFallAnimation() {
        if (isStopAnimation) {
            return;
        }
        //下落的动画
        ObjectAnimator translateAnimation = ObjectAnimator.ofFloat(mShapeChangeView, "translationY", 0, translateDistance);
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        //阴影缩小动画
        ObjectAnimator scaleAnimation = ObjectAnimator.ofFloat(mShadowView, "scaleX", 1f, 0.4f);
        //旋转动画

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateAnimation, scaleAnimation);
        animatorSet.setDuration(animateTime);
        animatorSet.setInterpolator(new AccelerateInterpolator());

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mShapeChangeView.changeShape();
                startUpAnimation();
            }
        });
        animatorSet.start();
    }

    private void startUpAnimation() {
        if (isStopAnimation) {
            return;
        }
        //弹起的动画
        ObjectAnimator translateAnimation = ObjectAnimator.ofFloat(mShapeChangeView, "translationY", translateDistance, 0);
        //阴影放大的动画
        ObjectAnimator scaleAnimation = ObjectAnimator.ofFloat(mShadowView, "scaleX", 0.4f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateAnimation, scaleAnimation);
        animatorSet.setDuration(animateTime);
        animatorSet.setInterpolator(new DecelerateInterpolator());

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                //弹起时旋转
                startRotateAnimation();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startFallAnimation();
            }
        });
        animatorSet.start();

    }

    private void startRotateAnimation() {
        if (isStopAnimation) {
            return;
        }
        ObjectAnimator rotateAnimation = null;
        switch (mShapeChangeView.getCurShape()) {
            case CIRCLE:
            case RECTANGLE:
                rotateAnimation = ObjectAnimator.ofFloat(mShapeChangeView, "rotation", 0, 180);
                break;
            case TRIANGLE:
                rotateAnimation = ObjectAnimator.ofFloat(mShapeChangeView, "rotation", 0, -120);
                break;
        }
        rotateAnimation.setDuration(animateTime);
        rotateAnimation.start();

    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(View.INVISIBLE);
        //清楚View的动画
        mShapeChangeView.clearAnimation();
        mShadowView.clearAnimation();

        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            //将view从父布局移除
            parent.removeView(this);
            //移除自身所有的view
            removeAllViews();
        }
        isStopAnimation = true;
    }
}
