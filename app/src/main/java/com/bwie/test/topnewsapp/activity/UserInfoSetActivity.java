package com.bwie.test.topnewsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.test.topnewsapp.R;
import com.bwie.test.topnewsapp.utils.Night_styleutils;
import com.bwie.test.topnewsapp.utils.ProtraitUtils;
import com.bwie.test.topnewsapp.utils.XCRoundImageView;

public class UserInfoSetActivity extends AppCompatActivity {

    private ImageView iv_back_include_head_login;
    private TextView tv_back_include_head_login;
    private TextView tv_right_include_head_login;
    private XCRoundImageView iv_protrait_user_info_set_;
    private EditText et_name_user_info_set;
    private int theme = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_set);
        initView();
        setImage();
        initData();
    }

    private void initData() {
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
        iv_protrait_user_info_set_ = (XCRoundImageView) findViewById(R.id.iv_protrait_user_info_set_);
        et_name_user_info_set = (EditText) findViewById(R.id.et_name_user_info_set);
        tv_right_include_head_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    //设置头像
    private void setImage(){
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
        ProtraitUtils.getBackPhone(UserInfoSetActivity.this,requestCode,resultCode,data,iv_protrait_user_info_set_);
    }

    private void submit() {
        // validate
        String edit = et_name_user_info_set.getText().toString().trim();
        if (TextUtils.isEmpty(edit)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
      /*  DbManager dbManager = MyXUtils.dataBaseXUtils("TopNews.db", 1);
        UserBean userBean = new UserBean();
        try {
            userDB = (ArrayList<UserBean>) userBean.getUserDB(dbManager);
        } catch (DbException e) {
            e.printStackTrace();
        }
        boolean flag = false;
        for (int i=0;i<userDB.size();i++){
            if (edit.equals(userDB.get(i).getName())){
                flag=true;
                break;
            }
        }
        if (flag){
            Toast.makeText(this, "此用户名已注册", Toast.LENGTH_SHORT).show();
        }*/
        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");
        String pwd = intent.getStringExtra("pwd");
    }
}
