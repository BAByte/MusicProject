package com.waterteam.musicproject;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;


import com.waterteam.musicproject.bean.AllMediaBean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.waterteam.musicproject.customview.BottomBar;
import com.waterteam.musicproject.util.StatusBarUtil;
import com.waterteam.musicproject.viewpagers.MyPageAdapter;
import com.waterteam.musicproject.viewpagers.album.page.AlbumPageFragment;
import com.waterteam.musicproject.viewpagers.artist.page.ArtistPageFragment;
import com.waterteam.musicproject.viewpagers.songs.page.SongsPageFragment;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private boolean debug = false;
    ViewPager viewPager;
    List<Fragment> fragmentList = new ArrayList<Fragment>();
    MyPageAdapter fragmentPagerAdapter;
    BottomBar bottomBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AllMediaBean mySongsData;


        //设置为沉浸式状态栏，设置了状态栏颜色及字体颜色
        StatusBarUtil.setStatusBarLightMode(this);

        //为了解决程序被杀死，再回来后空指针异常的问题我希望你这样再处理下数据源，反正这里必须要这样写
        if (savedInstanceState != null) {
            mySongsData = (AllMediaBean) savedInstanceState.getSerializable("datas");
            AllMediaBean.getInstance().setArtists(mySongsData.getArtists());
            AllMediaBean.getInstance().setAlbums(mySongsData.getAlbums());
            AllMediaBean.getInstance().setSongs(mySongsData.getSongs());
            Log.d(TAG, "从保存的获取");
        } else if (debug) { //下面的代码你写不写都行，我只是测试而已
            mySongsData = AllMediaBean.getInstance();
            Log.d(TAG, "艺术家=" + mySongsData.getArtists().size());
            Log.d(TAG, "专辑=" + mySongsData.getAlbums().size());
            Log.d(TAG, "歌曲=" + mySongsData.getSongs().size());
        }
        initView();
    }

    //必须写该方法
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("datas", AllMediaBean.getInstance());
    }

    @Override
    public void onBackPressed() {
        if (bottomBar.getIsPullUp()) {
            bottomBar.pullDown();
        } else {
            super.onBackPressed();
        }

    }

    /**
     * initView()方法用于初始化主活动界面
     *
     * @param
     * @return
     * @throws
     * @author CNT on 2017/12/5.
     */
    private void initView() {
        viewPager = (ViewPager) this.findViewById(R.id.viewPager_MainActivity);
        bottomBar = (BottomBar) this.findViewById(R.id.MainActivity_bottomBar);

        //往viewPager的数据列表中添加3个碎片；
        fragmentList.add(new ArtistPageFragment());
        fragmentList.add(new AlbumPageFragment());
        fragmentList.add(new SongsPageFragment());

        fragmentPagerAdapter = new MyPageAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(fragmentPagerAdapter);
    }
}
