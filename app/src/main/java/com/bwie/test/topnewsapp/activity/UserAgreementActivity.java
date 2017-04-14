package com.bwie.test.topnewsapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.bwie.test.topnewsapp.R;
import com.bwie.test.topnewsapp.utils.Night_styleutils;

public class UserAgreementActivity extends AppCompatActivity {

    private WebView web_agreement_phone_register;
    private RelativeLayout activity_user_agreement;
    private int theme = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);
        initView();
        initData();
    }

    private void initData() {
        web_agreement_phone_register.loadUrl("www.baidu.com");
    }

    private void initView() {
        web_agreement_phone_register = (WebView) findViewById(R.id.web_agreement_phone_register);
        activity_user_agreement = (RelativeLayout) findViewById(R.id.activity_user_agreement);
    }
}
