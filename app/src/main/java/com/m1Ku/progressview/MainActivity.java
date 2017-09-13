package com.m1Ku.progressview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.m1Ku.progressview.view.ProgressView;
import com.m1Ku.progressview.view.RatingBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ProgressView progressView = (ProgressView) findViewById(R.id.progressView);
//        progressView.setMaxProgress(4000);
//        progressView.setCurrentProgress(2855);

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setInitRate(3);
        ratingBar.setonRateSelectedListener(new RatingBar.OnRateSelectedListener() {
            @Override
            public void onRateSelect(int rate) {
                Log.i("m1Ku", "当前选择级别是" + rate +"级");
            }
        });
    }
}
