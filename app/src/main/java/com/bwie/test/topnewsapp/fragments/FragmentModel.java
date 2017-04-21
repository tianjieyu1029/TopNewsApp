package com.bwie.test.topnewsapp.fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.test.topnewsapp.NextActivity;
import com.bwie.test.topnewsapp.R;
import com.bwie.test.topnewsapp.adapters.MyRecyclerAdapter;
import com.bwie.test.topnewsapp.beans.JsonBean;
import com.bwie.test.topnewsapp.beans.SQLiteContent;
import com.bwie.test.topnewsapp.utils.GsonUtils;
import com.bwie.test.topnewsapp.utils.MySQLiteOpenHelper;
import com.bwie.test.topnewsapp.utils.MyXUtils;
import com.bwie.test.topnewsapp.utils.URLUtils;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;

import java.util.ArrayList;


/**
 * Created by tianjieyu on 2017/4/10.
 */

public class FragmentModel extends Fragment {

    private String path;
    private RecyclerView recycleView;
    private String title;
    private SQLiteDatabase database;
    private SuperSwipeRefreshLayout superSwipe;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        path = bundle.getString("url");
        title = bundle.getString("title");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragmentslayout, null);
        initView(view);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(getActivity());
        database = helper.getWritableDatabase();
        initData();
        initRefresh();
    }

    private void initRefresh() {
        superSwipe.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });
        View view = View.inflate(getActivity(), R.layout.refresh_foot_layout,null);
        superSwipe.setFooterView(view);
        superSwipe.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {

            }

            @Override
            public void onPushDistance(int distance) {

            }

            @Override
            public void onPushEnable(boolean enable) {

            }
        });
    }

    private void initData() {
        new MyXUtils().httpXUtilsPOST(URLUtils.URL_CONTENT, "uri", path, new MyXUtils.MyHttpCallback() {
            @Override
            public void onSuccess(String result) {
                JsonBean jsonBean = GsonUtils.gsonToBean(result, JsonBean.class);
                final ArrayList<JsonBean.ResultBean.DataBean> data = (ArrayList<JsonBean.ResultBean.DataBean>) jsonBean.getResult().getData();
                    ContentValues values = new ContentValues();
                final ArrayList<SQLiteContent> conList = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    //title text,pic text,url text,date text,category text,author_name text
                    values.put("title", data.get(i).getTitle());
                    values.put("pic", data.get(i).getThumbnail_pic_s());
                    values.put("url", data.get(i).getUrl());
                    values.put("date", data.get(i).getDate());
                    values.put("category", data.get(i).getCategory());
                    values.put("author_name", data.get(i).getAuthor_name());
                    database.insert("content", null, values);
                }
                for (int i=0;i<data.size();i++){
                    SQLiteContent e = new SQLiteContent();
                    e.setUrl(data.get(i).getUrl());
                    e.setCategory(data.get(i).getCategory());
                    e.setTitle(data.get(i).getTitle());
                    e.setAuthor_name(data.get(i).getAuthor_name());
                    e.setPic(data.get(i).getThumbnail_pic_s());
                    e.setDate(data.get(i).getDate());

                    conList.add(e);
                }

                MyRecyclerAdapter adapter = new MyRecyclerAdapter(conList);
                LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

                recycleView.setLayoutManager(manager);
                recycleView.setAdapter(adapter);
                adapter.setmOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), NextActivity.class);
                        intent.putExtra("url", conList.get(position).getUrl());
                        intent.putExtra("title",conList.get(position).getAuthor_name());
                        intent.putExtra("content",conList.get(position).getTitle());
                        getActivity().startActivity(intent);
                    }
                });

            }

            @Override
            public void onError(String errorMsg) {

            }

            @Override
            public void onFinished() {
               // readDatabase();
            }
        });
    }


    private void readDatabase() {
        /*ContentDB content = new ContentDB();
        DbManager dbManager = MyXUtils.dataBaseXUtils("TopNews.db", 1);
        try {
            ArrayList<ContentDB> list = (ArrayList<ContentDB>) content.getContent(dbManager);
            final ArrayList<ContentDB> contentDBs = new ArrayList<>();
            for (int i=0;i<list.size();i++){
                if (list.get(i).getCategory().equals(title)){
                    contentDBs.add(list.get(i));
                }
            }*/

        Cursor cursor = database.query("content", null, null, null, null, null, null);
        final ArrayList<SQLiteContent> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            //title text,pic text,url text,date text,category text,author_name text
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String pic = cursor.getString(cursor.getColumnIndex("pic"));
            String author_name = cursor.getString(cursor.getColumnIndex("author_name"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            String category = cursor.getString(cursor.getColumnIndex("category"));
            SQLiteContent content = new SQLiteContent();
            content.setPic(pic);
            content.setCategory(category);
            content.setAuthor_name(author_name);
            content.setTitle(title);
            content.setUrl(url);
            list.add(content);
        }
        final ArrayList<SQLiteContent> conList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCategory().equals(title)) {
                conList.add(list.get(i));
            }
        }


        MyRecyclerAdapter adapter = new MyRecyclerAdapter(conList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recycleView.setLayoutManager(manager);
        recycleView.setAdapter(adapter);
        adapter.setmOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), NextActivity.class);
                intent.putExtra("url", conList.get(position).getUrl());
                getActivity().startActivity(intent);
            }
        });
        cursor.close();
    }

    private void initView(View view) {
        recycleView = (RecyclerView) view.findViewById(R.id.recycleView);
        superSwipe = (SuperSwipeRefreshLayout) view.findViewById(R.id.superSwipe);
        /*ArrayList<SQLiteContent> arrayList = new ArrayList<>();
        MyRecyclerAdapter notAdapter = new MyRecyclerAdapter(arrayList);
        LinearLayoutManager notManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(notManager);
        recycleView.setAdapter(notAdapter);*/
    }

    public static FragmentModel newInstance(String url, String title) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        FragmentModel fragmentModel = new FragmentModel();
        fragmentModel.setArguments(bundle);
        return fragmentModel;
    }

}
