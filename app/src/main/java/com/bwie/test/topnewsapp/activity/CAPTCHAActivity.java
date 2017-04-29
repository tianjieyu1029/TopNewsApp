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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static com.mob.tools.utils.ResHelper.getStringRes;


public class CAPTCHAActivity extends AppCompatActivity implements View.OnClickListener {


    public int T = 60; //倒计时时长
    boolean flag = true;
   /* private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
           *//* if (msg.what == 1)
                Toast.makeText(CAPTCHAActivity.this, "回调完成", Toast.LENGTH_SHORT).show();
            else*//*
            if (msg.what == 2) {
                flag = true;
            } *//*else if (msg.what == 3) {
                Toast.makeText(CAPTCHAActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 4) {
                Toast.makeText(CAPTCHAActivity.this, "返回支持发送国家验证码", Toast.LENGTH_SHORT).show();
            }*//**//*else{
                Toast.makeText(CAPTCHAActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
            }*//*
        }

    };*/


    private String phone;
    private ImageView iv_back_include_head_login;
    private TextView tv_back_include_head_login;
    private TextView tv_right_include_head_login;
    private TextView tv_phone_register_number_captcha;
    private EditText et_captcha_captcha;
    private Button btn_resend_captcha;
    private EditText et_pwd_captcha;
    private Button btn_login_captcha;
    private int theme = 0;
    private String pwd;
    private EditText et_email_captcha;
    private String email;

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
       // new Thread(new MyCountDownTimer()).start();
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
        et_email_captcha = (EditText) findViewById(R.id.et_email_captcha);
    }

    private void submit() {
        // validate
        String code = et_captcha_captcha.getText().toString().trim();
        pwd = et_pwd_captcha.getText().toString().trim();
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
        email = et_email_captcha.getText().toString().trim();
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "请输入邮箱", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean emailAddress = isEmailAddress(email);
        if (!emailAddress){
            Toast.makeText(this, "请输入正确的邮箱", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something
        SMSSDK.submitVerificationCode("86", phone, code);
        flag = false;

    }
    //判断邮箱
    public static boolean isEmailAddress(String url) {
        String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(url);
        return matcher.matches();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_resend_captcha:
                //new Thread(new MyCountDownTimer()).start();
               // SMSSDK.getVerificationCode("86",phone);
                sendCAPTCHA(phone);

                break;
            case R.id.btn_login_captcha:
                submit();
               /* if (flag){
                    Toast.makeText(CAPTCHAActivity.this, "提交验证码成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CAPTCHAActivity.this, UserInfoSetActivity.class);
                    intent.putExtra("phone", phone);
                    intent.putExtra("pwd", pwd);
                    intent.putExtra("email",email);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(CAPTCHAActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                }*/
                break;
        }
    }

    private EventHandler eh;

    private void sendCAPTCHA(String phone) {
        //回调函数


        eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        // SMSSDK.initSDK(this, "您的appkey", "您的appsecret");//初始化
        SMSSDK.registerEventHandler(eh); //注册短信回调
        SMSSDK.getVerificationCode("86", phone);//请求获取短信验证码

    }


    private void reminderText() {
        handlerText.sendEmptyMessageDelayed(1, 1000);
    }

    Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            //Log.e("event", "event="+event);
            if (result == SMSSDK.RESULT_COMPLETE) {
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功,验证通过
                    Toast.makeText(getApplicationContext(), "验证码校验成功", Toast.LENGTH_SHORT).show();
                    handlerText.sendEmptyMessage(2);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){//服务器验证码发送成功
                    reminderText();
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//返回支持发送验证码的国家列表
                    Toast.makeText(getApplicationContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                if(flag){
                    Toast.makeText(CAPTCHAActivity.this, "验证码获取失败，请重新获取", Toast.LENGTH_SHORT).show();
                }else{
                    ((Throwable) data).printStackTrace();
                    int resId = getStringRes(CAPTCHAActivity.this, "smssdk_network_error");
                    Toast.makeText(CAPTCHAActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    if (resId > 0) {
                        Toast.makeText(CAPTCHAActivity.this, resId, Toast.LENGTH_SHORT).show();
                    }
                }

            }

        }

    };

    Handler handlerText =new Handler(){
        public void handleMessage(Message msg) {
            if(msg.what==1){
                if(T>0){
                    btn_resend_captcha.setClickable(false);
                    btn_resend_captcha.setText("重新发送 " + T);
                    btn_resend_captcha.setTextColor(Color.GRAY);
                    T--;
                    handlerText.sendEmptyMessageDelayed(1, 1000);
                }else{
                    btn_resend_captcha.setClickable(true);
                    btn_resend_captcha.setText("重新发送");
                    btn_resend_captcha.setTextColor(Color.parseColor("#ff0000"));
                    T = 59;
                }
            }else{
                Intent intent = new Intent(CAPTCHAActivity.this, UserInfoSetActivity.class);
                intent.putExtra("phone", phone);
                intent.putExtra("pwd", pwd);
                intent.putExtra("email",email);
                startActivity(intent);
                finish();
            }
        }
    };

   /* class MyCountDownTimer implements Runnable {

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
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }

}

