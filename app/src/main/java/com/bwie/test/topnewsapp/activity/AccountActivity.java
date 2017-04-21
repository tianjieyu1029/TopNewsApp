package com.bwie.test.topnewsapp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.test.topnewsapp.R;
import com.bwie.test.topnewsapp.utils.Night_styleutils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_back_include_head_login;
    private TextView tv_back_include_head_login;
    private Button acc_button;
private int theme = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
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
    private void setExit(){
        UMShareAPI.get(AccountActivity.this).deleteOauth(this, SHARE_MEDIA.QQ, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Toast.makeText(AccountActivity.this, "注销成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.acc_button:
                setExit();
                SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);
                SharedPreferences.Editor edit = preferences.edit();
                edit.clear();
                edit.commit();
                finish();
                break;
        }
    }
}
