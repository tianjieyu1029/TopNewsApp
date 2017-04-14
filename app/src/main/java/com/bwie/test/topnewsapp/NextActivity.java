package com.bwie.test.topnewsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NextActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressDialog dialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        initView();

        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        if(webView != null)
        {
            webView.setWebViewClient(new WebViewClient()
            {
                @Override
                public void onPageFinished(WebView view,String url)
                {
                    dialog.dismiss();
                }
            });

            loadUrl(url);
        }
    }
    public void loadUrl(String url)
    {
        if(webView != null)
        {

            dialog = ProgressDialog.show(this,null,"页面加载中，请稍后..");
            webView.loadUrl(url);
            webView.reload();
        }
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webView);
    }
}
