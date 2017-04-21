package com.bwie.test.topnewsapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.RegexUtils;
import com.bwie.test.topnewsapp.R;
import com.bwie.test.topnewsapp.utils.Night_styleutils;
import com.bwie.test.topnewsapp.utils.VolleyUtils;

import java.util.HashMap;

public class PhoneLoginActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView iv_back_include_head_login;
    private TextView tv_back_include_head_login;
    private TextView tv_right_include_head_login;
    private EditText et_number_phone_login;
    private EditText et_pwd_phone_login;
    private TextView tv_register_phone_login;
    private TextView tv_pwd_phone_login;
    private Button btn_login_phone_login;
    private int theme = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);
        initView();

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
        tv_back_include_head_login.setText("登录今日头条");
        tv_right_include_head_login = (TextView) findViewById(R.id.tv_right_include_head_login);
        tv_right_include_head_login.setOnClickListener(this);
        et_number_phone_login = (EditText) findViewById(R.id.et_number_phone_login);
        et_number_phone_login.setOnClickListener(this);
        et_pwd_phone_login = (EditText) findViewById(R.id.et_pwd_phone_login);
        et_pwd_phone_login.setOnClickListener(this);
        tv_register_phone_login = (TextView) findViewById(R.id.tv_register_phone_login);
        tv_register_phone_login.setOnClickListener(this);
        tv_pwd_phone_login = (TextView) findViewById(R.id.tv_pwd_phone_login);
        tv_pwd_phone_login.setOnClickListener(this);
        btn_login_phone_login = (Button) findViewById(R.id.btn_login_phone_login);
        btn_login_phone_login.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_phone_login:
                submit();
                break;
            case R.id.tv_register_phone_login:
                Intent intent = new Intent(PhoneLoginActivity.this, PhoneRegisterActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_pwd_phone_login:
                Intent intent1 = new Intent(PhoneLoginActivity.this, PwdBackActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }

    private void submit() {
        // validate
        String phone = et_number_phone_login.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd = et_pwd_phone_login.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean mobileExact = RegexUtils.isMobileExact(phone);
        /*if (!mobileExact){
            Toast.makeText(this, "手机号码有误", Toast.LENGTH_SHORT).show();
            return;
        }*/

        // http://192.168.23.226/mobile/index.php?act=login&username=qqqqqqqq&password=qq&client=android
        // TODO validate success, do something
        HashMap<String, String> map = new HashMap<>();
        map.put("username", phone);
        map.put("password", pwd);
        map.put("client", "android");
        VolleyUtils.post(this, VolleyUtils.LINK_MOBILE_LOGIN, map, new VolleyUtils.MyCallback() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(PhoneLoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);
                SharedPreferences.Editor edit = preferences.edit();
                edit.putInt("state", 0);
                edit.putBoolean("flag", true);
                edit.putString("QQName", "佚名");
                edit.putInt("QQPic", R.mipmap.twitter_popover);
                edit.commit();
                finish();
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(PhoneLoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
