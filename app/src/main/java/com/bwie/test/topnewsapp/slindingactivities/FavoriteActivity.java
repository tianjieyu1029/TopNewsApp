package com.bwie.test.topnewsapp.slindingactivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.test.topnewsapp.NextActivity;
import com.bwie.test.topnewsapp.R;
import com.bwie.test.topnewsapp.adapters.MyRecyclerAdapter;
import com.bwie.test.topnewsapp.beans.SQLiteContent;
import com.bwie.test.topnewsapp.utils.MySQLiteOpenHelper;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    private ImageView iv_back_include_head_login;
    private TextView tv_back_include_head_login;
    private TextView tv_right_include_head_login;
    private RecyclerView favorite_recycler;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        inData();
    }

    private void inData() {
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this);
        final SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.query("favor", null, null, null, null, null, null);
        final ArrayList<SQLiteContent> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            //title text,pic text,url text,author_name text
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String pic = cursor.getString(cursor.getColumnIndex("pic"));
            String author_name = cursor.getString(cursor.getColumnIndex("author_name"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            SQLiteContent content = new SQLiteContent();
            content.setTitle(title);
            content.setPic(pic);
            content.setUrl(url);
            content.setAuthor_name(author_name);
            list.add(content);
        }
        if (list.size() != 0) {
            favorite_recycler.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);
            final MyRecyclerAdapter adapter = new MyRecyclerAdapter(list);
            LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

            favorite_recycler.setLayoutManager(manager);
            favorite_recycler.setAdapter(adapter);
            adapter.setmOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(FavoriteActivity.this, NextActivity.class);
                    intent.putExtra("url", list.get(position).getUrl());
                    intent.putExtra("title", list.get(position).getAuthor_name());
                    intent.putExtra("content", list.get(position).getTitle());
                    intent.putExtra("pic", list.get(position).getPic());
                    startActivity(intent);
                }
            });
            adapter.setmOnItemLongClickListener(new MyRecyclerAdapter.OnItemLongClickListener() {
                @Override
                public void onItemLongClick(View view, int position) {
                    showNormalDialog(list,adapter,database,position);
                }
            });
        } else {
            layout.setVisibility(View.VISIBLE);
            favorite_recycler.setVisibility(View.GONE);
        }
    }
    private void showNormalDialog(final ArrayList<SQLiteContent> arrayList,
                                  final MyRecyclerAdapter adapter, final SQLiteDatabase database,
                                  final int position){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(FavoriteActivity.this);
        //normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("温馨提示");
        normalDialog.setMessage("是否删除此条目?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        database.delete("favor","url=?",new String[]{arrayList.get(position).getUrl()});

                        arrayList.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        dialog.dismiss();
                    }
                });
        // 显示
        normalDialog.show();
    }


    private void initView() {
        iv_back_include_head_login = (ImageView) findViewById(R.id.iv_back_include_head_login);
        iv_back_include_head_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_back_include_head_login = (TextView) findViewById(R.id.tv_back_include_head_login);
        tv_back_include_head_login.setText("我的收藏");
        tv_right_include_head_login = (TextView) findViewById(R.id.tv_right_include_head_login);
        //tv_right_include_head_login.setVisibility(View.VISIBLE);
        //tv_right_include_head_login.setText("编辑");
        favorite_recycler = (RecyclerView) findViewById(R.id.favorite_recycler);
        layout = (LinearLayout) findViewById(R.id.favorite_layout);
    }

}
