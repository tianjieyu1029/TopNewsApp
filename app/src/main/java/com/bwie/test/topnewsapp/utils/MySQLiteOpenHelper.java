package com.bwie.test.topnewsapp.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tianjieyu on 2017/4/14.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    public MySQLiteOpenHelper(Context context) {
        super(context, "TopNews.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_title = "create table title(id Integer primary key autoincrement," +
                "titleName text,uri text,state text)";
        db.execSQL(create_title);
        String create_content = "create table content(id Integer primary key autoincrement," +
                "title text,pic text,url text,date text,category text,author_name text)";
        db.execSQL(create_content);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
