package com.example.demo.MyFragment4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.demo.R;
import com.example.demo.Utils.StatusBarUtil;

public class VipActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView qrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip);
        //设置沉浸式
        StatusBarUtil.StatusBarLightMode(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        qrCode = findViewById(R.id.qr_code);
        qrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VipActivity.this, QRcodeActivity.class);
                startActivity(intent);
            }
        });
    }
}