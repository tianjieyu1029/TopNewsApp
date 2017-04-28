package com.bwie.test.topnewsapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.NetworkUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bwie.test.topnewsapp.activity.HomePagerActivity;
import com.bwie.test.topnewsapp.activity.LoginActivity;
import com.bwie.test.topnewsapp.activity.PhoneLoginActivity;
import com.bwie.test.topnewsapp.adapters.MyIndicatorAdapter;
import com.bwie.test.topnewsapp.adapters.MyViewPagerAdapter;
import com.bwie.test.topnewsapp.beans.SQLiteTitle;
import com.bwie.test.topnewsapp.beans.TitleBean;
import com.bwie.test.topnewsapp.fragments.FragmentModel;
import com.bwie.test.topnewsapp.slindingactivities.FavoriteActivity;
import com.bwie.test.topnewsapp.slindingactivities.Off_line_download;
import com.bwie.test.topnewsapp.slindingactivities.SettingActivity;
import com.bwie.test.topnewsapp.slindingactivities.ShoppingActivity;
import com.bwie.test.topnewsapp.utils.GsonUtils;
import com.bwie.test.topnewsapp.utils.ImmersionStatusBar;
import com.bwie.test.topnewsapp.utils.MySQLiteOpenHelper;
import com.bwie.test.topnewsapp.utils.MyXUtils;
import com.bwie.test.topnewsapp.utils.Night_styleutils;
import com.bwie.test.topnewsapp.utils.URLUtils;
import com.bwie.test.topnewsapp.utils.UiUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.Map;

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
    private ImageView after_login_image;
    private TextView after_login_text;
    private LinearLayout after_login_layout;
    private LinearLayout before_login_layout;
    private SharedPreferences.Editor editor;
    private int state = 0;
    private static final int QQLOGINSTATE = 1;
    private static final int WBLOGINSTATE = 2;
    private static final int PHONESTATE = 0;
    private LinearLayout layout_menu4;
    private Button download;
    private LinearLayout layout_menu6;
    private Button settingButton;
    private boolean connected;
    private static final int REQUEST_CODE_PICK_CITY = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImmersionStatusBar.setStatusBar(this, Color.parseColor("#CE2E2A"));
        //初始化控件
        initView();
        MySQLiteOpenHelper helper =  new MySQLiteOpenHelper(MainActivity.this);
        database = helper.getWritableDatabase();

        //侧滑
        initSlidingMenu();
        //判断是否有网络
        connected = NetworkUtils.isConnected();
        //判断是否第一次进入
        SharedPreferences preferencesHttp = getSharedPreferences("http", MODE_PRIVATE);
        edit = preferencesHttp.edit();
        flag = preferencesHttp.getBoolean("first", false);
        if (flag) {
            //读取数据库
            readDatabase();
        } else {
            if (connected) {
                //添加数据
                initData();
                Log.d("走没走？", "onCreate: ");
            }else{
                Toast.makeText(this, "无网络连接", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }

    //读取数据库
    private void readDatabase() {
        Cursor cursor = database.query(
                "title", null, "state=?", new String[]{"0"}, null, null, null);
        //titleName text,uri text,state Integer
        ArrayList<SQLiteTitle> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String titleName = cursor.getString(cursor.getColumnIndex("titleName"));
            String uri = cursor.getString(cursor.getColumnIndex("uri"));
            String state = cursor.getString(cursor.getColumnIndex("state"));
            SQLiteTitle titles = new SQLiteTitle(titleName, uri, state);
            list.add(titles);
        }
        Log.d("title---->", "readDatabase: " + list.size());
        initIndicator(list);
        initFragment(list);
        //database.close();
    }

    @Override
    protected void onPause() {
        super.onPause();
        database.close();
    }

    //侧滑
    private void initSlidingMenu() {

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
        if (!menu.isSecondaryMenuShowing()) {
            menu.showContent();
        } else {
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
        if (theme == 1) {
            magic_indicator.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        if (theme == 2) {
            magic_indicator.setBackgroundColor(Color.parseColor("#000000"));
        }
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            strings.add(list.get(i).getTitleName());
        }
        mCommonNavigator = new CommonNavigator(this);
        mCommonNavigator.setSkimOver(true);
        MyIndicatorAdapter adapter = new MyIndicatorAdapter(strings, viewPager);
        mCommonNavigator.setAdapter(adapter);
        magic_indicator.setNavigator(mCommonNavigator);
        ViewPagerHelper.bind(magic_indicator, viewPager);
    }

    //添加fragment
    private void initFragment(ArrayList<SQLiteTitle> list) {
        ArrayList<Fragment> fragList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            FragmentModel fragmentModel = FragmentModel.newInstance(list.get(i).getUri(), list.get(i).getTitleName());
            fragList.add(fragmentModel);
        }
        MyViewPagerAdapter pagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragList);
        viewPager.setAdapter(pagerAdapter);
    }

    //请求数据
    //改之前uri = news
    private void initData() {
        new MyXUtils().httpXUtilsPOST(URLUtils.URL_NEWS_TITLE,
                "uri", "title", new MyXUtils.MyHttpCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Log.d("这里呢？", "onSuccess: " + result);
                        TitleBean titleBean = GsonUtils.gsonToBean(result, TitleBean.class);
                        ArrayList<TitleBean.ResultBean.DateBean> date = (ArrayList<TitleBean.ResultBean.DateBean>) titleBean.getResult().getDate();
                        Log.d("有没有？", "onSuccess: " + date.toString());
                        //titleName text,uri text,state Integer
                        ContentValues values = new ContentValues();
                        for (int i = 0; i < date.size(); i++) {

                            values.put("titleName", date.get(i).getTitle());
                            values.put("uri", date.get(i).getUri());
                            values.put("state", date.get(i).getId() % 2 + "");
                            database.insert("title", null, values);
                        }
                        edit.putBoolean("first", true);
                        edit.commit();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        Log.d("错了？", "onError: " + errorMsg);
                    }

                    @Override
                    public void onFinished() {
                        readDatabase();

                    }
                });
    }

    //点击事件
    private void onClickAll() {
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
        //第三方QQ登录
        qq_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginQQ();
            }
        });
        //登录成功后点击layout
        after_login_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomePagerActivity.class);
                startActivity(intent);
            }
        });
        //第三方微博登录
        weibo_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWB();
            }
        });
        //手机号登录
        phone_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PhoneLoginActivity.class);
                startActivity(intent);
            }
        });
        //收藏页跳转
        layout_menu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
                startActivity(intent);
            }
        });
        //离线下载button
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Off_line_download.class);
                startActivity(intent);
            }
        });
        //商城页跳转
        layout_menu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShoppingActivity.class);
                startActivity(intent);
            }
        });
        //设置Button
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

    }


    //第三方QQ登录
    public void loginQQ() {
        state = QQLOGINSTATE;
        /*editor.putInt("state",QQLOGINSTATE);
        editor.commit();*/
        UMShareAPI.get(this).getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, umAuthListener);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
            String name = null;
            String iconurl = null;
            if (state == QQLOGINSTATE) {
                name = data.get("name");
                iconurl = data.get("iconurl");
                editor.putString("QQName", name);
                editor.putString("QQPic", iconurl);
                editor.putBoolean("flag", true);
                editor.commit();
            } else if (state == WBLOGINSTATE) {
                name = data.get("name");
                iconurl = data.get("iconurl");
                editor.putString("QQName", name);
                editor.putString("QQPic", iconurl);
                editor.putBoolean("flag", true);
                editor.commit();
            }
            editor.putInt("state", state);
            editor.commit();
            after_login_layout.setVisibility(View.VISIBLE);
            before_login_layout.setVisibility(View.GONE);
            setLoginInfo(name, iconurl);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    //设置登录信息
    private void setLoginInfo(String nickName, String figureurl_qq_1) {

        Glide.with(MainActivity.this).load(figureurl_qq_1).asBitmap().centerCrop().
                into(new BitmapImageViewTarget(mine_image) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        mine_image.setImageDrawable(circularBitmapDrawable);
                    }
                });

        Glide.with(MainActivity.this).load(figureurl_qq_1).asBitmap().centerCrop().
                into(new BitmapImageViewTarget(after_login_image) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        after_login_image.setImageDrawable(circularBitmapDrawable);
                    }
                });
        after_login_text.setText(nickName);
    }
    //第三方微博登录

    private void loginWB() {
        state = WBLOGINSTATE;
        /*editor.putInt("state",WBLOGINSTATE);
        editor.commit();*/
        UMShareAPI.get(this).getPlatformInfo(MainActivity.this, SHARE_MEDIA.SINA, umAuthListener);

    }

    //回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    //判断显示隐藏
    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences preferencesLogin = getSharedPreferences("config", MODE_PRIVATE);
        editor = preferencesLogin.edit();
        //flag是判断登录状态
        boolean flag = preferencesLogin.getBoolean("flag", false);
        int state = preferencesLogin.getInt("state", 0);
        if (flag) {
            after_login_layout.setVisibility(View.VISIBLE);
            before_login_layout.setVisibility(View.GONE);
            if (state == QQLOGINSTATE) {
                String qqName = preferencesLogin.getString("QQName", "");
                String qqPic = preferencesLogin.getString("QQPic", "");
                setLoginInfo(qqName, qqPic);
            } else if (state == WBLOGINSTATE) {
                String qqName = preferencesLogin.getString("QQName", "");
                String qqPic = preferencesLogin.getString("QQPic", "");
                setLoginInfo(qqName, qqPic);
            } else if (state == PHONESTATE) {
                String qqName = preferencesLogin.getString("QQName", "");
                int qqPic = preferencesLogin.getInt("QQPic", 0);
                Glide.with(MainActivity.this).load(qqPic).asBitmap().centerCrop().
                        into(new BitmapImageViewTarget(mine_image) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                mine_image.setImageDrawable(circularBitmapDrawable);
                            }
                        });
                Glide.with(MainActivity.this).load(qqPic).asBitmap().centerCrop().
                        into(new BitmapImageViewTarget(after_login_image) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                after_login_image.setImageDrawable(circularBitmapDrawable);
                            }
                        });
                after_login_text.setText(qqName);
            }
        } else {
            mine_image.setImageResource(R.mipmap.mine_titlebar_normal);
            after_login_layout.setVisibility(View.GONE);
            before_login_layout.setVisibility(View.VISIBLE);
        }
    }

    //初始化控件
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
                Intent intent = new Intent(MainActivity.this, TitleActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 侧滑的初始化View
     *
     * @param view
     */
    private void initViewSlidingMenu(View view) {
        phone_image = (ImageView) view.findViewById(R.id.phone_image);
        qq_image = (ImageView) view.findViewById(R.id.qq_image);
        weibo_image = (ImageView) view.findViewById(R.id.weibo_image);
        more_login_check = (CheckBox) view.findViewById(R.id.more_login_check);
        button = (RadioButton) view.findViewById(R.id.night_button);
        after_login_image = (ImageView) view.findViewById(R.id.after_login_image);
        after_login_text = (TextView) view.findViewById(R.id.after_login_text);
        after_login_layout = (LinearLayout) view.findViewById(R.id.after_login_layout);
        before_login_layout = (LinearLayout) view.findViewById(R.id.before_login_layout);
        layout_menu4 = (LinearLayout) findViewById(R.id.Layout_Menu4);
        download = (Button) findViewById(R.id.download_button);
        layout_menu6 = (LinearLayout) findViewById(R.id.Layout_Menu6);
        settingButton = (Button) findViewById(R.id.setting_button);
    }


    //双击退出
    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;
                    return true;
                } else {
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
}
