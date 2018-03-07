package com.m1Ku.progressview;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author: m1Ku
 * Email: howx172@163.com
 * Create Time: 2018/3/7
 * Description:
 */

public class ListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    ListAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tvName, item);
    }
}
