package com.example.demo.MyFragment2;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demo.MainActivity;
import com.example.demo.Order.SubmitActivity;
import com.example.demo.R;
import com.example.demo.Utils.Milk;
import com.example.demo.Utils.MilkUtil;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MyFragment2 extends Fragment {

    private View view;
    public RecyclerView rv_left, rv_right;
    public RightRecyclerAdapter rightAdapter;
    public LeftRecyclerAdapter leftAdapter;
    public List<Milk> milkList;
    public LinearLayoutManager leftManager = new LinearLayoutManager(getActivity());
    public LinearLayoutManager rightManager = new LinearLayoutManager(getActivity());
    private ImageView shopping_bag;
    public static RelativeLayout relativeLayout;
    public static TextView shopping_price;
    private Button choice;
    private ImageView img_goods;
    private TextView goods_name, goods_type, jiage, shuliang, add, del, clear;
    private Dialog mShareDialog;
    private boolean isChoose, isLogin;
    private int milkCount, milkPrice;
    private String milkName, milkType, milkUrl;

    public MyFragment2() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my2, container, false);
        milkList = new MilkUtil().getMilkList();

        initLeftRecyclerView();
        initRightRecyclerView();
        initView();
        readMilkInfo();//??????SharedPreferences???????????????????????????
        initBarClick();//??????????????????????????????

        /*//Recyclerview???????????????
        rv_right.addOnScrollListener(new RecyclerView.OnScrollListener() {//???????????????????????????
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // ?????????????????????
                int firstItemPosition = recyclerView.getChildLayoutPosition(recyclerView.getChildAt(0));
                int firstItemPosition_typeId = milkList.get(firstItemPosition).getM_type();//??????????????????????????????ID
                if (firstItemPosition_typeId != 0) {
                    for (int i = 0; i < 10; i++) {
                        if (firstItemPosition_typeId == i) {
                            for (int k = 0; k < 10; k++) {
                                View viewByPosition = leftManager.findViewByPosition(k);
                            }
                            leftAdapter.setCheckedPosition(i);//????????????????????????
                            rightAdapter.notifyDataSetChanged();
                            rv_left.scrollToPosition(i);//??????????????????
                            break;
                        }
                    }
                }
            }
        });*/

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        readMilkInfo();
    }

    //???????????????RecyclerView??????????????????
    private void initLeftRecyclerView() {
        rv_left = view.findViewById(R.id.rv_left);
        rv_left.setLayoutManager(leftManager);
        leftAdapter = new LeftRecyclerAdapter(getActivity());
        rv_left.setAdapter(leftAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rv_left.addItemDecoration(decoration);
    }

    //???????????????RecyclerView??????????????????
    private void initRightRecyclerView() {
        rv_right = view.findViewById(R.id.rv_right);
        rv_right.setLayoutManager(rightManager);
        rightAdapter = new RightRecyclerAdapter(getActivity(), milkList);
        rv_right.setAdapter(rightAdapter);
    }

    private void initView() {
        relativeLayout = view.findViewById(R.id.shop_progre);
        shopping_bag = view.findViewById(R.id.shopping_bag);
        shopping_price = view.findViewById(R.id.shopping_price);
        choice = view.findViewById(R.id.choice);
    }

    /**
     * ???SharedPreferences?????????????????????
     */
    public void readMilkInfo() {
        SharedPreferences shp = getActivity().getSharedPreferences("milkInfo", MODE_PRIVATE);
        isChoose = shp.getBoolean("isChoose", false);
        milkName = shp.getString("milkName", "");
        milkPrice = shp.getInt("milkPrice", 0);
        milkCount = shp.getInt("milkCount", 0);
        milkType = shp.getString("milkType", "");
        milkUrl = shp.getString("milkUrl", "");
        SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", MODE_PRIVATE);
        isLogin = sp.getBoolean("isLogin", false);
        if (isChoose && isLogin) {
            relativeLayout.setVisibility(View.VISIBLE);
        }else {
            relativeLayout.setVisibility(View.GONE);
        }
        shopping_price.setText(String.valueOf(milkPrice));
    }

    /**
     * ??????????????????????????????SharedPreferences???
     */
    private void saveMilkInfo(boolean isChoose, String milkName, int milkPrice, String milkType, int milkCount, String milkUrl) {
        SharedPreferences shp = getActivity().getSharedPreferences("milkInfo", MODE_PRIVATE);
        //???????????????int
        SharedPreferences.Editor editor = shp.edit();

        //????????????????????????????????????
        editor.putBoolean("isChoose", isChoose);
        //???????????????????????????????????????
        editor.putString("milkName", milkName);
        //????????????????????????????????????
        editor.putInt("milkPrice", milkPrice);
        //????????????????????????????????????
        editor.putInt("milkCount", milkCount);
        //????????????????????????????????????
        editor.putString("milkType", milkType);
        //????????????????????????????????????
        editor.putString("milkUrl", milkUrl);
        //????????????
        editor.apply();
    }

    //??????SharedPreferences?????????
    private void clearMilkInfo() {
        SharedPreferences sp = getActivity().getSharedPreferences("milkInfo", MODE_PRIVATE);
        if (sp != null) {
            sp.edit().clear().apply();
        }
    }

    //??????????????????????????????
    private void initBarClick() {
        shopping_bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SubmitActivity.class);
                startActivity(intent);
            }
        });
        shopping_price.setText(String.valueOf(milkPrice));
    }

    //???????????????
    private void showDialog() {
        initShareDialog();
        mShareDialog.show();
    }

    /**
     * ????????????????????????
     */
    private void initShareDialog() {
        mShareDialog = new Dialog(getContext(), R.style.dialog_bottom_full);
        mShareDialog.setCanceledOnTouchOutside(true); //???????????????????????????
        mShareDialog.setCancelable(true);             //????????? ???true
        Window window = mShareDialog.getWindow();      // ??????dialog?????????
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.share_animation);
        //??????????????????
        View view = View.inflate(getContext(), R.layout.dialog_bag, null);

        initDialogView(view);
        readMilkInfo();
        //??????????????????
        Glide.with(this).load(milkUrl).into(img_goods);
        goods_name.setText(milkName);
        goods_type.setText(milkType);
        jiage.setText(String.valueOf(milkPrice));
        shuliang.setText(String.valueOf(milkCount));
        //???????????????
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMilkInfo();//??????SharedPreferences????????????????????????????????????
                relativeLayout.setVisibility(View.GONE);//????????????????????????
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
                    Toast.makeText(getActivity(), "???????????????>_<", Toast.LENGTH_SHORT).show();
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
                    int now = Integer.parseInt(jiage.getText().toString()) / (count + 1);//????????????
                    String tot = Integer.toString(count * now);
                    jiage.setText(tot);
                    saveMilkInfo(true, milkName, count * now, milkType, count, milkUrl);
                } else {
                    clearMilkInfo();//??????SharedPreferences????????????????????????????????????
                    relativeLayout.setVisibility(View.GONE);//????????????????????????
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
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//??????????????????
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


}