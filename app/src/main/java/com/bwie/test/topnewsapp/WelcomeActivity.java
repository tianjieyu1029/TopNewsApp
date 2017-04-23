package com.bwie.test.topnewsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bwie.test.topnewsapp.utils.ImmersionStatusBar;

public class WelcomeActivity extends AppCompatActivity {

    private ImageView wel_image;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ImmersionStatusBar.setStatusBar(this, Color.TRANSPARENT);
        initView();
        initData();

    }

    private void initData() {
        handler.sendEmptyMessageDelayed(0,3000);
        SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);
        int state = preferences.getInt("state", 0);
        if (state==0){
            int qqPic = preferences.getInt("QQPic", 0);
            Glide.with(WelcomeActivity.this).load(qqPic).asBitmap().centerCrop().
                    into(new BitmapImageViewTarget(wel_image) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            wel_image.setImageDrawable(circularBitmapDrawable);
                        }
                    });
        }else{
            String qqPic = preferences.getString("QQPic", "");
            Glide.with(WelcomeActivity.this).load(qqPic).asBitmap().centerCrop().
                    into(new BitmapImageViewTarget(wel_image) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            wel_image.setImageDrawable(circularBitmapDrawable);
                        }
                    });
        }
    }

    private void initView() {
        wel_image = (ImageView) findViewById(R.id.wel_image);
    }
}
