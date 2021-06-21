package com.example.demo.MyFragment2;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * @author: JJY
 * @date: 2021/6/3 17:54
 */
public class LeftRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private int checkedPosition;

    private Context context;
    public static int[] img = {R.drawable.xinpin, R.drawable.renqi, R.drawable.xianding,
            R.drawable.guocha, R.drawable.kafei, R.drawable.zhishi, R.drawable.qingru,
            R.drawable.naicha, R.drawable.liaoduoduo, R.drawable.tixing};
    public static String[] category = {"当季新品", "人气推荐", "限定果茶", "果茶系列",
            "冷萃咖啡", "芝士茗茶", "轻乳茶", "奶茶家族", "料多多", "用餐提醒"};

    public LeftRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_linear_left, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        // 注册点击事件 start
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = myViewHolder.getAdapterPosition();

            }
        });
        // 注册点击事件 end

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //设置文字空间的drawable图片
        Drawable topDrawable = context.getResources().getDrawable(img[position]);
        topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
        holder.leftName.setCompoundDrawables(null, topDrawable, null, null);

        //设置文字
        holder.leftName.setText(category[position]);

        if(position==checkedPosition){
            holder.leftName.setSelected(true);
        }else {
            holder.leftName.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return img.length;
    }

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        notifyDataSetChanged();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView leftName;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        leftName = itemView.findViewById(R.id.left_name);
    }
}
