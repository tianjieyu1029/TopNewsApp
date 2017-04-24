package com.bwie.test.topnewsapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import com.bwie.test.topnewsapp.R;

import java.util.ArrayList;

/**
 * data:2017/4/24
 * author:郭彦君(Administrator)
 * function:
 */
public class My_off_line_adapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> list;

    public My_off_line_adapter(Context context, ArrayList<String> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.off_line_download_listview_layout, null);
            holder = new ViewHolder();
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.Off_line_download_CB);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.checkBox.setText(list.get(position));
        return convertView;
    }

    class ViewHolder {
        CheckBox checkBox;
    }
}
