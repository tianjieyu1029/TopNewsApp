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

public class PwdBackActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView iv_back_include_head_login;
    private TextView tv_back_include_head_login;
    private TextView tv_right_include_head_login;
    private EditText et_number_pwd_back;
    private ImageView iv_clear_pwd_back;
    private Button btn_login_pwd_back;
    private int theme = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_back);
        initView();
        onClickAll();
    }

    private void onClickAll() {
        iv_clear_pwd_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_number_pwd_back.setText("");
            }
        });
        iv_back_include_head_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initView() {
        iv_back_include_head_login = (ImageView) findViewById(R.id.iv_back_include_head_login);
        tv_back_include_head_login = (TextView) findViewById(R.id.tv_back_include_head_login);
        tv_right_include_head_login = (TextView) findViewById(R.id.tv_right_include_head_login);
        et_number_pwd_back = (EditText) findViewById(R.id.et_number_pwd_back);
        iv_clear_pwd_back = (ImageView) findViewById(R.id.iv_clear_pwd_back);
        btn_login_pwd_back = (Button) findViewById(R.id.btn_login_pwd_back);

        btn_login_pwd_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_pwd_back:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String back = et_number_pwd_back.getText().toString().trim();
        if (TextUtils.isEmpty(back)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something
        Intent intent = new Intent(PwdBackActivity.this,PwdBackCAPTCHA.class);
        intent.putExtra("phone",back);
        startActivity(intent);
        finish();
    }
}
