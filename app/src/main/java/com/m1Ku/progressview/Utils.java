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
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context, int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics());
    }

}
