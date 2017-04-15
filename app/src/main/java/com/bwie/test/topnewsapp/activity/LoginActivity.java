package com.bwie.test.topnewsapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.test.topnewsapp.R;
import com.bwie.test.topnewsapp.utils.Night_styleutils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_back_include_head_login;
    private TextView tv_back_include_head_login;
    private TextView tv_right_include_head_login;
    private Button btn_login_login;
    private Button btn_register_login;
    private ImageView iv_qq_login;
    private ImageView iv_wb_login;
    private ImageView iv_qb_login;
    private ImageView iv_rr_login;
    private int theme = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);
        boolean aBoolean = preferences.getBoolean("flag", false);
        if (aBoolean){
            finish();
        }
    }

    private void initView() {
        iv_back_include_head_login = (ImageView) findViewById(R.id.iv_back_include_head_login);
        //左上角返回键
        iv_back_include_head_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_back_include_head_login = (TextView) findViewById(R.id.tv_back_include_head_login);
        tv_back_include_head_login.setText("登录今日头条");
        tv_right_include_head_login = (TextView) findViewById(R.id.tv_right_include_head_login);
        tv_right_include_head_login.setOnClickListener(this);
        btn_login_login = (Button) findViewById(R.id.btn_login_login);
        btn_login_login.setOnClickListener(this);
        btn_register_login = (Button) findViewById(R.id.btn_register_login);
        btn_register_login.setOnClickListener(this);
        iv_qq_login = (ImageView) findViewById(R.id.iv_qq_login);
        iv_qq_login.setOnClickListener(this);
        iv_wb_login = (ImageView) findViewById(R.id.iv_wb_login);
        iv_wb_login.setOnClickListener(this);
        iv_qb_login = (ImageView) findViewById(R.id.iv_qb_login);
        iv_qb_login.setOnClickListener(this);
        iv_rr_login = (ImageView) findViewById(R.id.iv_rr_login);
        iv_rr_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_login:
                Intent intent = new Intent(LoginActivity.this, PhoneLoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_register_login:
                Intent intent1 = new Intent(LoginActivity.this, PhoneRegisterActivity.class);
                startActivity(intent1);
                break;

        }
    }
}
