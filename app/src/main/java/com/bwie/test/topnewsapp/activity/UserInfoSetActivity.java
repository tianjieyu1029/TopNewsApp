package com.bwie.test.topnewsapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.test.topnewsapp.R;
import com.bwie.test.topnewsapp.utils.CircleImageView;
import com.bwie.test.topnewsapp.utils.Night_styleutils;
import com.bwie.test.topnewsapp.utils.ProtraitUtils;
import com.bwie.test.topnewsapp.utils.VolleyUtils;

import java.util.HashMap;

public class UserInfoSetActivity extends AppCompatActivity {

    private ImageView iv_back_include_head_login;
    private TextView tv_back_include_head_login;
    private TextView tv_right_include_head_login;
    private CircleImageView iv_protrait_user_info_set_;
    private EditText et_name_user_info_set;
    private int theme = 0;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_set);
        initView();
        setImage();
        setLoginState();
    }

    private void setLoginState() {
        SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);
        edit = preferences.edit();
        tv_right_include_head_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }


    private void initView() {
        iv_back_include_head_login = (ImageView) findViewById(R.id.iv_back_include_head_login);
        iv_back_include_head_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_back_include_head_login = (TextView) findViewById(R.id.tv_back_include_head_login);
        tv_back_include_head_login.setText("设置个人信息");
        tv_right_include_head_login = (TextView) findViewById(R.id.tv_right_include_head_login);
        tv_right_include_head_login.setVisibility(View.VISIBLE);
        tv_right_include_head_login.setText("完成");
        iv_protrait_user_info_set_ = (CircleImageView) findViewById(R.id.iv_protrait_user_info_set_);
        et_name_user_info_set = (EditText) findViewById(R.id.et_name_user_info_set);

    }


    //设置头像
    private void setImage() {
        iv_protrait_user_info_set_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProtraitUtils.initPhone(UserInfoSetActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ProtraitUtils.getBackPhone(UserInfoSetActivity.this, requestCode, resultCode, data, iv_protrait_user_info_set_);
    }

    private void submit() {
        // validate
        final String user = et_name_user_info_set.getText().toString().trim();
        if (TextUtils.isEmpty(user)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO 将注册的手机号、密码、头像存入服务器
        //http://192.168.23.226/mobile/index.php?act=login&op=register&username=qqqqqqqq&password=qq&password_confirm=qq&client=android&email=45402354@qq.com

       // http://192.168.23.226/mobile/index.php?act=login&username=qqqqqqqq&password=qq&client=android
        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");
        String pwd = intent.getStringExtra("pwd");
        String email = intent.getStringExtra("email");
        HashMap<String,String> map = new HashMap<>();
        map.put("username",phone);
        map.put("password",pwd);
        map.put("password_confirm",pwd);
        map.put("client","android");
        map.put("email",email);
        VolleyUtils.post(this, VolleyUtils.LINK_MOBILE_REG, map, new VolleyUtils.MyCallback() {
            @Override
            public void onSuccess(String result) {
                Log.d("注册成功-------->", "onSuccess: "+result);
                Toast.makeText(UserInfoSetActivity.this, result, Toast.LENGTH_SHORT).show();
                edit.putBoolean("flag",true);
                edit.putString("QQName",user);
                edit.putInt("QQPic",R.mipmap.twitter_popover);
                edit.putInt("state",0);
                edit.commit();
                finish();
            }

            @Override
           public void onError(String errorMsg) {
                Log.d("注册失败------>", "onError: "+errorMsg);
                Toast.makeText(UserInfoSetActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
