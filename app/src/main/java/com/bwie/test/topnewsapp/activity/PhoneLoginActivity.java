package com.bwie.test.topnewsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.test.topnewsapp.R;
import com.bwie.test.topnewsapp.utils.Night_styleutils;

public class PhoneLoginActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView iv_back_include_head_login;
    private TextView tv_back_include_head_login;
    private TextView tv_right_include_head_login;
    private EditText et_number_phone_login;
    private EditText et_pwd_phone_login;
    private TextView tv_register_phone_login;
    private TextView tv_pwd_phone_login;
    private Button btn_login_phone_login;
    private int theme = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);
        initView();

    }


    private void initView() {

        iv_back_include_head_login = (ImageView) findViewById(R.id.iv_back_include_head_login);
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
        et_number_phone_login = (EditText) findViewById(R.id.et_number_phone_login);
        et_number_phone_login.setOnClickListener(this);
        et_pwd_phone_login = (EditText) findViewById(R.id.et_pwd_phone_login);
        et_pwd_phone_login.setOnClickListener(this);
        tv_register_phone_login = (TextView) findViewById(R.id.tv_register_phone_login);
        tv_register_phone_login.setOnClickListener(this);
        tv_pwd_phone_login = (TextView) findViewById(R.id.tv_pwd_phone_login);
        tv_pwd_phone_login.setOnClickListener(this);
        btn_login_phone_login = (Button) findViewById(R.id.btn_login_phone_login);
        btn_login_phone_login.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_phone_login:

                break;
            case R.id.tv_register_phone_login:
                Intent intent = new Intent(PhoneLoginActivity.this, PhoneRegisterActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_pwd_phone_login:
                Intent intent1 = new Intent(PhoneLoginActivity.this, PwdBackActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }

    private void submit() {
        // validate
        String phone = et_number_phone_login.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd = et_pwd_phone_login.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
