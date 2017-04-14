package com.bwie.test.topnewsapp.applications;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.bwie.test.topnewsapp.beans.UserBean;
import com.bwie.test.topnewsapp.utils.MyXUtils;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import cn.smssdk.SMSSDK;

/**
 * Created by tianjieyu on 2017/4/12.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(getApplicationContext());
        x.Ext.init(this);
        x.Ext.setDebug(true);
        SMSSDK.initSDK(this, "1cf72a14a34ed", "6891b8254a2fc86bb0becbe07ff72987");

    }
}
