package com.bwie.test.topnewsapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.test.topnewsapp.R;
import com.bwie.test.topnewsapp.utils.Night_styleutils;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


public class CAPTCHAActivity extends AppCompatActivity implements View.OnClickListener {


    public int T = 60; //倒计时时长
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
           /* if (msg.what == 1)
                Toast.makeText(CAPTCHAActivity.this, "回调完成", Toast.LENGTH_SHORT).show();
            else*/ if (msg.what == 2)
                Toast.makeText(CAPTCHAActivity.this, "提交验证码成功", Toast.LENGTH_SHORT).show();
            else if (msg.what == 3)
                Toast.makeText(CAPTCHAActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
            else if (msg.what == 4)
                Toast.makeText(CAPTCHAActivity.this, "返回支持发送国家验证码", Toast.LENGTH_SHORT).show();
        }

    };


    private String phone;
    private EditText pwdEdit;
    private ImageView iv_back_include_head_login;
    private TextView tv_back_include_head_login;
    private TextView tv_right_include_head_login;
    private TextView tv_phone_register_number_captcha;
    private EditText et_captcha_captcha;
    private Button btn_resend_captcha;
    private EditText et_pwd_captcha;
    private Button btn_login_captcha;
    private int theme = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captcha);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        tv_phone_register_number_captcha.setText(phone);
        sendCAPTCHA(phone);
        new Thread(new MyCountDownTimer()).start();
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
        tv_back_include_head_login.setText("手机号注册");
        tv_right_include_head_login = (TextView) findViewById(R.id.tv_right_include_head_login);
        tv_right_include_head_login.setOnClickListener(this);
        tv_phone_register_number_captcha = (TextView) findViewById(R.id.tv_phone_register_number_captcha);
        tv_phone_register_number_captcha.setOnClickListener(this);
        et_captcha_captcha = (EditText) findViewById(R.id.et_captcha_captcha);
        et_captcha_captcha.setOnClickListener(this);
        btn_resend_captcha = (Button) findViewById(R.id.btn_resend_captcha);
        btn_resend_captcha.setOnClickListener(this);
        et_pwd_captcha = (EditText) findViewById(R.id.et_pwd_captcha);
        et_pwd_captcha.setOnClickListener(this);
        btn_login_captcha = (Button) findViewById(R.id.btn_login_captcha);
        btn_login_captcha.setOnClickListener(this);
    }

    private void submit() {
        // validate
        String code = et_captcha_captcha.getText().toString().trim();
        String pwd = et_pwd_captcha.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pwd.length() < 6 || pwd.length() > 20) {
            Toast.makeText(this, "密码长度应大于6位且小于20位", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something
        SMSSDK.submitVerificationCode("86", phone, code);
      /*  DbManager dbManager = MyXUtils.dataBaseXUtils("TopNews.db", 1);
        UserBean userBean = new UserBean();
        userBean.setPhone(phone);
        userBean.setPwd(pwd);
        try {
            dbManager.save(userBean);
        } catch (DbException e) {
            e.printStackTrace();
        }*/
        Intent intent = new Intent(CAPTCHAActivity.this, UserInfoSetActivity.class);
        intent.putExtra("phone", phone);
        intent.putExtra("pwd", pwd);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_resend_captcha:
                new Thread(new MyCountDownTimer()).start();
                sendCAPTCHA(phone);

                break;
            case R.id.btn_login_captcha:
                submit();
                break;
        }
    }

    private void sendCAPTCHA(String phone) {
        //回调函数
        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    mHandler.sendEmptyMessage(1);
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        mHandler.sendEmptyMessage(2);

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        mHandler.sendEmptyMessage(3);

                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                        mHandler.sendEmptyMessage(4);
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        // SMSSDK.initSDK(this, "您的appkey", "您的appsecret");//初始化
        SMSSDK.registerEventHandler(eh); //注册短信回调
        SMSSDK.getVerificationCode("86", phone);//请求获取短信验证码
    }


    class MyCountDownTimer implements Runnable {

        @Override
        public void run() {

            //倒计时开始，循环
            while (T > 0) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        btn_resend_captcha.setClickable(false);
                        btn_resend_captcha.setText("重新发送 " + T);
                        btn_resend_captcha.setTextColor(Color.GRAY);
                    }
                });
                try {
                    Thread.sleep(1000); //强制线程休眠1秒，就是设置倒计时的间隔时间为1秒。
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                T--;
            }

            //倒计时结束，也就是循环结束
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    btn_resend_captcha.setClickable(true);
                    btn_resend_captcha.setText("重新发送");
                    btn_resend_captcha.setTextColor(Color.parseColor("#ff0000"));
                }
            });
            T = 60; //最后再恢复倒计时时长
        }
    }
}

