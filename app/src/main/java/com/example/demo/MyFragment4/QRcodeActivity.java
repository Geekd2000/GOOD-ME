package com.example.demo.MyFragment4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.demo.R;
import com.example.demo.Utils.StatusBarUtil;

public class QRcodeActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public ImageView iv_qrCode;
    private Bitmap qrCode_bitmap; //生成的二维码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_rcode);
        //设置沉浸式
        StatusBarUtil.StatusBarLightMode(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        iv_qrCode = findViewById(R.id.qr_code);
        /*qrCode_bitmap = QRCodeUtil.createQRCodeBitmap("会员码", 800, 800, "UTF-8",
                "H", "1",Color.BLACK, Color.WHITE, null, 0.2F, null);
        iv_qrCode.setImageBitmap(qrCode_bitmap);*/
    }


}