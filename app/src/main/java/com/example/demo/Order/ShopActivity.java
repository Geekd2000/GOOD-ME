package com.example.demo.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.demo.MyFragment2.LeftRecyclerAdapter;
import com.example.demo.Utils.Milk;
import com.example.demo.Utils.MilkUtil;
import com.example.demo.MyFragment2.RightRecyclerAdapter;
import com.example.demo.R;
import com.example.demo.Utils.StatusBarUtil;

import java.util.List;

public class ShopActivity extends AppCompatActivity {

    public RecyclerView rv_left, rv_right;
    public RightRecyclerAdapter rightAdapter;
    public LeftRecyclerAdapter leftAdapter;
    public Toolbar toolbar;
    public List<Milk> milkList;
    public LinearLayoutManager leftManager = new LinearLayoutManager(this);
    public LinearLayoutManager rightManager = new LinearLayoutManager(this);
    public Boolean move = false;
    public int mIndex = 0;
    public Button choice;
    public static RelativeLayout relativeLayout;
    public static TextView shopping_price;
    private ImageView shopping_bag;
    private boolean isChoose;
    private int milkCount, milkPrice;
    private String milkName, milkType, milkUrl;
    private Dialog mShareDialog;
    private ImageView img_goods;
    private TextView goods_name, goods_type, jiage, shuliang, add, del, clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.right_in, R.anim.right_silent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        //设置沉浸式
        StatusBarUtil.StatusBarLightMode(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //获取数据库数据
        milkList = new MilkUtil().getMilkList();

        initLeftRecyclerView();
        initRightRecyclerView();
        smoothMoveToPosition(0);
        rv_right.addOnScrollListener(new RecyclerViewListener());

        initView();
        readMilkInfo();//读取SharedPreferences中的存放的奶茶信息
        shopping_bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this, SubmitActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        readMilkInfo();
    }

    //显示对话框
    private void showDialog() {
        initShareDialog();
        mShareDialog.show();
    }

    /**
     * 初始化分享弹出框
     */
    private void initShareDialog() {
        mShareDialog = new Dialog(this, R.style.dialog_bottom_full);
        mShareDialog.setCanceledOnTouchOutside(true); //手指触碰到外界取消
        mShareDialog.setCancelable(true);             //可取消 为true
        Window window = mShareDialog.getWindow();      // 得到dialog的窗体
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.share_animation);
        //获取布局视图
        View view = View.inflate(this, R.layout.dialog_bag, null);

