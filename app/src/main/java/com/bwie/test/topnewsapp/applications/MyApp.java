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
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setQQZone("1106034403", "XbCJuzd68e1ipMWu");
        PlatformConfig.setSinaWeibo("2602983103", "8844474daf4e10dd6a5f41aeb3a0d663", "http://sns.whalecloud.com");

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
