package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.demo.Order.SubmitActivity;
import com.example.demo.Utils.StatusBarUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    //UI Object
    private TextView tab_main; //首页按钮
    private TextView tab_sub; //点单按钮
    private TextView tab_order; //订单按钮
    private TextView tab_my; //我的按钮
    private ViewPager viewPager;
    private MyFragmentPagerAdapter mAdapter;

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        bindViews();//初始化绑定控件

        int id = getIntent().getIntExtra("num", 0);
        if (id == 0) {
            viewPager.setCurrentItem(PAGE_ONE);
            tab_main.setSelected(true);//进去后选择第一项
        }
        if (id == 1) {
            viewPager.setCurrentItem(PAGE_TWO);
            tab_sub.setSelected(true);//进去后选择第二项
        }
        if (id == 2) {
            viewPager.setCurrentItem(PAGE_THREE);
            tab_order.setSelected(true);//进去后选择第三项
        }
        if (id == 3) {
            viewPager.setCurrentItem(PAGE_FOUR);
            tab_my.setSelected(true);//进去后选择第四项
        }
        //设置沉浸式
        StatusBarUtil.StatusBarLightMode(this);
    }

    //UI组件初始化与事件绑定
    private void bindViews() {
        tab_main = findViewById(R.id.tab_main);
        tab_sub = findViewById(R.id.tab_sub);
        tab_order = findViewById(R.id.tab_order);
        tab_my = findViewById(R.id.tab_my);

        tab_main.setOnClickListener(this);
        tab_sub.setOnClickListener(this);
        tab_order.setOnClickListener(this);
        tab_my.setOnClickListener(this);

        viewPager = findViewById(R.id.vPager);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(2); // 防止频繁的销毁视图
    }

    //重置所有文本的选中状态
    private void setSelected() {
        tab_main.setSelected(false);
        tab_sub.setSelected(false);
        tab_order.setSelected(false);
        tab_my.setSelected(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_main:
                viewPager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.tab_sub:
                viewPager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.tab_order:
                viewPager.setCurrentItem(PAGE_THREE);
                break;
            case R.id.tab_my:
                viewPager.setCurrentItem(PAGE_FOUR);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (viewPager.getCurrentItem()) {
                case PAGE_ONE:
                    setSelected();
                    tab_main.setSelected(true);
                    break;
                case PAGE_TWO:
                    setSelected();
                    tab_sub.setSelected(true);
                    break;
                case PAGE_THREE:
                    setSelected();
                    tab_order.setSelected(true);
                    break;
                case PAGE_FOUR:
                    setSelected();
                    tab_my.setSelected(true);
                    break;
            }
        }
    }


    //双击手机返回键退出 start
    private long firstTime = 0;//第一次返回按钮计时

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    showShortToast("再按一次退出");
                    firstTime = secondTime;
                } else {//完全退出
                    moveTaskToBack(false);//应用退到后台
                    System.exit(0);
                }
                return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    /**
     * 快捷显示short toast方法
     *
     * @param string
     */
    public void showShortToast(String string) {
        showShortToast(string, false);
    }

    /**
     * 快捷显示short toast方法
     *
     * @param string
     * @param isForceDismissProgressDialog
     */
    public void showShortToast(final String string, final boolean isForceDismissProgressDialog) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isForceDismissProgressDialog) {
                    dismissProgressDialog();
                }
                Toast.makeText(MainActivity.this, "" + string, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 隐藏加载进度
     */
    public void dismissProgressDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //把判断写在runOnUiThread外面导致有时dismiss无效，可能不同线程判断progressDialog.isShowing()结果不一致
                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                if (progressDialog == null || progressDialog.isShowing() == false) {
                    Log.w("MainActivity", "dismissProgressDialog  progressDialog == null" +
                            " || progressDialog.isShowing() == false >> return;");
                    return;
                }
                progressDialog.dismiss();
            }
        });
    }
    //双击手机返回键退出 end
}