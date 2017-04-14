package com.bwie.test.topnewsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.RegexUtils;
import com.bwie.test.topnewsapp.R;
import com.bwie.test.topnewsapp.utils.Night_styleutils;

public class PhoneRegisterActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView iv_back_include_head_login;
    private TextView tv_back_include_head_login;
    private TextView tv_right_include_head_login;
    private EditText et_number_phone_register;
    private CheckBox check_yes_phone_register;
    private TextView tv_agreement_phone_register;
    private Button btn_login_phone_register;
    private int theme = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);
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
        tv_back_include_head_login.setText("手机号注册");
        tv_right_include_head_login = (TextView) findViewById(R.id.tv_right_include_head_login);
        tv_right_include_head_login.setOnClickListener(this);
        et_number_phone_register = (EditText) findViewById(R.id.et_number_phone_register);
        et_number_phone_register.setOnClickListener(this);
        check_yes_phone_register = (CheckBox) findViewById(R.id.check_yes_phone_register);
        check_yes_phone_register.setOnClickListener(this);
        tv_agreement_phone_register = (TextView) findViewById(R.id.tv_agreement_phone_register);
        tv_agreement_phone_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhoneRegisterActivity.this,UserAgreementActivity.class);
                startActivity(intent);
            }
        });
        btn_login_phone_register = (Button) findViewById(R.id.btn_login_phone_register);
        btn_login_phone_register.setOnClickListener(this);
        check_yes_phone_register.setChecked(false);
        check_yes_phone_register.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_phone_register:

                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String code = et_number_phone_register.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something
       /* DbManager dbManager = MyXUtils.dataBaseXUtils("TopNews.db", 1);
        UserBean userBean = new UserBean();
        try {
            userDB = (ArrayList<UserBean>) userBean.getUserDB(dbManager);
        } catch (DbException e) {
            e.printStackTrace();
        }*/
        boolean flag = false;
        boolean mobileExact = RegexUtils.isMobileExact(code);
        if (mobileExact) {
            /*for (int i=0;i<userDB.size();i++){
                if (code.equals(userDB.get(i).getPhone())){
                    flag = true;
                    break;
                }
            }
            if (flag){
                Toast.makeText(this, "该号码已注册", Toast.LENGTH_SHORT).show();
                return;
            }*/

            Intent intent = new Intent(PhoneRegisterActivity.this, CAPTCHAActivity.class);
            intent.putExtra("phone", code);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
        }
    }


}
