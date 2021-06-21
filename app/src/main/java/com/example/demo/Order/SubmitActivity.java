package com.example.demo.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.demo.R;
import com.example.demo.Utils.MilkByName;
import com.example.demo.Utils.StatusBarUtil;
import com.example.demo.ToastUtils;
import com.example.demo.Utils.User;
import com.example.demo.Utils.UserByName;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SubmitActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public TextView count, total, totalPrice;
    public EditText phone;
    public Button submit;
    private int milkCount, milkPrice;
    private String milkName, milkType, milkUrl;
    private boolean isChoose;
    private TextView goods_name, goods_type, shuliang, jiage;
    private ImageView img_goods;
    private String usr;

    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        //设置沉浸式
        StatusBarUtil.StatusBarLightMode(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
        readMilkInfo();//读取奶茶的信息
        readUserInfo();//读取登陆的用户名
        count.setText(String.valueOf(milkCount));//共几杯奶茶
        total.setText(String.valueOf(milkPrice));//总价
        totalPrice.setText(String.valueOf(milkPrice));//总价
        goods_name.setText(milkName);//奶茶名字
        goods_type.setText(milkType);//奶茶规格
        shuliang.setText(String.valueOf(milkCount));//共几杯奶茶
        jiage.setText(String.valueOf(milkPrice / milkCount));//单价
        Glide.with(this).load(milkUrl).into(img_goods);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phone.getText().toString().equals("")) {
                    ToastUtils.show(SubmitActivity.this, "手机号不能为空！");
                } else if (!judgePhoneNums(phone.getText().toString())) {
                    return;
                } else {
                    //当前系统时间
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String time = formatter.format(date);
                    //订单号生成
                    //订单编号随机生成
                    int r1 = (int) (Math.random() * (9) + 1);//产生2个0-9的随机数
                    int r2 = (int) (Math.random() * (10));
                    int r3 = (int) (Math.random() * (10));
                    int r4 = (int) (Math.random() * (10));
                    long now = System.currentTimeMillis();//一个13位的时间戳
                    String no = String.valueOf(r1) + String.valueOf(r2) + String.valueOf(r3) + String.valueOf(r4) + String.valueOf(now);// 订单编号
                    User user = new UserByName(usr).getUser();
                    int u_id = user.getU_id();
                    int m_id = new MilkByName(milkName).getMilk().getM_id();
                    try {
                        runSubmit(u_id, m_id, time, no, milkPrice, milkType, milkCount);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finish();
                    ToastUtils.show(SubmitActivity.this, "支付成功！");
                    Intent intent = new Intent(SubmitActivity.this, ReadyActivity.class);
                    intent.putExtra("o_no", no);
                    startActivity(intent);
                    clearMilkInfo();//清除购物袋的信息
                }
            }
        });
    }

    private void initView() {
        count = findViewById(R.id.count);
        total = findViewById(R.id.total);
        totalPrice = findViewById(R.id.total_price);
        phone = findViewById(R.id.phone_number);
        submit = findViewById(R.id.submit);
        goods_name = findViewById(R.id.goods_name);
        goods_type = findViewById(R.id.goods_type);
        shuliang = findViewById(R.id.shuliang);
        jiage = findViewById(R.id.jiage);
        img_goods = findViewById(R.id.img_goods);
    }

    //向服务器发送post请求
    private void runSubmit(int u_id, int m_id, String o_time, String o_no,
                           int o_price, String o_type, int o_count) throws InterruptedException {
        final OkHttpClient client = new OkHttpClient();
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("u_id", u_id);
        map.put("m_id", m_id);
        map.put("o_time", o_time);
        map.put("o_no", o_no);
        map.put("o_price", o_price);
        map.put("o_type", o_type);
        map.put("o_count", o_count);
        String params = gson.toJson(map);

        RequestBody requestBody = RequestBody.create(JSON, params);
        final Request request = new Request.Builder()
                .url("http://121.43.145.130:9898/order/insertOrder")
                .post(requestBody)
                .build();
        //处理注册逻辑
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
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
    private void readUserInfo() {
        SharedPreferences shp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        usr = shp.getString("loginUserName", "");
    }

    /**
     * 从SharedPreferences中读取奶茶信息
     */
    public void readMilkInfo() {
        SharedPreferences shp = getSharedPreferences("milkInfo", MODE_PRIVATE);
        isChoose = shp.getBoolean("isChoose", false);
        milkName = shp.getString("milkName", "");
        milkPrice = shp.getInt("milkPrice", 0);
        milkCount = shp.getInt("milkCount", 0);
        milkType = shp.getString("milkType", "");
        milkUrl = shp.getString("milkUrl", "");
    }

    //清除SharedPreferences中信息
    private void clearMilkInfo() {
        SharedPreferences sp = getSharedPreferences("milkInfo", MODE_PRIVATE);
        if (sp != null) {
            sp.edit().clear().apply();
        }
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