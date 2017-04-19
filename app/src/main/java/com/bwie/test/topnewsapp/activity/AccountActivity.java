package com.bwie.test.topnewsapp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.test.topnewsapp.R;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_back_include_head_login;
    private TextView tv_back_include_head_login;
    private Button acc_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        initView();
    }

    private void initView() {
        iv_back_include_head_login = (ImageView) findViewById(R.id.iv_back_include_head_login);
        tv_back_include_head_login = (TextView) findViewById(R.id.tv_back_include_head_login);
        acc_button = (Button) findViewById(R.id.acc_button);

        acc_button.setOnClickListener(this);
        iv_back_include_head_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_back_include_head_login.setText("账号管理");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.acc_button:
                SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);
                SharedPreferences.Editor edit = preferences.edit();
                edit.clear();
                edit.putBoolean("flag",false);
                edit.commit();
                finish();
                break;
        }
    }
}
