package com.example.demo.Login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.demo.R;
import com.example.demo.Utils.StatusBarUtil;
import com.example.demo.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private Button register;
    private EditText user;
    private EditText password;
    private EditText aginpass;
    private EditText phone;
    private SharedPreferences shp;
    public Toolbar toolbar;
    private String usr, psd, apsd, pho;
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //设置沉浸式
        StatusBarUtil.StatusBarLightMode(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        register = findViewById(R.id.register);
        user = findViewById(R.id.username);
        password = findViewById(R.id.password);
        aginpass = findViewById(R.id.aginpass);
        phone = findViewById(R.id.phone);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usr = user.getText().toString().trim();
                psd = password.getText().toString().trim();
                apsd = aginpass.getText().toString().trim();
                pho = phone.getText().toString().trim();
                if (usr.equals("") || psd.equals("") || apsd.equals("") || pho.equals("")) {
                    Toast.makeText(RegisterActivity.this, "注册信息不能为空！", Toast.LENGTH_SHORT).show();
                } else if (!psd.equals(apsd)) {
                    Toast.makeText(RegisterActivity.this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
                } else {
                    //通过规则判断手机号
                    if (!judgePhoneNums(pho)) {
                        return;
                    }
                    try {
                        runRegister(usr,psd,pho);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String  result = "";
                result = (String) msg.obj;
                if (result.equals("注册失败")) {
                    ToastUtils.show(RegisterActivity.this, "注册失败");
                    return;
                } else if (result.equals("用户已存在")) {
                    ToastUtils.show(RegisterActivity.this, "用户名已存在");
                    return;
                } else {
                    ToastUtils.show(RegisterActivity.this, "注册成功");
                    saveRegisterInfo(usr);
                    //注册成功后把账号传递到LoginActivity.java中
                    finish();
                }
            }
        }
    };

    private void runRegister(final String username, final String password, final String phone) throws InterruptedException {
        final OkHttpClient client = new OkHttpClient();
        Map map = new HashMap<>();
        map.put("username", username);//用户名
        map.put("password", password);//密码
        map.put("nickname", username);//昵称一开始默认就是用户名
        map.put("phone", phone);//手机号
        String param = gson.toJson(map);

        RequestBody requestBody = RequestBody.create(JSON, param);
        final Request request = new Request.Builder()
                .url("http://121.43.145.130:9898/user/register")
                .post(requestBody)
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

    private boolean judgePhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11)
                && isMobileNO(phoneNums)) {
            return true;
        }
        Toast.makeText(this, "手机号码输入有误！", Toast.LENGTH_SHORT).show();
        return false;
    }

    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }

    public static boolean isMobileNO(String mobileNums) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或7或8，其他位置的可以为0-9
         */
        String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    /**
     * 保存账号和密码到SharedPreferences中SharedPreferences
     */
    private void saveRegisterInfo(String userName) {
        //loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
        SharedPreferences sp = getSharedPreferences("registerInfo", MODE_PRIVATE);
        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
        SharedPreferences.Editor editor = sp.edit();
        //以用户名为key，密码为value保存在SharedPreferences中
        //key,value,如键值对，editor.putString(用户名，密码）;
        editor.putString("userName",userName);
        //提交修改 editor.commit();
        editor.commit();
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

}