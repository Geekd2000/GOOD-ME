package com.example.appofzhejiang.fragment1.tourismculture;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appofzhejiang.R;
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;

public class TourismCultureHeritageActivity extends AppCompatActivity {
    private TextView tourismPublicityTitle;
    private String currentCity;
    private String location;
    private Toolbar backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_heritage);
        //设置沉浸式
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setDarkFont(this);
        // 隐藏系统标题
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Intent intent = getIntent();
        this.currentCity = intent.getStringExtra("currentCity");
        // 设置标题
        this.tourismPublicityTitle = (TextView) findViewById(R.id.tourism_publicity_title);
        setTourismPublicityTitle();
        // 设置标题栏返回按钮点击事件
        backButton = findViewById(R.id.back_toolbar);
        backButton.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 设置标题
     */
    private void setTourismPublicityTitle() {
        this.tourismPublicityTitle.setText("世界文化遗产-浙江篇");
        this.tourismPublicityTitle.setTextColor(Color.BLACK);
    }
}