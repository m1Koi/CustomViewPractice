package com.m1Ku.progressview;

import android.content.Context;
import android.util.TypedValue;

/**
 * Author: m1Ku
 * Email: howx172@163.com
 * Create Time: 2017/9/11
 * Description:
 */

public class Utils {
    /**
     * dp2px
     */
    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue,
                context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics());
    }

    public static int px2dp(Context context, float pxValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pxValue,
                context.getResources().getDisplayMetrics());

    }

}
