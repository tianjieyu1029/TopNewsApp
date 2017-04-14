package com.bwie.test.topnewsapp.utils;

import com.google.gson.Gson;

/**
 * Created by tianjieyu on 2017/4/10.
 */

public class GsonUtils {
    public static Gson gson = new Gson();

    public static <T> T gsonToBean(String json, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(json, cls);
        }
        return t;
    }
}
