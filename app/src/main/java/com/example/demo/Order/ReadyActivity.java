package com.example.demo.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.demo.R;
import com.example.demo.Utils.Milk;
import com.example.demo.Utils.MilkById;
import com.example.demo.Utils.MilkByName;
import com.example.demo.Utils.Order;
import com.example.demo.Utils.OrderByNo;
import com.example.demo.Utils.StatusBarUtil;

public class ReadyActivity extends AppCompatActivity {

    private Toolbar toolbar;
    public Button mMore;
    public TextView phone, price, total, count, time, no;
    private TextView goods_name, goods_type, shuliang, jiage;
    private ImageView img_goods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready);
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
        mMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReadyActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });
        String o_no = getIntent().getStringExtra("o_no");
        Order order = new OrderByNo(o_no).getOrder();
        String tot_price = String.valueOf(order.getO_price());
        String counts = String.valueOf(order.getO_count());
        String o_type = order.getO_type();
        String o_time = order.getO_time();
        int m_id = order.getM_id();
        Milk milk = new MilkById(m_id).getMilk();
        String m_name = milk.getM_name();
        String danjia = String.valueOf(Integer.parseInt(tot_price)/Integer.parseInt(counts));
        String m_picture = milk.getM_picture();

        Glide.with(this).load(m_picture).into(img_goods);
        goods_name.setText(m_name);
        goods_type.setText(o_type);
        shuliang.setText(counts);
        jiage.setText(danjia);
        price.setText(tot_price);
        total.setText(tot_price);
        count.setText(counts);
        time.setText(o_time);
        no.setText(o_no);

    }

    private void initView() {
        mMore = findViewById(R.id.more);
        phone = findViewById(R.id.phone);
        price = findViewById(R.id.price);
        total = findViewById(R.id.total);
        count = findViewById(R.id.count);
        time = findViewById(R.id.time);
        no = findViewById(R.id.no);
        toolbar = findViewById(R.id.toolbar);
        goods_name = findViewById(R.id.goods_name);
        goods_type = findViewById(R.id.goods_type);
        shuliang = findViewById(R.id.shuliang);
        jiage = findViewById(R.id.jiage);
        img_goods = findViewById(R.id.img_goods);
    }

}