package com.bwie.test.topnewsapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;

/**
 * Created by tianjieyu on 2017/4/10.
 */

public class MyIndicatorAdapter extends CommonNavigatorAdapter {
    private ArrayList<String> list;
    private ViewPager mViewPager;

    public MyIndicatorAdapter(ArrayList<String> list, ViewPager mViewPager) {
        this.list = list;
        this.mViewPager = mViewPager;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
        clipPagerTitleView.setText(list.get(index));

        clipPagerTitleView.setTextColor(Color.GRAY);
        clipPagerTitleView.setClipColor(Color.RED);


        clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(index);
            }
        });
        return clipPagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        return null;
    }
}
