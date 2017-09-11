package com.m1Ku.progressview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressView progressView = (ProgressView) findViewById(R.id.progressView);
        progressView.setMaxProgress(4000);
        progressView.setCurrentProgress(2855);
    }
}
