package com.bwie.test.topnewsapp.activity;

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

public class PwdBackCAPTCHA extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_back_include_head_login;
    private TextView tv_back_include_head_login;
    private TextView tv_right_include_head_login;
    private TextView tv_phone_pwd_back_captch;
    private EditText et_captch_pwd_back_captch;
    private Button btn_resend_pwd_back_captch;
    private EditText et_pwd_pwd_back_captch;
    private Button btn_login_pwd_back_captch;
    private int theme = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_back_captch);
        initView();
    }

    private void initView() {
        iv_back_include_head_login = (ImageView) findViewById(R.id.iv_back_include_head_login);
        tv_back_include_head_login = (TextView) findViewById(R.id.tv_back_include_head_login);
        tv_right_include_head_login = (TextView) findViewById(R.id.tv_right_include_head_login);
        tv_phone_pwd_back_captch = (TextView) findViewById(R.id.tv_phone_pwd_back_captch);
        et_captch_pwd_back_captch = (EditText) findViewById(R.id.et_captch_pwd_back_captch);
        btn_resend_pwd_back_captch = (Button) findViewById(R.id.btn_resend_pwd_back_captch);
        et_pwd_pwd_back_captch = (EditText) findViewById(R.id.et_pwd_pwd_back_captch);
        btn_login_pwd_back_captch = (Button) findViewById(R.id.btn_login_pwd_back_captch);

        btn_resend_pwd_back_captch.setOnClickListener(this);
        btn_login_pwd_back_captch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_resend_pwd_back_captch:

                break;
            case R.id.btn_login_pwd_back_captch:

                break;
        }
    }

    private void submit() {
        // validate
        String captch = et_captch_pwd_back_captch.getText().toString().trim();
        if (TextUtils.isEmpty(captch)) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd = et_pwd_pwd_back_captch.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
