package com.bwie.test.topnewsapp.applications;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

import cn.smssdk.SMSSDK;

/**
 * Created by tianjieyu on 2017/4/12.
 */

public class MyApp extends Application {
    {
        PlatformConfig.setQQZone("1106034403", "XbCJuzd68e1ipMWu");
        //PlatformConfig.setQQZone("1106034403","XbCJuzd68e1ipMWu");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(getApplicationContext());
        x.Ext.init(this);
        x.Ext.setDebug(true);
        Config.DEBUG = true;
        SMSSDK.initSDK(this, "1cf72a14a34ed", "6891b8254a2fc86bb0becbe07ff72987");
        UMShareAPI.get(this);
    }
}
