package com.bwie.test.topnewsapp.slindingactivities;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.test.topnewsapp.R;
import com.bwie.test.topnewsapp.adapters.MyXListView;
import com.bwie.test.topnewsapp.beans.MyShop;
import com.bwie.test.topnewsapp.beans.ShopBean;
import com.bwie.test.topnewsapp.utils.ImmersionStatusBar;
import com.bwie.test.topnewsapp.utils.MySQLiteOpenHelper;
import com.bwie.test.topnewsapp.utils.MyXUtils;
import com.google.gson.Gson;

import java.util.ArrayList;

import xlistview.bawei.com.xlistviewlibrary.XListView;

public class ShoppingActivity extends AppCompatActivity {

    private XListView xListView;
    private int index= 0;
    private MySQLiteOpenHelper helper;
    private SharedPreferences.Editor edit;
    private ArrayList<MyShop> shopArrayList = new ArrayList<>();
    private MyXListView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        ImmersionStatusBar.setStatusBar(this, Color.parseColor("#CE2E2A"));
        helper = new MySQLiteOpenHelper(this);
        SharedPreferences preferences = getSharedPreferences("shop", MODE_PRIVATE);
        edit = preferences.edit();
        boolean flag = preferences.getBoolean("flag", true);
        initView();
        if (flag){
            initData(index);
        }else{
            readDatabase();
        }


    }
//http://www.93.gov.cn/93app/data.do?channelId=0&startNum
    private void initData(int index) {
        new MyXUtils().httpXUtilsShop(index+"", new MyXUtils.MyHttpCallback() {
            @Override
            public void onSuccess(String result) {
                edit.putBoolean("flag",false);
                edit.commit();
                Gson gson = new Gson();
                ShopBean shopBean = gson.fromJson(result, ShopBean.class);
                ArrayList<ShopBean.DataBean> data = (ArrayList<ShopBean.DataBean>) shopBean.getData();
                SQLiteDatabase database = helper.getWritableDatabase();
                for (int i=0;i<data.size();i++) {
                    ContentValues values = new ContentValues();
                    values.put("title",data.get(i).getTITLE());
                    values.put("pic",data.get(i).getIMAGEURL()+"");
                    values.put("date",data.get(i).getFROMNAME());
                    values.put("author_name",data.get(i).getSHOWTIME());
                    database.insert("shop",null,values);
                    MyShop shop = new MyShop();
                    shop.setTitle(data.get(i).getTITLE());
                    shop.setPic(data.get(i).getIMAGEURL()+"");
                    shop.setData(data.get(i).getFROMNAME());
                    shop.setName(data.get(i).getSHOWTIME());
                    shopArrayList.add(shop);
                }
               setAdapter();

            }

            @Override
            public void onError(String errorMsg) {

            }

            @Override
            public void onFinished() {
                //readDatabase();
            }
        });
    }

    private void setAdapter(){
        if (adapter==null) {
            adapter = new MyXListView(ShoppingActivity.this, shopArrayList);
            xListView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }
    private void readDatabase() {
        SQLiteDatabase database = helper.getWritableDatabase();
        ArrayList<MyShop> list = new ArrayList<>();
        Cursor cursor = database.query("shop", null, null, null, null, null, null);
        while (cursor.moveToNext()){
            //title text,pic text,date text,author_name text
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String pic = cursor.getString(cursor.getColumnIndex("pic"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String author_name = cursor.getString(cursor.getColumnIndex("author_name"));
            MyShop shop = new MyShop();
            shop.setTitle(title);
            shop.setPic(pic);
            shop.setData(date);
            shop.setName(author_name);
            list.add(shop);
        }
        MyXListView adapter = new MyXListView(ShoppingActivity.this, list);
        xListView.setAdapter(adapter);
    }

    private void initView() {
        xListView = (XListView) findViewById(R.id.xListView);
        ImageView imageView = (ImageView) findViewById(R.id.iv_back_include_head_login);
        TextView textView = (TextView) findViewById(R.id.tv_back_include_head_login);
        textView.setVisibility(View.GONE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        xListView.setPullRefreshEnable(true);
        xListView.setPullLoadEnable(true);
        xListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                SQLiteDatabase database = helper.getWritableDatabase();
                database.delete("shop",null,null);
                shopArrayList.clear();
                index = 0;
                initData(index);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xListView.stopRefresh();
                    }
                },2000);

            }

            @Override
            public void onLoadMore() {
                index++;
                initData(index);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xListView.stopLoadMore();
                    }
                },2000);
            }
        });
    }
}
