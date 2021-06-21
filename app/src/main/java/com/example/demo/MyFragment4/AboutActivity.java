package com.example.demo.MyFragment4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.example.demo.R;
import com.example.demo.Utils.StatusBarUtil;

public class AboutActivity extends AppCompatActivity {

    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.bottom_in,R.anim.bottom_silent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //设置沉浸式
        StatusBarUtil.StatusBarLightMode(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_silent,R.anim.right_out);
    }
}