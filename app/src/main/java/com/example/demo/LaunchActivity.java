package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author: JJY
 * @date: 2021/6/10 17:27
 */
/*
 *  描述： 启动页
 */

public class LaunchActivity extends AppCompatActivity {
    /**
     * 1.延时2000ms
     * 2.判断程序是否第一次运行
     * 3.Activity全屏主题
     */

    //闪屏页延时
    private static final int HANDLER_SPLASH = 1001;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_SPLASH:
                    startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                    finish();
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    //初始化View
    private void initView() {
        //延时2000ms
        handler.sendEmptyMessageDelayed(HANDLER_SPLASH, 500);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}