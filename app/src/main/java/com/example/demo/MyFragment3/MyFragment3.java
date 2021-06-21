package com.example.demo.MyFragment3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.demo.Login.LoginActivity;
import com.example.demo.Order.ShopActivity;
import com.example.demo.R;
import com.example.demo.Utils.Order;
import com.example.demo.Utils.OrderByUid;
import com.example.demo.Utils.UserByName;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MyFragment3 extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private TextView niu;
    private Button cup;
    private String usr;
    private boolean isLogin;
    private MyFragment3RecyclerAdapter adapter;
    private Integer u_id;

    public MyFragment3() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my3, container, false);

        niu = view.findViewById(R.id.niu);
        cup = view.findViewById(R.id.cup);
        readUserInfo();
        if (isLogin) {
            u_id = new UserByName(usr).getUser().getU_id();
            List<Order> orderList = new OrderByUid(u_id.toString()).getOrderList();
            if (orderList == null) {
                niu.setVisibility(View.VISIBLE);
                cup.setVisibility(View.VISIBLE);
            } else {
                initRecyclerView();
                niu.setVisibility(View.GONE);
                cup.setVisibility(View.GONE);
            }
        } else {
            niu.setVisibility(View.VISIBLE);
            cup.setVisibility(View.VISIBLE);
        }
        cup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", MODE_PRIVATE);
                boolean isLoginStatus = sp.getBoolean("isLogin", false);
                if (isLoginStatus) {
                    Intent intent = new Intent(getActivity(), ShopActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "请先登录!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.putExtra("n", 3);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        readUserInfo();
        if (isLogin) adapter.refresh(new OrderByUid(u_id.toString()).getOrderList());
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        recyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyFragment3RecyclerAdapter(getActivity(), new OrderByUid(u_id.toString()).getOrderList());
        recyclerView.setAdapter(adapter);
    }

    /**
     * 从SharedPreference中获取登录状态和登录用户名
     */
    private void readUserInfo() {
        SharedPreferences shp = getActivity().getSharedPreferences("loginInfo", MODE_PRIVATE);
        usr = shp.getString("loginUserName", "");
        isLogin = shp.getBoolean("isLogin", false);
    }

}