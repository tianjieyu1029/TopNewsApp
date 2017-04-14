package com.bwie.test.topnewsapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.bwie.test.topnewsapp.MainActivity;
import com.bwie.test.topnewsapp.R;
import com.bwie.test.topnewsapp.activity.LoginActivity;
import com.bwie.test.topnewsapp.utils.UiUtils;

/**
 * Created by tianjieyu on 2017/4/13.
 */

public class LeftFragment extends Fragment {
    private ImageView phone_image;
    private ImageView qq_image;
    private ImageView weibo_image;
    private CheckBox more_login_check;
    private RadioButton button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.slidingmenu, null);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        more_login_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.switchAppTheme(getActivity());
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.reload();
            }
        });
    }

    private void initView(View view) {
        phone_image = (ImageView) view.findViewById(R.id.phone_image);
        qq_image = (ImageView) view.findViewById(R.id.qq_image);
        weibo_image = (ImageView) view.findViewById(R.id.weibo_image);
        more_login_check = (CheckBox) view.findViewById(R.id.more_login_check);
        button = (RadioButton) view.findViewById(R.id.night_button);

    }
}
