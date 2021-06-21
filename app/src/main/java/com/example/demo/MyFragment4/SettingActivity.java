package com.example.demo.MyFragment4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.Login.LoginActivity;
import com.example.demo.MainActivity;
import com.example.demo.R;
import com.example.demo.Utils.StatusBarUtil;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button mBtnRollOut;
    private RelativeLayout user;
    private TextView username;
    private Boolean isLoginStatus;
    private CircleImageView user_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.right_in,R.anim.right_silent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //设置沉浸式
        StatusBarUtil.StatusBarLightMode(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBtnRollOut = findViewById(R.id.roll_out);
        user = findViewById(R.id.user);
        username = findViewById(R.id.user_name_setting);
        user_image = findViewById(R.id.user_image);

        LoginStatus();//判断登陆状态

        mBtnRollOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                isLoginStatus = sp.getBoolean("isLogin", false);
                if (isLoginStatus) {
                    Out(false);
                    Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                    intent.putExtra("num",3);
                    startActivity(intent);
                    Toast.makeText(SettingActivity.this, "退出成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SettingActivity.this, "未登录，无法退出请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                }
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_silent,R.anim.right_out);
    }

    /**
     * 判断登录状态
     */
    public void LoginStatus() {
        SharedPreferences sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        isLoginStatus = sp.getBoolean("isLogin", false);
        if (isLoginStatus) {
            username.setText(getName());
            user_image.setImageResource(R.drawable.touxiang);
        }
    }

    /**
     * 退出登录
     * @param status
     */
    public void Out(Boolean status) {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor = sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin", status);
        //提交修改
        editor.apply();
    }

    public String getName() {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        return sp.getString("loginNickname", null);
    }
}