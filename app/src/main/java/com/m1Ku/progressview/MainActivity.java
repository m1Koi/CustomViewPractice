package com.m1Ku.progressview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.m1Ku.progressview.view.LetterIndexView;
import com.m1Ku.progressview.view.ProgressView;
import com.m1Ku.progressview.view.RatingBar;
import com.m1Ku.progressview.view.ShapeChangeView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Handler handler = new Handler();

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
        final ShapeChangeView shapeChangeView = (ShapeChangeView) findViewById(R.id.shapeChangeView);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            shapeChangeView.changeShape();
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }
}
