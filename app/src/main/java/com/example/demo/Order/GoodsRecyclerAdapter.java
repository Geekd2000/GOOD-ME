package com.example.demo.Order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;

/**
 * @author: JJY
 * @date: 2021/6/3 17:54
 */
public class GoodsRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder3> {

    public Context context;

    public GoodsRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_goods_item, parent, false);

        return new MyViewHolder3(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder3 holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }
}

class MyViewHolder3 extends RecyclerView.ViewHolder {

    public TextView goods_name, goods_type, price, count;
    public ImageView img_goods;

    public MyViewHolder3(@NonNull View itemView) {
        super(itemView);
        img_goods = itemView.findViewById(R.id.img_goods);
        goods_name = itemView.findViewById(R.id.goods_name);
        goods_type = itemView.findViewById(R.id.goods_type);
        price = itemView.findViewById(R.id.jiage);
        count = itemView.findViewById(R.id.shuliang);
    }
}
