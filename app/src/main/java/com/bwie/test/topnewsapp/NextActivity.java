package com.bwie.test.topnewsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class NextActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressDialog dialog = null;
    private ImageView iv_back_include_head_login;
    private TextView tv_back_include_head_login;
    private TextView tv_right_include_head_login;
    private ImageView next_share_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        initView();
//show_title_details_normal.png
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        if (webView != null) {
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    dialog.dismiss();
                }
            });

            loadUrl(url);
        }
    }

    public void loadUrl(String url) {
        if (webView != null) {

            dialog = ProgressDialog.show(this, null, "页面加载中，请稍后..");
            webView.loadUrl(url);
            webView.reload();
        }
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webView);
        iv_back_include_head_login = (ImageView) findViewById(R.id.iv_back_include_head_login);
        iv_back_include_head_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_back_include_head_login = (TextView) findViewById(R.id.tv_back_include_head_login);
        tv_back_include_head_login.setVisibility(View.GONE);
        tv_right_include_head_login = (TextView) findViewById(R.id.tv_right_include_head_login);
        tv_right_include_head_login.setVisibility(View.VISIBLE);
        tv_right_include_head_login.setText("");
        tv_right_include_head_login.setBackgroundResource(R.mipmap.show_title_details_normal);
        next_share_image = (ImageView) findViewById(R.id.next_share_image);

    }
}
