package com.bwie.test.topnewsapp.utils;

import android.app.Activity;
import android.os.Build;
import android.view.View;

/**
 * Created by tianjieyu on 2017/4/13.
 */

public class ImmersionStatusBar {
    public static void setStatusBar(Activity activity, int color){
        if (Build.VERSION.SDK_INT>=21){
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setStatusBarColor(color);
        }
    }
}
