package com.bwie.test.topnewsapp.slindingactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.test.topnewsapp.NextActivity;
import com.bwie.test.topnewsapp.R;
import com.bwie.test.topnewsapp.utils.Night_styleutils;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_back_include_head_login;
    private TextView tv_back_include_head_login;
    private TextView tv_right_include_head_login;
    private CheckBox cb_digest_setting;
    private LinearLayout linear_typeface_setting;
    private LinearLayout linear_comment_setting;
    private LinearLayout linear_WLAN_setting;
    private LinearLayout linear_cache_setting;
    private CheckBox cb_inform_setting;
    private CheckBox cb_plug_in_setting;
    private CheckBox cb_optimize_setting;
    private CheckBox cb_collect_setting;
    private CheckBox cb_digglist_setting;
    private LinearLayout linear_versions_setting;
    private LinearLayout linear_apply_recommend_setting;
    private LinearLayout linear_help_setting;
    private TextView tv_webView_setting;
    private int theme = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        iv_back_include_head_login = (ImageView) findViewById(R.id.iv_back_include_head_login);
        tv_back_include_head_login = (TextView) findViewById(R.id.tv_back_include_head_login);
        tv_right_include_head_login = (TextView) findViewById(R.id.tv_right_include_head_login);
        cb_digest_setting = (CheckBox) findViewById(R.id.cb_digest_setting);
        linear_typeface_setting = (LinearLayout) findViewById(R.id.linear_typeface_setting);
        linear_comment_setting = (LinearLayout) findViewById(R.id.linear_comment_setting);
        linear_WLAN_setting = (LinearLayout) findViewById(R.id.linear_WLAN_setting);
        linear_cache_setting = (LinearLayout) findViewById(R.id.linear_cache_setting);
        cb_inform_setting = (CheckBox) findViewById(R.id.cb_inform_setting);
        cb_plug_in_setting = (CheckBox) findViewById(R.id.cb_plug_in_setting);
        cb_optimize_setting = (CheckBox) findViewById(R.id.cb_optimize_setting);
        cb_collect_setting = (CheckBox) findViewById(R.id.cb_collect_setting);
        cb_digglist_setting = (CheckBox) findViewById(R.id.cb_digglist_setting);
        linear_versions_setting = (LinearLayout) findViewById(R.id.linear_versions_setting);
        linear_apply_recommend_setting = (LinearLayout) findViewById(R.id.linear_apply_recommend_setting);
        linear_help_setting = (LinearLayout) findViewById(R.id.linear_help_setting);
        tv_webView_setting = (TextView) findViewById(R.id.tv_webView_setting);
        tv_back_include_head_login.setText("设置");
        tv_right_include_head_login.setVisibility(View.VISIBLE);
        tv_right_include_head_login.setText("意见反馈");
        tv_right_include_head_login.setOnClickListener(this);
        iv_back_include_head_login.setOnClickListener(this);
        linear_typeface_setting.setOnClickListener(this);
        linear_comment_setting.setOnClickListener(this);
        linear_WLAN_setting.setOnClickListener(this);
        linear_cache_setting.setOnClickListener(this);
        linear_versions_setting.setOnClickListener(this);
        linear_apply_recommend_setting.setOnClickListener(this);
        linear_help_setting.setOnClickListener(this);
        tv_webView_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_include_head_login:
                finish();
                break;
            case R.id.tv_webView_setting:
                Intent intent = new Intent(SettingActivity.this,NextActivity.class);
                startActivity(intent);
                break;
        }
    }


}
