package com.bwie.test.topnewsapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * data:2017/4/24
 */
public class NetUtils {

    public static boolean isWiFi(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.getType() == manager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    //判断是不是流量
    public static boolean isMobile(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.getType() == manager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }
}
