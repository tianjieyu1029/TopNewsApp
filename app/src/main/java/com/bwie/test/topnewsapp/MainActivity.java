package com.bwie.test.topnewsapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.NetworkUtils;
import com.bwie.test.topnewsapp.activity.LoginActivity;
import com.bwie.test.topnewsapp.adapters.MyIndicatorAdapter;
import com.bwie.test.topnewsapp.adapters.MyViewPagerAdapter;
import com.bwie.test.topnewsapp.beans.SQLiteTitle;
import com.bwie.test.topnewsapp.beans.TitleBean;
import com.bwie.test.topnewsapp.fragments.FragmentModel;
import com.bwie.test.topnewsapp.utils.GsonUtils;
import com.bwie.test.topnewsapp.utils.ImmersionStatusBar;
import com.bwie.test.topnewsapp.utils.MySQLiteOpenHelper;
import com.bwie.test.topnewsapp.utils.MyXUtils;
import com.bwie.test.topnewsapp.utils.Night_styleutils;
import com.bwie.test.topnewsapp.utils.URLUtils;
import com.bwie.test.topnewsapp.utils.UiUtils;
import com.bwie.test.topnewsapp.utils.XCRoundImageView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int theme = 0;
    private ImageView mine_image;
    private LinearLayout title_layout;
    private ImageView search_image;
    private MagicIndicator magic_indicator;
    private ImageView expanded_menu_image;
    private ViewPager viewPager;
    private CommonNavigator mCommonNavigator;
    private SlidingMenu menu;
    private RelativeLayout layout;
    private SharedPreferences.Editor edit;
    private boolean flag;
    private SQLiteDatabase database;
    private ImageView phone_image;
    private ImageView qq_image;
    private ImageView weibo_image;
    private CheckBox more_login_check;
    private RadioButton button;
    private XCRoundImageView after_login_image;
    private TextView after_login_text;
    private LinearLayout after_login_layout;
    private LinearLayout before_login_layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImmersionStatusBar.setStatusBar(this,Color.parseColor("#CE2E2A"));
        //初始化控件
        initView();
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(MainActivity.this);
        database = helper.getWritableDatabase();
        //侧滑
        initSlidingMenu();
        //判断是否有网络
        boolean connected = NetworkUtils.isConnected();
        //判断是否第一次进入
        SharedPreferences preferences = getSharedPreferences("http", MODE_PRIVATE);
        edit = preferences.edit();
        flag = preferences.getBoolean("first", false);
        if (flag){
            //读取数据库
            readDatabase();
        }else {
            if (connected){
                //添加数据
                initData();
                Log.d("走没走？", "onCreate: ");
            }
        }
        //所有的监听事件
        onClickAll();
    }

    //夜间模式
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);//进入动画
        finish();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        startActivity(intent);
    }

    //读取数据库
    private void readDatabase() {
       /* TitleDB titles = new TitleDB();
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
        }*/
        //MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this);
        //SQLiteDatabase database = helper.getWritableDatabase();
        //String query = "select * from title where state=0";
        Cursor cursor = database.query(
                "title", null, "state=?", new String[]{"0"}, null, null, null);
        //titleName text,uri text,state Integer
        ArrayList<SQLiteTitle> list = new ArrayList<>();
        while (cursor.moveToNext()){
            String titleName = cursor.getString(cursor.getColumnIndex("titleName"));
            String uri = cursor.getString(cursor.getColumnIndex("uri"));
            String state = cursor.getString(cursor.getColumnIndex("state"));
            SQLiteTitle titles = new SQLiteTitle(titleName,uri,state);
            list.add(titles);
        }
        Log.d("title---->", "readDatabase: "+list.size());
        initIndicator(list);
        initFragment(list);
        database.close();
    }
    //侧滑
    private void initSlidingMenu() {

       /* setBehindContentView(R.layout.menu_frame_left);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.menu_frame, new LeftFragment()).commit();*/
        // 实例化滑动菜单对象
        //menu = getSlidingMenu();
        menu = new SlidingMenu(this);
        menu.attachToActivity(MainActivity.this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.slidingmenu);
        initViewSlidingMenu(menu);
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
        if (! menu.isSecondaryMenuShowing()){
            menu.showContent();
        }else{
            menu.showSecondaryMenu();
        }
        //点击侧滑
        mine_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.toggle();
            }
        });
        //处理侧滑与Viewpager的冲突
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
    private void initIndicator(ArrayList<SQLiteTitle> list) {
        if (theme ==1){
            magic_indicator.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        if (theme==2){
            magic_indicator.setBackgroundColor(Color.parseColor("#000000"));
        }
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0;i<list.size();i++){
            strings.add(list.get(i).getTitleName());
        }
        mCommonNavigator = new CommonNavigator(this);
        mCommonNavigator.setSkimOver(true);
        MyIndicatorAdapter adapter = new MyIndicatorAdapter(strings,viewPager);
        mCommonNavigator.setAdapter(adapter);
        magic_indicator.setNavigator(mCommonNavigator);
        ViewPagerHelper.bind(magic_indicator, viewPager);
    }
    //添加fragment
    private void initFragment(ArrayList<SQLiteTitle> list) {
        ArrayList<Fragment> fragList = new ArrayList<>();
        for (int i =0;i<list.size();i++){
            FragmentModel fragmentModel = FragmentModel.newInstance(list.get(i).getUri(),list.get(i).getTitleName());
            fragList.add(fragmentModel);
        }
        MyViewPagerAdapter pagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragList);
        viewPager.setAdapter(pagerAdapter);
    }
    //请求数据
    private void initData() {
        new MyXUtils().httpXUtilsPOST(URLUtils.URL_TITLE,
                "uri", "news", new MyXUtils.MyHttpCallback() {
            @Override
            public void onSuccess(String result) {
                Log.d("这里呢？", "onSuccess: "+result);
                TitleBean titleBean = GsonUtils.gsonToBean(result, TitleBean.class);
                ArrayList<TitleBean.ResultBean.DateBean> date = (ArrayList<TitleBean.ResultBean.DateBean>) titleBean.getResult().getDate();
                Log.d("有没有？", "onSuccess: "+date.toString());
                //titleName text,uri text,state Integer
                ContentValues values = new ContentValues();
                for (int i=0;i<date.size();i++){

                    values.put("titleName",date.get(i).getTitle());
                    values.put("uri",date.get(i).getUri());
                    values.put("state",date.get(i).getId()%2 + "");
                    database.insert("title",null,values);
                }
                edit.putBoolean("first",true);
                edit.commit();
              /*  DbManager dbManager = MyXUtils.dataBaseXUtils("TopNews.db", 1);
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
                }*/
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
    //点击事件
    private void onClickAll(){
        //更多方式登录
        more_login_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        //夜间模式button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.switchAppTheme(MainActivity.this);

                reload();
            }
        });
    }
    //判断显示隐藏
    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);
        //flag是判断登录状态
        boolean flag = preferences.getBoolean("flag", false);
        if (flag) {
            after_login_layout.setVisibility(View.VISIBLE);
            before_login_layout.setVisibility(View.GONE);
        }else{
            after_login_layout.setVisibility(View.GONE);
            before_login_layout.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        layout = (RelativeLayout) findViewById(R.id.main_title);
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
                finish();
            }
        });
    }

    /**
     *侧滑的初始化View
     * @param view
     */
    private void initViewSlidingMenu(View view) {
        phone_image = (ImageView) view.findViewById(R.id.phone_image);
        qq_image = (ImageView) view.findViewById(R.id.qq_image);
        weibo_image = (ImageView) view.findViewById(R.id.weibo_image);
        more_login_check = (CheckBox) view.findViewById(R.id.more_login_check);
        button = (RadioButton) view.findViewById(R.id.night_button);
        after_login_image = (XCRoundImageView) view.findViewById(R.id.after_login_image);
        after_login_text = (TextView) view.findViewById(R.id.after_login_text);
        after_login_layout = (LinearLayout) view.findViewById(R.id.after_login_layout);
        before_login_layout = (LinearLayout) view.findViewById(R.id.before_login_layout);
    }
}
