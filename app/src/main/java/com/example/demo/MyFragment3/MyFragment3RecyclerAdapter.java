package com.example.demo.MyFragment3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demo.Order.OrderDetailActivity;
import com.example.demo.R;
import com.example.demo.Order.ShopActivity;
import com.example.demo.Utils.Milk;
import com.example.demo.Utils.MilkById;
import com.example.demo.Utils.Order;

import java.sql.Time;
import java.util.List;

/**
 * @author: JJY
 * @date: 2021/6/3 17:54
 */
public class MyFragment3RecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {

    public Context context;
    public List<Order> orderList;

    public MyFragment3RecyclerAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_linear_order2, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        // 注册点击事件 start
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = myViewHolder.getAdapterPosition();
                Order order = orderList.get(orderList.size() - position - 1);
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("o_no", String.valueOf(order.getO_no()));
                context.startActivity(intent);
            }
        });
        // 注册点击事件 end

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.oneMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShopActivity.class);
                context.startActivity(intent);
            }
        });
        Order order = orderList.get(orderList.size() - position - 1);
        Milk milk = new MilkById(order.getM_id()).getMilk();
        Glide.with(context).load(milk.getM_picture()).into(holder.milkTea);
        holder.time.setText(order.getO_time());
        holder.price.setText(String.valueOf(order.getO_price()));
        holder.count.setText(String.valueOf(order.getO_count()));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    //刷新数据
    public void refresh(List<Order> newOrderList) {
        orderList.removeAll(orderList);
        orderList.addAll(newOrderList);
        notifyDataSetChanged();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView storeName, status, time, price, count;
    public Button oneMore;
    public ImageView milkTea;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        storeName = itemView.findViewById(R.id.store_name);
        status = itemView.findViewById(R.id.status);
        time = itemView.findViewById(R.id.time);
        price = itemView.findViewById(R.id.price);
        count = itemView.findViewById(R.id.count);
        oneMore = itemView.findViewById(R.id.one_more);
        milkTea = itemView.findViewById(R.id.miketea);
    }
}
