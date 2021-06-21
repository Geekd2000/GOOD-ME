package com.example.demo.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.MainActivity;
import com.example.demo.R;
import com.example.demo.Utils.StatusBarUtil;
import com.example.demo.ToastUtils;
import com.example.demo.Utils.UserUtil;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText user;
    private EditText password;
    private TextView register;
    private CheckBox checkBox;
    private Button login;
    private SharedPreferences shp;
    private String usr;
    private String pass;
    private Boolean is = false;
    private int n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //此活动打开的进入方式
        overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //设置沉浸式
        StatusBarUtil.StatusBarLightMode(this);
        user = findViewById(R.id.user);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        checkBox = findViewById(R.id.checkbox);
        login = findViewById(R.id.login);
        //加载本地文件的账号信息
        loadUserInfo();
        //这个数值用来判断是否在奶茶界面登陆，奶茶界面登陆为1，其他为0
        n = getIntent().getIntExtra("n", 0);

        //登陆
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (user.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "账号和密码不能为空", Toast.LENGTH_SHORT).show();
                }
                if (user.getText().toString().equals(usr) && password.getText().toString().equals(pass)) {
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    if (checkBox.isChecked()) {
                        saveUserInfo();
                    }
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "账号或密码错误请重新输入", Toast.LENGTH_SHORT).show();
                }*/
                //开始登录，获取用户名和密码 getText().toString().trim();
                usr = user.getText().toString().trim();
                pass = password.getText().toString().trim();
                //请求获取服务器数据
                try {
                    runLogin(usr, pass);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //注册
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.bottom_silent, R.anim.bottom_out);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String result = "";
                result = (String) msg.obj;
                if (result.equals("用户名不存在")) {
                    ToastUtils.show(LoginActivity.this, "用户名不存在");
                    return;
                } else if (result.equals("密码错误")) {
                    ToastUtils.show(LoginActivity.this, "密码错误");
                    return;
                } else {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //保存登录状态，在界面保存登录的用户名 定义个方法 saveLoginStatus boolean 状态 , userName 用户名;
                    saveLoginStatus(true, usr, checkBox.isChecked());
                    //登录成功后关闭此页面进入主页
                    Intent data = new Intent();
                    //datad.putExtra( ); name , value ;
                    data.putExtra("isLogin", true);
                    //RESULT_OK为Activity系统常量，状态码为-1
                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                    setResult(RESULT_OK, data);
                    //销毁登录界面
                    finish();
                    if (n == 0) {
                        //跳转到主界面，登录成功的状态传递到 MainActivity 中
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("num", 3);
                        startActivity(intent);
                    }else if(n == 3){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("num", 2);
                        startActivity(intent);
                    }
                }
            }
        }
    };

    private void runLogin(String username, String psw) throws InterruptedException {
        final OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url("http://121.43.145.130:9898/user/login?username=" + username + "&password=" + psw)
                .build();
        //处理注册逻辑
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    } else {
//                        mHandler.obtainMessage(1, response.body().string()).sendToTarget();
//                        System.out.println(response.body().string());
                        mHandler.obtainMessage(1, response.body().string()).sendToTarget();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t1.join();
    }

    /**
     * 从SharedPreference中获取登录状态和登录用户名
     */
    private void loadUserInfo() {
        shp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        usr = shp.getString("loginUserName", "");
        pass = shp.getString("password", "");
        is = shp.getBoolean("is", false);
        user.setText(usr);
        if (is) {
            password.setText(pass);
        }
        checkBox.setChecked(is);
    }

    /**
     * 从SharedPreferences中根据用户名读取密码
     */
    private String readPsw(String userName) {
        //getSharedPreferences("loginInfo",MODE_PRIVATE);
        //"loginInfo",mode_private; MODE_PRIVATE表示可以继续写入
        shp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        //shp.getString() userName, "";
        return shp.getString(userName, "");
    }

    /**
     * 保存登录状态和登录用户名到SharedPreferences中
     */
    private void saveLoginStatus(boolean status, String userName, boolean checkBox) {
        //saveLoginStatus(true, userName);
        //loginInfo表示文件名  SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        shp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor = shp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin", status);
        //存入登录状态时的用户名
        editor.putString("loginUserName", userName);
        //存入checkBox的选中状态
        editor.putBoolean("is", checkBox);
        //存入登录状态的昵称
        String nickname = new UserUtil(userName).getUser().getNickname();
        System.out.println(nickname);
        editor.putString("loginNickname", nickname);
        //提交修改
        editor.apply();
    }

    //点击空白处收起键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }


    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private void clearRegisterInfo() {
        shp = getSharedPreferences("registerInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.clear();
        editor.apply();
    }
}