package com.m1Ku.progressview.view.view9;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.m1Ku.progressview.R;

public class BubbleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble);

        MessageBubbleView.attach(findViewById(R.id.textView1),
                new MessageBubbleView.MessageDisappearListener() {
                    @Override
                    public void onDisappear(View view) {

                    }
                });

    }
}
