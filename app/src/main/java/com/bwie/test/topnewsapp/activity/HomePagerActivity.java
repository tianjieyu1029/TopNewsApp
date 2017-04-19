package com.bwie.test.topnewsapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.test.topnewsapp.R;
import com.bwie.test.topnewsapp.utils.MyXUtils;

public class HomePagerActivity extends AppCompatActivity {

    private ImageView iv_back_include_head_login;
    private TextView tv_back_include_head_login;
    private TextView tv_right_include_head_login;
    private ImageView home_pager_image;
    private TextView home_pager_text;
    private CheckBox home_pager_check;
    private TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pager);
        initView();
        initData();
    }

    private void initData() {
        tv_back_include_head_login.setText("个人主页");
        iv_back_include_head_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);
        String qqName = preferences.getString("QQName", "");
        String qqPic = preferences.getString("QQPic", "");
        final boolean flag = preferences.getBoolean("flag", false);
        if (flag) {
            MyXUtils.imageXUtils(home_pager_image, qqPic, true);
            home_pager_text.setText(qqName);
            home_pager_check.setVisibility(View.VISIBLE);
            textview.setVisibility(View.GONE);
        } else {
            home_pager_image.setImageResource(R.mipmap.use_comment);
            home_pager_text.setText("");
            textview.setVisibility(View.VISIBLE);
            home_pager_check.setVisibility(View.GONE);
        }

        home_pager_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    Intent intent = new Intent(HomePagerActivity.this, AccountActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void initView() {
        iv_back_include_head_login = (ImageView) findViewById(R.id.iv_back_include_head_login);
        tv_back_include_head_login = (TextView) findViewById(R.id.tv_back_include_head_login);
        tv_right_include_head_login = (TextView) findViewById(R.id.tv_right_include_head_login);
        home_pager_image = (ImageView) findViewById(R.id.home_pager_image);
        home_pager_text = (TextView) findViewById(R.id.home_pager_text);
        home_pager_check = (CheckBox) findViewById(R.id.home_pager_check);
        textview = (TextView) findViewById(R.id.home_pager_text_gone);
    }
}
