package com.bwie.test.topnewsapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.NetworkUtils;
import com.bwie.test.topnewsapp.adapters.MyIndicatorAdapter;
import com.bwie.test.topnewsapp.adapters.MyViewPagerAdapter;
import com.bwie.test.topnewsapp.beans.NewsBean;
import com.bwie.test.topnewsapp.beans.TitleBean;
import com.bwie.test.topnewsapp.beans.TitleDB;
import com.bwie.test.topnewsapp.beans.UserBean;
import com.bwie.test.topnewsapp.fragments.FragmentModel;
import com.bwie.test.topnewsapp.fragments.LeftFragment;
import com.bwie.test.topnewsapp.utils.GsonUtils;
import com.bwie.test.topnewsapp.utils.ImmersionStatusBar;
import com.bwie.test.topnewsapp.utils.MyXUtils;
import com.bwie.test.topnewsapp.utils.Night_styleutils;
import com.bwie.test.topnewsapp.utils.URLUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;

public class MainActivity extends SlidingFragmentActivity {
    private int theme = 0;
    private ImageView mine_image;
    private LinearLayout title_layout;
    private ImageView search_image;
    private MagicIndicator magic_indicator;
    private ImageView expanded_menu_image;
    private ViewPager viewPager;
    private CommonNavigator mCommonNavigator;
    private SlidingMenu menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImmersionStatusBar.setStatusBar(this,Color.parseColor("#CE2E2A"));

        initView();
        initDB();
        initSlidingMenu();
        boolean connected = NetworkUtils.isConnected();
        if (connected){
            initData();
            Log.d("走没走？", "onCreate: ");
         }

    }
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);//进入动画
        finish();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        startActivity(intent);
    }
    private void initDB() {

        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig();
        daoConfig.setDbName("TopNews.db")
                .setDbVersion(1)
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                    }
                });
        DbManager db = x.getDb(daoConfig);
        //DbManager dbManager = MyXUtils.dataBaseXUtils("TopNews.db", 1);
        UserBean userBean = new UserBean();
        userBean.setName("1");
        userBean.setPwd("1111");
        userBean.setPhone("1");
        userBean.setPic("1111");
        try {
            db.save(userBean);
        } catch (DbException e) {
            e.printStackTrace();
        }
        Log.d("mainActivity----->", "onCreate: "+userBean.toString());
    }

    private void readDatabase() {
        TitleDB titles = new TitleDB();
        DbManager dbManager = MyXUtils.dataBaseXUtils("TopNews.db", 1);
        try {
            ArrayList<TitleDB> titleDB = (ArrayList<TitleDB>) titles.getTitleDB(dbManager);
            ArrayList<NewsBean> list = new ArrayList<>();
            ArrayList<String> stringList = new ArrayList<>();
            for (int i=0;i<titleDB.size();i++){
               list.add(new NewsBean(titleDB.get(i).getTitle(),titleDB.get(i).getUri()));
                stringList.add(titleDB.get(i).getTitle());
            }
            initIndicator(stringList);
            initFragment(list);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
    //侧滑
    private void initSlidingMenu() {

        setBehindContentView(R.layout.menu_frame_left);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.menu_frame, new LeftFragment()).commit();
        // 实例化滑动菜单对象
        menu = getSlidingMenu();
        // 设置可以左右滑动的菜单
        menu.setMode(SlidingMenu.LEFT);
        // 设置滑动菜单视图的宽度
        int widthPixels = this.getResources().getDisplayMetrics().widthPixels;
        menu.setBehindWidth(widthPixels / 7 * 6);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        // 设置触摸屏幕的模式,这里设置为全屏
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        // 设置下方视图的在滚动时的缩放比例
        menu.setBehindScrollScale(0.0f);

        mine_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.toggle();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                   menu.setTouchModeAbove(
                            SlidingMenu.TOUCHMODE_FULLSCREEN);
                } else {
                    // 当在其他位置的时候，设置不可以拖拽出来(SlidingMenu.TOUCHMODE_NONE)，或只有在边缘位置才可以拖拽出来TOUCHMODE_MARGIN
                    menu.setTouchModeAbove(
                            SlidingMenu.TOUCHMODE_NONE);
                    menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    //指示器
    private void initIndicator(ArrayList<String> list) {
        magic_indicator.setBackgroundColor(Color.parseColor("#ffffff"));
        mCommonNavigator = new CommonNavigator(this);
        mCommonNavigator.setSkimOver(true);
        MyIndicatorAdapter adapter = new MyIndicatorAdapter(list,viewPager);
        mCommonNavigator.setAdapter(adapter);
        magic_indicator.setNavigator(mCommonNavigator);
        ViewPagerHelper.bind(magic_indicator, viewPager);
    }
    //添加fragment
    private void initFragment(ArrayList<NewsBean> list) {
        ArrayList<Fragment> fragList = new ArrayList<>();
        for (int i =0;i<list.size();i++){
            FragmentModel fragmentModel = FragmentModel.newInstance(list.get(i).getKey(),list.get(i).getTitle());
            fragList.add(fragmentModel);
        }
        MyViewPagerAdapter pagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragList);
        viewPager.setAdapter(pagerAdapter);
    }
    //请求数据
    private void initData() {
        new MyXUtils().httpXUtilsPOST(URLUtils.URL_TITLE, "uri", "news", new MyXUtils.MyHttpCallback() {
            @Override
            public void onSuccess(String result) {
                Log.d("这里呢？", "onSuccess: "+result);
                TitleBean titleBean = GsonUtils.gsonToBean(result, TitleBean.class);
                ArrayList<TitleBean.ResultBean.DateBean> date = (ArrayList<TitleBean.ResultBean.DateBean>) titleBean.getResult().getDate();
                Log.d("有没有？", "onSuccess: "+date.toString());
                DbManager dbManager = MyXUtils.dataBaseXUtils("TopNews.db", 1);
                try {
                    dbManager.dropDb();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                for (int i=0;i<date.size();i++){
                    TitleDB titleDB = new TitleDB();
                    titleDB.setTid(date.get(i).getId());
                    titleDB.setTitle(date.get(i).getTitle());
                    titleDB.setUri(date.get(i).getUri());
                    try {
                        dbManager.save(titleDB);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {
                Log.d("错了？", "onError: "+errorMsg);
            }

            @Override
            public void onFinished() {
                readDatabase();

            }
        });
    }

    private void initView() {
        mine_image = (ImageView) findViewById(R.id.mine_image);
        title_layout = (LinearLayout) findViewById(R.id.title_layout);
        search_image = (ImageView) findViewById(R.id.search_image);
        magic_indicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        expanded_menu_image = (ImageView) findViewById(R.id.expanded_menu_image);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        expanded_menu_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TitleActivity.class);
                startActivity(intent);
            }
        });

    }
}
