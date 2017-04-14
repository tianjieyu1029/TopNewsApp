package com.bwie.test.topnewsapp.utils;

import android.widget.ImageView;

import com.bwie.test.topnewsapp.R;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by tianjieyu on 2017/4/12.
 */

public class MyXUtils {

    public void httpXUtilsPOST(String url, String key, String values, final MyHttpCallback callback){

        RequestParams params = new RequestParams(url);
        params.addQueryStringParameter(key,values);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                callback.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callback.onError(ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                callback.onFinished();

            }
        });

    }
    public interface MyHttpCallback {
        void onSuccess(String result);

        void onError(String errorMsg);

        void onFinished();
    }

    /**
     * 数据库
     * @param dbName
     * @param version
     * @return
     */
    public static DbManager dataBaseXUtils(String dbName, int version) {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig();
        daoConfig.setDbName(dbName)
                .setDbVersion(version)
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
            @Override
            public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

            }
        });
        DbManager db = x.getDb(daoConfig);
        return db;
    }

    /**
     * 图片加载
     * @param imageView 控件
     * @param iconUrl   url
     * @param isCircluar 矩形图片四个角是否为圆角
     */
    public static void imageXUtils(ImageView imageView, String iconUrl, boolean isCircluar) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setUseMemCache(true)
                .setCircular(isCircluar)
                .setCrop(true)
                .setLoadingDrawableId(R.mipmap.big_loadpic_full_listpage)
                .setFailureDrawableId(R.mipmap.big_loadpic_full_listpage)
                .build();
        x.image().bind(imageView, iconUrl, imageOptions);
    }
}
