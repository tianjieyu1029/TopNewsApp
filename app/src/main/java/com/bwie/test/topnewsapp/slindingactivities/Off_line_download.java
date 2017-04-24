package com.bwie.test.topnewsapp.slindingactivities;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bwie.test.topnewsapp.R;
import com.bwie.test.topnewsapp.adapters.My_off_line_adapter;
import com.bwie.test.topnewsapp.utils.MySQLiteOpenHelper;
import com.bwie.test.topnewsapp.utils.NetUtils;

import java.util.ArrayList;

public class Off_line_download extends AppCompatActivity {

    private ListView listView;
    private ImageView off_line_download_back;
    private TextView off_line_download_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_off_line_download);
        initView();
        initData();

    }

    private void initData() {
        MySQLiteOpenHelper mySQLlite = new MySQLiteOpenHelper(this);
        SQLiteDatabase db = mySQLlite.getReadableDatabase();
        ArrayList<String> sList = new ArrayList<>();

       /* for (int i = 0; i < 100; i++) {
            sList.add("条目" + i);
        }*/

        Cursor cursor = db.query("title", new String[]{"titleName"}, "state=?", new String[]{"0"}, null, null, null);
        while (cursor.moveToNext()) {
            String string = cursor.getString(cursor.getColumnIndex("titleName"));
            sList.add(string);
        }
        My_off_line_adapter adapter = new My_off_line_adapter(this, sList);
        listView.setAdapter(adapter);


    }

    private void initView() {
        listView = (ListView) findViewById(R.id.Off_line_download_listView);
        off_line_download_back = (ImageView) findViewById(R.id.Off_line_download_back);
        off_line_download_down = (TextView) findViewById(R.id.Off_line_download_down);

        //返回键
        off_line_download_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //下载键
        off_line_download_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean mobile = NetUtils.isMobile(Off_line_download.this);
                if (mobile) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Off_line_download.this);
                    builder.setMessage("移动网络离线下载会使用较多流量，是否继续？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    builder.show();
                }
            }
        });
    }
}
