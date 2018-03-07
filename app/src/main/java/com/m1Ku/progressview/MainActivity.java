package com.m1Ku.progressview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.m1Ku.progressview.view.view9.BubbleActivity;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListAdapter adapter;

    private List<String> mDatas = Arrays.asList("自定义view1-QQ计步器",
            "自定义view2-RatingBar", "自定义view3-字母索引条", "自定义view4-58同城加载动画",
            "自定义view9-贝塞尔曲线练习");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ListAdapter(R.layout.item_customview, mDatas);
        RecyclerView rvCustomView = findViewById(R.id.rvCustomView);
        rvCustomView.setAdapter(adapter);
        rvCustomView.setLayoutManager(new LinearLayoutManager(this));
        rvCustomView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(ContextCompat.getColor(this, R.color.divider_color))
                .sizeResId(R.dimen.divider_height).build());

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String item = mDatas.get(position);
                if (item.contains("view9")) {
                    startActivity(new Intent(MainActivity.this, BubbleActivity.class));
                }
            }
        });


//        final Handler handler = new Handler();

        /**QQ计步器测试 **/
//        ProgressView progressView = (ProgressView) findViewById(R.id.progressView);
//        progressView.setMaxProgress(4000);
//        progressView.setCurrentProgress(2855);

        /** RatingBar 评分条测试**/
//        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
//        ratingBar.setInitRate(3);
//        ratingBar.setonRateSelectedListener(new RatingBar.OnRateSelectedListener() {
//            @Override
//            public void onRateSelect(int rate) {
//                Log.i("m1Ku", "当前选择级别是" + rate +"级");
//            }
//        });
        /**仿网易云音乐字母索引条测试 **/
//        final TextView tvIndicator = (TextView) findViewById(R.id.tv_indicator);
//
//        LetterIndexView letterIndexView = (LetterIndexView) findViewById(R.id.letterIndexView);
//        letterIndexView.setOnLetterTouchListener(new LetterIndexView.OnLetterTouchListener() {
//            @Override
//            public void onTouchIndex(String letter, boolean isShow) {
//                if (isShow) {
//                    tvIndicator.setVisibility(View.VISIBLE);
//                    tvIndicator.setText(letter);
//                } else {
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            tvIndicator.setVisibility(View.GONE);
//                        }
//                    }, 300);
//                }
//
//            }
//        });

        /**仿58同城加载动画测试 **/

//        mDatas = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            mDatas.add("item ->" + i);
//        }
//
//        listView = (ListView) findViewById(R.id.listView);
//        listView.setAdapter(new BaseAdapter() {
//            @Override
//            public int getCount() {
//                return mDatas.size();
//            }
//
//            @Override
//            public Object getItem(int i) {
//                return mDatas.get(i);
//            }
//
//            @Override
//            public long getItemId(int i) {
//                return i;
//            }
//
//            @Override
//            public View getView(int i, View view, ViewGroup viewGroup) {
//                TextView textView = (TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.item_list,null);
//                textView.setText(mDatas.get(i));
//                return textView;
//            }
//        });

//        final LoadingView loadingView = (LoadingView) findViewById(R.id.loadingView);
//        Button button = (Button) findViewById(R.id.btn);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (loadingView.getVisibility()== View.VISIBLE){
//                    loadingView.setVisibility(View.GONE);
//                }else {
//                    loadingView.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });

    }
}