        initDialogView(view);
        readMilkInfo();
        //设置奶茶信息
        Glide.with(this).load(milkUrl).into(img_goods);
        goods_name.setText(milkName);
        goods_type.setText(milkType);
        jiage.setText(String.valueOf(milkPrice));
        shuliang.setText(String.valueOf(milkCount));
        //清除购物车
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMilkInfo();//清除SharedPreferences中的信息，同时清除购物车
                relativeLayout.setVisibility(View.GONE);//同时隐藏购物袋栏
                if (mShareDialog != null && mShareDialog.isShowing()) {
                    mShareDialog.dismiss();
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = shuliang.getText().toString();
                int count = Integer.parseInt(s);
                if (count < 100) {
                    count = count + 1;
                    String num = Integer.toString(count);
                    shuliang.setText(num);
                    int now = Integer.parseInt(jiage.getText().toString()) / (count - 1);
                    String tot = Integer.toString(count * now);
                    jiage.setText(tot);
                    saveMilkInfo(true, milkName, count * now, milkType, count, milkUrl);
                } else {
                    Toast.makeText(ShopActivity.this, "不能再加啦>_<", Toast.LENGTH_SHORT).show();
                }
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = shuliang.getText().toString();
                int count = Integer.parseInt(s);
                if (count > 1) {
                    count = count - 1;
                    String num = Integer.toString(count);
                    shuliang.setText(num);
                    int now = Integer.parseInt(jiage.getText().toString()) / (count + 1);//当前单价
                    String tot = Integer.toString(count * now);
                    jiage.setText(tot);
                    saveMilkInfo(true, milkName, count * now, milkType, count, milkUrl);
                } else {
                    clearMilkInfo();//清除SharedPreferences中的信息，同时清除购物车
                    relativeLayout.setVisibility(View.GONE);//同时隐藏购物袋栏
                    if (mShareDialog != null && mShareDialog.isShowing()) {
                        mShareDialog.dismiss();
                        readMilkInfo();
                    }
                }
            }
        });

        mShareDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mShareDialog.dismiss();
                readMilkInfo();
            }
        });
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
    }

    private void initDialogView(View view) {
        img_goods = view.findViewById(R.id.img_goods);
        goods_name = view.findViewById(R.id.goods_name);
        goods_type = view.findViewById(R.id.goods_type);
        jiage = view.findViewById(R.id.jiage);
        shuliang = view.findViewById(R.id.shuliang);
        clear = view.findViewById(R.id.clear);
        add = view.findViewById(R.id.add);
        del = view.findViewById(R.id.del);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_silent, R.anim.right_out);
    }

    private void initView(){
        relativeLayout = findViewById(R.id.rl);
        shopping_price = findViewById(R.id.shopping_price);
        shopping_bag = findViewById(R.id.shopping_bag);
        choice = findViewById(R.id.choice);
    }

    //初始化左侧RecyclerView，绑定适配器
    private void initLeftRecyclerView() {
        rv_left = findViewById(R.id.rv_left);
        rv_left.setLayoutManager(leftManager);
        leftAdapter = new LeftRecyclerAdapter(this);
        rv_left.setAdapter(leftAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rv_left.addItemDecoration(decoration);
    }

    //初始化右侧RecyclerView，绑定适配器
    private void initRightRecyclerView() {
        rv_right = findViewById(R.id.rv_right);
        rv_right.setLayoutManager(rightManager);
        rightAdapter = new RightRecyclerAdapter(this, milkList);
        rv_right.setAdapter(rightAdapter);
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
        if (isChoose) {
            relativeLayout.setVisibility(View.VISIBLE);
        }else {
            relativeLayout.setVisibility(View.GONE);
        }
        shopping_price.setText(String.valueOf(milkPrice));
    }

    /**
     * 保存选好的奶茶信息到SharedPreferences中
     */
    private void saveMilkInfo(boolean isChoose, String milkName, int milkPrice, String milkType, int milkCount, String milkUrl) {
        SharedPreferences shp = getSharedPreferences("milkInfo", MODE_PRIVATE);
        //获取编辑器int
        SharedPreferences.Editor editor = shp.edit();

        //存入奶茶是否有加入购物袋
        editor.putBoolean("isChoose", isChoose);
        //存入存入购物袋的奶茶的名字
        editor.putString("milkName", milkName);
        //存入存入购物袋的奶茶总价
        editor.putInt("milkPrice", milkPrice);
        //存入存入购物袋的奶茶数量
        editor.putInt("milkCount", milkCount);
        //存入存入购物袋的奶茶规格
        editor.putString("milkType", milkType);
        //存入存入购物袋的奶茶图片
        editor.putString("milkUrl", milkUrl);
        //提交修改
        editor.apply();
    }

    //清除SharedPreferences中信息
    private void clearMilkInfo() {
        SharedPreferences sp = getSharedPreferences("milkInfo", MODE_PRIVATE);
        if (sp != null) {
            sp.edit().clear().apply();
        }
    }

    //通过滚动的类型来进行相应的滚动
    private void smoothMoveToPosition(int n) {
        int firstItem = rightManager.findFirstVisibleItemPosition();
        int lastItem = rightManager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            rv_right.smoothScrollToPosition(n);
        } else if (n <= lastItem) {
            int top = rv_right.getChildAt(n - firstItem).getTop();
            rv_right.smoothScrollBy(0, top);
        } else {
            rv_right.smoothScrollToPosition(n);
            move = true;
        }
    }

    //监听第三种情况，滚动停止之后再次进行滚动
    private class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                move = false;
                int n = mIndex - rightManager.findFirstVisibleItemPosition();
                Log.d("n---->", String.valueOf(n));
                if (0 <= n && n < rv_right.getChildCount()) {
                    int top = rv_right.getChildAt(n).getTop();
                    Log.d("top--->", String.valueOf(top));
                    rv_right.smoothScrollBy(0, top);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    }

}