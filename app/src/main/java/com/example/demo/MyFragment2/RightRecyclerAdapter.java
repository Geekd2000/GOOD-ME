package com.example.demo.MyFragment2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demo.Login.LoginActivity;
import com.example.demo.MainActivity;
import com.example.demo.Order.ShopActivity;
import com.example.demo.R;
import com.example.demo.Utils.Milk;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author: JJY
 * @date: 2021/6/3 17:54
 */
public class RightRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder2> {

    private Context context;
    private List<Milk> milkList;
    private Dialog mShareDialog;
    private ImageView img;
    private TextView mName, new_good, cold, cha, ka, ru, mang, content;
    private TextView mid, big;
    private TextView zhenzhu, yeguo, ximi;
    private TextView shao, duo, zheng, wen, re;
    private TextView qi, wu, san, wei, shi;
    private TextView milk_type, milk_price, milk_count, add, del;
    private Button addToShop;
    private String guiges;
    private int other;

    public RightRecyclerAdapter(Context context, List<Milk> milkList) {
        this.context = context;
        this.milkList = milkList;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_linear_right, parent, false);
        final MyViewHolder2 myViewHolder = new MyViewHolder2(view);
        // 注册点击事件 start
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = myViewHolder.getAdapterPosition();
                Milk milk = milkList.get(position);
                SharedPreferences sp = context.getSharedPreferences("loginInfo", MODE_PRIVATE);
                boolean isLoginStatus = sp.getBoolean("isLogin", false);
                if (isLoginStatus) {
                    showDialog(milk);
                } else {
                    Toast.makeText(context, "请先登录!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.putExtra("n", 1);
                    context.startActivity(intent);
                }
            }
        });
        // 注册点击事件 end

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
        Milk milk = milkList.get(position);
        //加载图片
        Glide.with(context).load(milk.getM_picture()).into(holder.img);
        holder.name.setText(milk.getM_name());
        holder.introduce.setText(milk.getM_info());
        holder.price.setText(String.valueOf(milk.getM_price()));
        holder.guige.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = context.getSharedPreferences("loginInfo", MODE_PRIVATE);
                boolean isLoginStatus = sp.getBoolean("isLogin", false);
                if (isLoginStatus) {
                    showDialog(milk);
                } else {
                    Toast.makeText(context, "请先登录!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.putExtra("n", 1);
                    context.startActivity(intent);
                }
            }
        });

        int m_xin = milk.getM_xin(), m_cha = milk.getM_cha(), m_ru = milk.getM_ru(),
                m_ka = milk.getM_ka(), m_mang = milk.getM_mang(), m_bing = milk.getM_bing();
        if (m_xin == 1) holder.newLabel.setVisibility(View.VISIBLE);
        if (m_cha == 1) holder.chaLabel.setVisibility(View.VISIBLE);
        if (m_ru == 1) holder.ruzhipinLabel.setVisibility(View.VISIBLE);
        if (m_ka == 1) holder.coffeeLabel.setVisibility(View.VISIBLE);
        if (m_mang == 1) holder.mangguoLabel.setVisibility(View.VISIBLE);
        if (m_bing == 1) holder.coldLabel.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return milkList.size();
    }

    //显示对话框
    private void showDialog(Milk milk) {
        initShareDialog(milk);
        mShareDialog.show();
    }

    /**
     * 初始化分享弹出框
     */
    private void initShareDialog(Milk milk) {
        mShareDialog = new Dialog(context, R.style.dialog_bottom_full);
        mShareDialog.setCanceledOnTouchOutside(true); //手指触碰到外界取消
        mShareDialog.setCancelable(true);             //可取消 为true
        Window window = mShareDialog.getWindow();      // 得到dialog的窗体
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.share_animation);
        //获取布局视图
        View view = View.inflate(context, R.layout.dialog_choose, null);
        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShareDialog != null && mShareDialog.isShowing()) {
                    mShareDialog.dismiss();
                }
            }
        });
        initDialogView(view);
        Glide.with(context).load(milk.getM_picture()).into(img);//奶茶图
        mName.setText(milk.getM_name());//奶茶名
        content.setText(milk.getM_info());//奶茶描述
        milk_price.setText(String.valueOf(milk.getM_price()));//设置价格
        //设置标签
        int m_xin = milk.getM_xin(), m_cha = milk.getM_cha(), m_ru = milk.getM_ru(),
                m_ka = milk.getM_ka(), m_mang = milk.getM_mang(), m_bing = milk.getM_bing();
        if (m_xin == 1) new_good.setVisibility(View.VISIBLE);
        if (m_cha == 1) cha.setVisibility(View.VISIBLE);
        if (m_ru == 1) ru.setVisibility(View.VISIBLE);
        if (m_ka == 1) ka.setVisibility(View.VISIBLE);
        if (m_mang == 1) mang.setVisibility(View.VISIBLE);
        if (m_bing == 1) cold.setVisibility(View.VISIBLE);

        setSelected();//默认设置  规格：中选中; 温度：正常冰选中; 甜度：七分甜（正常糖）选中
        clickListener(milk);//点击事件设置 奶茶规格 价格均在此设置

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = milk_count.getText().toString();
                int count = Integer.parseInt(s);
                if (count < 100) {
                    count = count + 1;
                    String num = Integer.toString(count);
                    milk_count.setText(num);
                    int now = Integer.parseInt(milk_price.getText().toString()) / (count - 1);
                    String tot = Integer.toString(count * now);
                    milk_price.setText(tot);
                } else {
                    Toast.makeText(context, "不能再加啦>_<", Toast.LENGTH_SHORT).show();
                }
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = milk_count.getText().toString();
                int count = Integer.parseInt(s);
                if (count > 1) {
                    count = count - 1;
                    String num = Integer.toString(count);
                    milk_count.setText(num);
                    int now = Integer.parseInt(milk_price.getText().toString()) / (count + 1);//当前单价
                    String tot = Integer.toString(count * now);
                    milk_price.setText(tot);
                } else {
                    Toast.makeText(context, "不能再减啦>_<", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addToShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMilkInfo(true, mName.getText().toString(),
                        Integer.parseInt(milk_price.getText().toString()),
                        milk_type.getText().toString(), Integer.parseInt(milk_count.getText().toString()), milk.getM_picture());
                if (mShareDialog != null && mShareDialog.isShowing()) {
                    mShareDialog.dismiss();
                    MyFragment2.shopping_price.setText(milk_price.getText().toString());
                    MyFragment2.relativeLayout.setVisibility(View.VISIBLE);
                    ShopActivity.shopping_price.setText(milk_price.getText().toString());
                    ShopActivity.relativeLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
    }

    /**
     * 保存选好的奶茶信息到SharedPreferences中
     */
    private void saveMilkInfo(boolean isChoose, String milkName, int milkPrice, String milkType, int milkCount, String milkUrl) {
        SharedPreferences shp = context.getSharedPreferences("milkInfo", MODE_PRIVATE);
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

    private class onClick1 implements View.OnClickListener {

        private Milk milk;

        public onClick1(Milk milk) {
            this.milk = milk;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mid:
                    cancelSelected1();
                    mid.setSelected(true);
                    type(milk);
                    break;
                case R.id.big:
                    cancelSelected1();
                    big.setSelected(true);
                    type(milk);
                    break;
            }
        }
    }

    private class onClick2 implements View.OnClickListener {

        private Milk milk;

        public onClick2(Milk milk) {
            this.milk = milk;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.shao:
                    cancelSelected2();
                    shao.setSelected(true);
                    type(milk);
                    break;
                case R.id.duo:
                    cancelSelected2();
                    duo.setSelected(true);
                    type(milk);
                    break;
                case R.id.zheng:
                    cancelSelected2();
                    zheng.setSelected(true);
                    type(milk);
                    break;
                case R.id.wen:
                    cancelSelected2();
                    wen.setSelected(true);
                    type(milk);
                    break;
                case R.id.re:
                    cancelSelected2();
                    re.setSelected(true);
                    type(milk);
                    break;
            }
        }
    }

    private class onClick3 implements View.OnClickListener {

        private Milk milk;

        public onClick3(Milk milk) {
            this.milk = milk;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.qi:
                    cancelSelected3();
                    qi.setSelected(true);
                    type(milk);
                    break;
                case R.id.wu:
                    cancelSelected3();
                    wu.setSelected(true);
                    type(milk);
                    break;
                case R.id.san:
                    cancelSelected3();
                    san.setSelected(true);
                    type(milk);
                    break;
                case R.id.wei:
                    cancelSelected3();
                    wei.setSelected(true);
                    type(milk);
                    break;
                case R.id.shi:
                    cancelSelected3();
                    shi.setSelected(true);
                    type(milk);
                    break;
            }
        }
    }

    private class onClick4 implements View.OnClickListener {

        private Milk milk;

        public onClick4(Milk milk) {
            this.milk = milk;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.zhenzhu:
                    if (zhenzhu.isSelected())
                        zhenzhu.setSelected(false);
                    else
                        zhenzhu.setSelected(true);
                    type(milk);
                    break;
                case R.id.yeguo:
                    if (yeguo.isSelected())
                        yeguo.setSelected(false);
                    else
                        yeguo.setSelected(true);
                    type(milk);
                    break;
                case R.id.ximi:
                    if (ximi.isSelected())
                        ximi.setSelected(false);
                    else
                        ximi.setSelected(true);
                    type(milk);
                    break;
            }
        }
    }

    private void type(Milk milk)  {
        guiges = "";
        other = 0;
        if (mid.isSelected()) {
            guiges = guiges + "中、";
        } else if (big.isSelected()) {
            guiges = guiges + "大、";
            other = other + 2;
        }

        if (shao.isSelected()) {
            guiges = guiges + "少冰、";
        } else if (duo.isSelected()) {
            guiges = guiges + "多冰、";
        } else if (zheng.isSelected()) {
            guiges = guiges + "正常冰、";
        } else if (wen.isSelected()) {
            guiges = guiges + "温、";
        } else if (re.isSelected()) {
            guiges = guiges + "热、";
        }

        if (qi.isSelected()) {
            guiges = guiges + "七分甜（正常糖）";
        } else if (wu.isSelected()) {
            guiges = guiges + "五分甜";
        } else if (san.isSelected()) {
            guiges = guiges + "三分甜";
        } else if (wei.isSelected()) {
            guiges = guiges + "微糖（不额外加糖）";
        } else if (shi.isSelected()) {
            guiges = guiges + "十分甜";
        }

        if (zhenzhu.isSelected()) {
            guiges = guiges + "、珍珠";
            other++;
        }
        if (yeguo.isSelected()) {
            guiges = guiges + "、椰果";
            other++;
        }
        if (ximi.isSelected()) {
            guiges = guiges + "、西米";
            other++;
        }

        milk_type.setText(guiges);
        milk_price.setText(String.valueOf((milk.getM_price() + other) * Integer.parseInt(milk_count.getText().toString())));
    }

    //重置所有文本的选中状态
    private void cancelSelected1() {
        mid.setSelected(false);
        big.setSelected(false);
    }

    //重置所有文本的选中状态
    private void cancelSelected2() {
        shao.setSelected(false);
        duo.setSelected(false);
        zheng.setSelected(false);
        wen.setSelected(false);
        re.setSelected(false);
    }

    //重置所有文本的选中状态
    private void cancelSelected3() {
        qi.setSelected(false);
        wu.setSelected(false);
        san.setSelected(false);
        wei.setSelected(false);
        shi.setSelected(false);
    }

    //设置默认选中
    private void setSelected() {
        mid.setSelected(true);
        zheng.setSelected(true);
        qi.setSelected(true);
    }

    private void clickListener(Milk milk) {
        onClick1 onClick1 = new onClick1(milk);
        mid.setOnClickListener(onClick1);
        big.setOnClickListener(onClick1);
        onClick2 onClick2 = new onClick2(milk);
        shao.setOnClickListener(onClick2);
        duo.setOnClickListener(onClick2);
        zheng.setOnClickListener(onClick2);
        wen.setOnClickListener(onClick2);
        re.setOnClickListener(onClick2);
        onClick3 onClick3 = new onClick3(milk);
        qi.setOnClickListener(onClick3);
        wu.setOnClickListener(onClick3);
        san.setOnClickListener(onClick3);
        wei.setOnClickListener(onClick3);
        shi.setOnClickListener(onClick3);
        onClick4 onClick4 = new onClick4(milk);
        zhenzhu.setOnClickListener(onClick4);
        yeguo.setOnClickListener(onClick4);
        ximi.setOnClickListener(onClick4);
    }

    //初始化对话框的控件
    private void initDialogView(View view) {
        img = view.findViewById(R.id.img_milk);
        mName = view.findViewById(R.id.milk_name);
        new_good = view.findViewById(R.id.new_good);
        cold = view.findViewById(R.id.only_cold);
        cha = view.findViewById(R.id.hancha);
        ka = view.findViewById(R.id.hankafei);
        ru = view.findViewById(R.id.hanruzhipin);
        mang = view.findViewById(R.id.hanmangguo);
        content = view.findViewById(R.id.content);
        mid = view.findViewById(R.id.mid);
        big = view.findViewById(R.id.big);
        zhenzhu = view.findViewById(R.id.zhenzhu);
        yeguo = view.findViewById(R.id.yeguo);
        ximi = view.findViewById(R.id.ximi);
        shao = view.findViewById(R.id.shao);
        duo = view.findViewById(R.id.duo);
        zheng = view.findViewById(R.id.zheng);
        wen = view.findViewById(R.id.wen);
        re = view.findViewById(R.id.re);
        qi = view.findViewById(R.id.qi);
        wu = view.findViewById(R.id.wu);
        san = view.findViewById(R.id.san);
        wei = view.findViewById(R.id.wei);
        shi = view.findViewById(R.id.shi);
        milk_type = view.findViewById(R.id.milk_type);
        milk_price = view.findViewById(R.id.milk_price);
        milk_count = view.findViewById(R.id.milk_count);
        add = view.findViewById(R.id.add);
        del = view.findViewById(R.id.del);
        addToShop = view.findViewById(R.id.add_to_shopping);
    }
}

class MyViewHolder2 extends RecyclerView.ViewHolder {

    public TextView name, introduce, price, guige, newLabel, coldLabel, chaLabel, coffeeLabel, ruzhipinLabel, mangguoLabel;
    public ImageView img;

    public MyViewHolder2(@NonNull View itemView) {
        super(itemView);
        img = itemView.findViewById(R.id.img);
        name = itemView.findViewById(R.id.name);
        introduce = itemView.findViewById(R.id.introduce);
        price = itemView.findViewById(R.id.price);
        guige = itemView.findViewById(R.id.guige);
        newLabel = itemView.findViewById(R.id.new_good);
        coldLabel = itemView.findViewById(R.id.only_cold);
        chaLabel = itemView.findViewById(R.id.hancha);
        coffeeLabel = itemView.findViewById(R.id.hankafei);
        ruzhipinLabel = itemView.findViewById(R.id.hanruzhipin);
        mangguoLabel = itemView.findViewById(R.id.hanmangguo);
    }
}
