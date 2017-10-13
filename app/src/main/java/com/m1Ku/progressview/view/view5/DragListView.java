package com.m1Ku.progressview.view.view5;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;

/**
 * Author: m1Ku
 * Email: howx172@163.com
 * Create Time: 2017/10/12
 * Description:
 */

public class DragListView extends FrameLayout {

    private ViewDragHelper mViewDragHelper;
    private View mDragView;
    private View mMenuView;
    private int menuHeight;
    private boolean isMenuOpen = false;

    public DragListView(@NonNull Context context) {
        this(context, null);
    }

    public DragListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragListView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mViewDragHelper = ViewDragHelper.create(this, mCallBack);
    }

    @Override
    protected void onFinishInflate() {
        mDragView = getChildAt(1);
        super.onFinishInflate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mMenuView = getChildAt(0);
            menuHeight = mMenuView.getMeasuredHeight();
        }
    }

    private ViewDragHelper.Callback mCallBack = new ViewDragHelper.Callback() {
        /**
         *
         * @param child
         * @param pointerId
         * @return 返回true，表示所有的子view都可以拖动
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return mDragView == child;
        }

        /**
         * 拖动方向
         * @param child
         * @param top
         * @param dy
         * @return
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            //上面布局滑动的距离
            if (top <= 0) {
                top = 0;
            }

            if (top > menuHeight) {
                top = menuHeight;
            }
            return top;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            float top = mDragView.getTop();
            if (releasedChild == mDragView)
                //垂直滚动，只需要判断段y的位置
                if (top > menuHeight / 2) {
                    //距离大于菜单高度一半，滚动到菜单高度
                    mViewDragHelper.settleCapturedViewAt(0, menuHeight);
                    isMenuOpen = true;
                } else {
                    //距离小于菜单高度一半，滚动到起始位置
                    mViewDragHelper.settleCapturedViewAt(0, 0);
                    isMenuOpen = false;
                }
            invalidate();
        }
    };

    /**
     * 响应滚动
     */
    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    private float downY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //当菜单打开时，拦截触摸滑动事件，我们的ViewGroup自己处理事件
        if (isMenuOpen)
            return true;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                mViewDragHelper.processTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY();
                /**
                 * 由于滑动控件是ListView或者是RecyclerView，
                 * 当向下滑动时，这个控件会消费触摸滑动事件，导致ViewGroup不响应滑动事件，此控件无法下滑。
                 * 所以，我们判断用户手势是下滑时，要拦截滑动触摸事件交由我们自己的ViewGroup处理，
                 * 为了ListView的正确显示，我们还需要判断当前ListView控件的位置是否下滑到第一个条目，
                 * 只有当下滑到第一个条目时，滑动触摸事件才交由我们的ViewGroup处理，否则还是ListView处理，
                 * 以让其能正确滑动到第一个条目。
                 */
                if (moveY - downY > 0 && !canChildScrollUp()) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * SwipeRefreshLayout中判断是否还可以向下滑动的方法
     * @return
     */
    public boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mDragView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mDragView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mDragView, -1) || mDragView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mDragView, -1);
        }
    }
}
