package com.example.demo.MyFragment1;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demo.GlideImageLoader;
import com.example.demo.MainActivity;
import com.example.demo.R;
import com.example.demo.Order.ShopActivity;
import com.example.demo.ToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

public class MyFragment1 extends Fragment {

    private View view;
    private Banner banner;
    private TextView daodian,ten_one,tiqian;
    private RelativeLayout rl1;
    private LinearLayout takeOrder;
    private TextView news,weibo;

    public MyFragment1() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my1, container, false);

        initView();//初始化控件，找到控件
        //设置字体
        daodian.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "HGZY_CNKI.TTF"));
        ten_one.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "heiti.TTF"));
        tiqian.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "zhongdengxian.TTF"));

        ArrayList<Integer> img = new ArrayList<Integer>();
        img.add(R.drawable.wulong);
        img.add(R.drawable.zhenzhu);
        img.add(R.drawable.naifu);

        //样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(img);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

        //增加点击事件
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
//                Toast.makeText(getContext(), "position"+position, Toast.LENGTH_SHORT).show();
            }
        });

        //购买界面
        takeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShopActivity.class);
                startActivity(intent);
            }
        });

        //微博
        weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(getContext(),"该板块还在持续开发中...");
//                Intent intent = new Intent(getContext(),WbActivity.class);
//                startActivity(intent);
            }
        });

        //集十赠一
        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(getContext(),"该板块还在持续开发中...");
            }
        });

        //新品公告牌
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(getContext(),"该板块还在持续开发中...");
            }
        });

        return view;
    }

    private void initView() {
        banner = view.findViewById(R.id.banner);
        daodian = view.findViewById(R.id.daodian);
        ten_one = view.findViewById(R.id.ten_one);
        tiqian = view.findViewById(R.id.tiqian);
        takeOrder = view.findViewById(R.id.take_order);
        rl1 = view.findViewById(R.id.rl1);
        news = view.findViewById(R.id.news);
        weibo = view.findViewById(R.id.weibo);
    }

}