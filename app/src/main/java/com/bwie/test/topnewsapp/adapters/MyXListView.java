package com.bwie.test.topnewsapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.test.topnewsapp.R;
import com.bwie.test.topnewsapp.beans.MyShop;
import com.bwie.test.topnewsapp.utils.MyXUtils;

import java.util.ArrayList;

/**
 * Created by tianjieyu on 2017/4/24.
 */

public class MyXListView extends BaseAdapter {
    private Context context;
    private ArrayList<MyShop> list;
    public static final int TYPE_1 = 0;
    public static final int TYPE_2 = 1;

    public MyXListView(Context context, ArrayList<MyShop> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getPic().equals("null")){
            return TYPE_2;
        }
        return TYPE_1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        int viewType = getItemViewType(position);
        if (convertView == null) {
            holder = new ViewHolder();
            switch (viewType) {
                case TYPE_1:
                    convertView = View.inflate(context, R.layout.shop_item1_layout, null);
                    holder.imageView = (ImageView) convertView.findViewById(R.id.shop_item_image);
                    holder.textView1 = (TextView) convertView.findViewById(R.id.shop_item_text1);
                    holder.textView2 = (TextView) convertView.findViewById(R.id.shop_item_text2);

                    break;
                case TYPE_2:
                    convertView = View.inflate(context, R.layout.shop_item2_layout, null);
                    holder.textView1 = (TextView) convertView.findViewById(R.id.shop_item2_text1);
                    holder.textView2 = (TextView) convertView.findViewById(R.id.shop_item2_text2);

                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        switch (viewType) {
            case TYPE_1:
                MyXUtils.imageXUtils(holder.imageView, list.get(position).getPic(), false);
                holder.textView1.setText(list.get(position).getTitle());
                holder.textView2.setText(list.get(position).getName() + "   " + list.get(position).getData());
                break;
            case TYPE_2:
                holder.textView1.setText(list.get(position).getTitle());
                holder.textView2.setText(list.get(position).getName() + "   " + list.get(position).getData());
                break;
        }

        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView2;
    }
}
