package com.bwie.test.topnewsapp.utils;

import android.content.Context;
import android.os.Bundle;

/**
 * data:2017/4/14
 * author:郭彦君(Administrator)
 * function:
 */
public class Night_styleutils {
    private Context context;
    private Bundle savedInstanceState;

    public static void changeStyle(Context context, int theme, Bundle savedInstanceState) {

        //切换主题必须放在onCreate()之前
        if (savedInstanceState == null) {
            theme = UiUtils.getAppTheme(context);
        } else {
            theme = savedInstanceState.getInt("theme");
        }
        context.setTheme(theme);

    }

}
