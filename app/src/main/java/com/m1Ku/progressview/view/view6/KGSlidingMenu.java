package com.m1Ku.progressview.view.view6;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;

import com.m1Ku.progressview.R;
import com.m1Ku.progressview.Utils;

/**
 * Author: m1Ku
 * Email: howx172@163.com
 * Create Time: 2017/10/16
 * Description:仿酷狗侧滑栏
 */

public class KGSlidingMenu extends HorizontalScrollView {

    private int menuRightMargin = 30;
    private ViewGroup mMenuView;
    private ViewGroup mContentView;
    private GestureDetector mGestureDetector;
    private boolean isMenuOpen = false;
    private boolean isIntercept = false;

    public KGSlidingMenu(Context context) {
        this(context, null);
    }

    public KGSlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KGSlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mGestureDetector = new GestureDetector(getContext(), onGestureListener);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.KGSlidingMenu);
        menuRightMargin = typedArray.getDimensionPixelOffset(R.styleable.KGSlidingMenu_rightMenuMargin, Utils.dp2px(getContext(), menuRightMargin));

        typedArray.recycle();
    }

    GestureDetector.SimpleOnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() {

        /**
         * 快速滑动方法,只要快速滑动就回调
         * @param motionEvent
         * @param motionEvent1
         * @param v
         * @param v1
         * @return
         */
        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            Log.e("m1Ku", "速率 = " + v);
            if (isMenuOpen) {
                //关闭菜单
                if (v < 0) {
                    closeMenu();
                    return true;
                }

            } else {
                //打开菜单
                if (v > 0) {
                    openMenu();
                    return true;
                }

            }
            return false;
        }
    };


    /**
     * 布局加载完成调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ViewGroup containerView = (ViewGroup) getChildAt(0);

        int count = containerView.getChildCount();
        if (count != 2) {
            throw new RuntimeException("there should be two child of the container");
        }
        /*********指定菜单和内容部分的宽度****/
        //1.菜单栏
        mMenuView = (ViewGroup) containerView.getChildAt(0);
        ViewGroup.LayoutParams menuParams = mMenuView.getLayoutParams();
        menuParams.width = getScreenWidth(getContext()) - menuRightMargin;
        mMenuView.setLayoutParams(menuParams);

        //2.内容部分
        mContentView = (ViewGroup) containerView.getChildAt(1);
        ViewGroup.LayoutParams contentParams = mContentView.getLayoutParams();
        contentParams.width = getScreenWidth(getContext());
        mContentView.setLayoutParams(contentParams);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //如果拦截事件了，不执行自己的onTouchEvent
        if (isIntercept){
            return true;
        }
        if (mGestureDetector.onTouchEvent(ev)) {
            return true;
        }
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            //手指抬起时，判断scrollView滑动的距离，如果大于菜单宽度一半就关闭菜单，否则打开菜单
            int scrollX = getScrollX();
            if (scrollX > mMenuView.getMeasuredWidth() / 2) {
                closeMenu();
            } else {
                openMenu();
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        isIntercept = false;
        if (isMenuOpen) {
            if (ev.getX() > mMenuView.getMeasuredWidth()) {
                isIntercept = true;
                //关闭菜单
                closeMenu();
                //拦截事件，子view不响应事件
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 打开菜单，即滚动到初始位置
     */
    private void openMenu() {
        smoothScrollTo(0, 0);
        isMenuOpen = true;
    }

    /**
     * 关闭菜单，即滚动菜单的宽度只显示出主内容
     */
    private void closeMenu() {
        smoothScrollTo(mMenuView.getMeasuredWidth(), 0);
        isMenuOpen = false;
    }

    /**
     * scrollView滑动时的回调，可以拿到滑动的距离
     *
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        //scrollView滑动距离为菜单的宽度，这里通过滑动过的距离除以菜单宽度。
        // 得到一个比例系数，可以用来计算给定view的透明度和缩放
        float rate = 1f * l / mMenuView.getMeasuredWidth();
        Log.e("m1Ku", rate + "");
        //1.右边主内容的缩放
        //设置缩放的中心，以View的左边中间为缩放原点
        ViewCompat.setPivotX(mContentView, 0);
        ViewCompat.setPivotY(mContentView, mContentView.getMeasuredHeight() / 2);
        float contentScale = 0.7f + 0.3f * rate;//表示缩放到最小为0.7倍
        ViewCompat.setScaleY(mContentView, contentScale);

        //2.左边菜单内容的缩放和透明度变化
        float alpha = 0.3f + 0.7f * (1 - rate);
        ViewCompat.setAlpha(mMenuView, alpha);
        float menuScale = 0.8f + 0.2f * (1 - rate);
        ViewCompat.setScaleY(mMenuView, menuScale);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //默认情况下滚动到菜单关闭状态
        scrollTo(mMenuView.getMeasuredWidth(), 0);
    }

    private int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

}
