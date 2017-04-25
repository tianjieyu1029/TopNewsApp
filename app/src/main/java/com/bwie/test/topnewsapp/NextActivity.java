package com.bwie.test.topnewsapp;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.test.topnewsapp.utils.ImmersionStatusBar;
import com.bwie.test.topnewsapp.utils.MySQLiteOpenHelper;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

public class NextActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressDialog dialog = null;
    private ImageView iv_back_include_head_login;
    private TextView tv_back_include_head_login;
    private TextView tv_right_include_head_login;
    private ImageView next_share_image;
    private CheckBox checkBox;
    private MySQLiteOpenHelper helper;
    private SharedPreferences favor;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        ImmersionStatusBar.setStatusBar(this, Color.parseColor("#CE2E2A"));
        helper = new MySQLiteOpenHelper(this);
        initView();
        UMShareConfig config = new UMShareConfig();
        //是否需求重复授权用户信息
        config.isNeedAuthOnGetUserInfo(true);
        //是否打开分享编辑页面
        config.isOpenShareEditActivity(true);

        UMShareAPI.get(this).setShareConfig(config);
//show_title_details_normal.png
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        final String url = intent.getStringExtra("url");
        final String title = intent.getStringExtra("title");
        final String content = intent.getStringExtra("content");
        final String pic = intent.getStringExtra("pic");
        if (webView != null) {
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    dialog.dismiss();
                }
            });
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);

            webView.getSettings().setBuiltInZoomControls(true);
            //可以访问的文件
            webView.getSettings().setAllowFileAccess(true);
            loadUrl(url);
            /*webView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });*/

        }
        //分享
        next_share_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UMImage image = new UMImage(NextActivity.this, url);//资源文件
                UMWeb umWeb = new UMWeb(url);
                umWeb.setTitle(title);
                umWeb.setDescription(content);
                //开启分享平台
                new ShareAction(NextActivity.this).withText("")
                        .withMedia(umWeb)
                        .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN,
                                SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QZONE,
                                SHARE_MEDIA.WEIXIN_FAVORITE, SHARE_MEDIA.FACEBOOK)
                        .setCallback(umShareListener).open();
            }
        });
        //收藏
        boolean flag = false;
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.query("favor", null, "url = ?",new String[]{url} , null, null, null);
        if (cursor.moveToNext()){
            flag = true;
        }else{
            flag = false;
        }
        checkBox.setChecked(flag);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    SQLiteDatabase database = helper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    //title text,pic text,url text,author_name text
                    values.put("author_name", title);
                    values.put("title",content);
                    values.put("url",url);
                    values.put("pic",pic);
                    database.insert("favor",null,values);
                }else{
                    SQLiteDatabase database = helper.getWritableDatabase();
                    if (database!=null) {
                        database.delete("favor","title=?",new String[]{content});
                    }
                }
            }
        });
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            Toast.makeText(NextActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(NextActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(NextActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

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
        tv_right_include_head_login.setText("  ");
        tv_right_include_head_login.setBackgroundResource(R.mipmap.show_title_details_normal);
        next_share_image = (ImageView) findViewById(R.id.next_share_image);
        checkBox = (CheckBox) findViewById(R.id.next_favor_checkBox);
    }

}
