package com.example.demo.MyFragment4;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demo.Login.LoginActivity;
import com.example.demo.Order.ShopActivity;
import com.example.demo.R;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class MyFragment4 extends Fragment {

    private View view;
    private TextView about;
    private TextView mxhy;
    private TextView hym;
    private TextView set;
    private TextView userName;
    private CircleImageView circleImageView;
    private Dialog mShareDialog;
    private Boolean isLoginStatus;//登录状态

    public MyFragment4() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my4, container, false);

        initView();
        LoginStatus();

        return view;
    }

    private void initView() {
        TextView service = view.findViewById(R.id.txt_service);//客服信息
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();// 单击按钮后 调用显示视图的 showDialog 方法
            }
        });
        about = view.findViewById(R.id.txt_about);//关于古茗
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });
        mxhy = view.findViewById(R.id.mxhy);//茗星会员页面
        mxhy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", MODE_PRIVATE);
                boolean isLoginStatus = sp.getBoolean("isLogin", false);
                if (isLoginStatus) {
                    Intent intent = new Intent(getActivity(), VipActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "请先登录!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        hym = view.findViewById(R.id.hym);//会员码页面
        hym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", MODE_PRIVATE);
                boolean isLoginStatus = sp.getBoolean("isLogin", false);
                if (isLoginStatus) {
                    Intent intent = new Intent(getActivity(), QRcodeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "请先登录!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        set = view.findViewById(R.id.txt_set);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
        userName = view.findViewById(R.id.user_name);//用户名 点击登录
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.putExtra("rg", 1);
                startActivity(intent);
            }
        });
        circleImageView = view.findViewById(R.id.user_image);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        LoginStatus();
    }

    //显示对话框
    private void showDialog() {
        if (mShareDialog == null) {
            initShareDialog();
        }
        mShareDialog.show();
    }

    /**
     * 初始化分享弹出框
     */
    private void initShareDialog() {
        mShareDialog = new Dialog(getActivity(), R.style.dialog_bottom_full);
        mShareDialog.setCanceledOnTouchOutside(true); //手指触碰到外界取消
        mShareDialog.setCancelable(true);             //可取消 为true
        Window window = mShareDialog.getWindow();      // 得到dialog的窗体
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.share_animation);

        View view = View.inflate(getActivity(), R.layout.dialog_service, null); //获取布局视图
        view.findViewById(R.id.know).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShareDialog != null && mShareDialog.isShowing()) {
                    mShareDialog.dismiss();
                }
            }
        });
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
    }

    /**
     * 判断登录状态
     */
    public void LoginStatus() {
        SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        isLoginStatus = sp.getBoolean("isLogin", false);
        if (isLoginStatus) {
            userName.setText(sp.getString("loginNickname", null));
            userName.setEnabled(false);
            circleImageView.setEnabled(false);
            circleImageView.setImageResource(R.drawable.touxiang);
        }
    }

}