package com.yihaoer.wyh.wanandroid.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.jess.arms.di.component.AppComponent;
import com.yihaoer.wyh.wanandroid.R;
import com.yihaoer.wyh.wanandroid.app.base.SupportActivity;
import com.yihaoer.wyh.wanandroid.di.component.DaggerMainComponent;
import com.yihaoer.wyh.wanandroid.di.module.MainModule;
import com.yihaoer.wyh.wanandroid.mvp.contract.MainContract;
import com.yihaoer.wyh.wanandroid.mvp.presenter.MainPresenter;
import com.yihaoer.wyh.wanandroid.mvp.ui.fragment.ProjectFragment;
import com.yihaoer.wyh.wanandroid.mvp.ui.fragment.main.GuideFragment;
import com.yihaoer.wyh.wanandroid.mvp.ui.fragment.main.HomeFragment;

import java.util.TimerTask;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Author: Yihaoer
 * Description: 首页+侧滑栏的View层
 */
public class MainActivity extends SupportActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.dl_drawer)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav)
    NavigationView mNavigationView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_back)
    RelativeLayout mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.bottom_nav_bar)
    BottomNavigationBar mBottomNavBar;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    /**
     * 注意loadRootFragment的执行时机，如果在initData中执行的话，要保证SupportActivity中的onCreate()里先执行
     * mDelegate.onCreate(savedInstanceState);，再在SupportActivity中的onCreate(）里执行
     * super.onCreate(savedInstanceState);，这样做是为了先初始化mTransactionDelegate，否则当你调用
     * loadRootFragment时会出现mTransactionDelegate.loadRootTransaction为空的错误，因为它还没初始化。
     * <p>
     * 当然你也可以用下面这方法重写onCreate，就不用担心SupportActivity中的onCreate()里两行代码的执行顺序
     */
    //    @Override
    //    protected void onCreate(@Nullable Bundle savedInstanceState) {
    //        super.onCreate(savedInstanceState);
    //        initFragmentation();
    //    }
    @Override
    public void initData(Bundle savedInstanceState) {
        initToolbar();
        initNavigationView();
        initFragmentation();
        initBottomNavigationBar();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        mToolbarBack.setVisibility(View.GONE);
        mToolbarTitle.setText(R.string.home_page);
        setSupportActionBar(mToolbar);//必须在setNavigationOnClickListener前调用，点击事件才有效
        mToolbar.setNavigationIcon(R.mipmap.ic_toolbar_left_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    /**
     * 初始化fragment的显示区域和初始页面
     */
    private void initFragmentation() {
        HomeFragment homeFragment = findFragment(HomeFragment.class);
        if (homeFragment == null) {
            loadRootFragment(R.id.content_fl, HomeFragment.newInstance());
        }
    }

    /**
     * 初始化NavigationView
     */
    private void initNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_follow:
                        Toast.makeText(MainActivity.this, "我的关注", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_collect:
                        Toast.makeText(MainActivity.this, "我的收藏", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_setup:
                        Toast.makeText(MainActivity.this, "系统设置", Toast.LENGTH_SHORT).show();
                        break;
                }
                closeDrawer();
                return false;
            }
        });
    }

    /**
     * 关闭左边侧滑栏
     */
    public void closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    /**
     * 初始化底部导航栏BottomNavigationBar
     */
    public void initBottomNavigationBar() {
        mBottomNavBar.addItem(new BottomNavigationItem(R.mipmap.ic_bottom_bar_home_active, "首页").setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_bottom_bar_home_inactive)))
                .addItem(new BottomNavigationItem(R.mipmap.ic_bottom_bar_project_active, "项目").setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_bottom_bar_project_inactive)))
                .addItem(new BottomNavigationItem(R.mipmap.ic_bottom_bar_guidance_active, "导航").setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_bottom_bar_guidance_inactive)))
                .addItem(new BottomNavigationItem(R.mipmap.ic_bottom_bar_hierarchy_active, "体系").setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_bottom_bar_hierarchy_inactive)))
                .setActiveColor(R.color.color_566ffe)
                .setInActiveColor(R.color.color_707070)
                .setFirstSelectedPosition(0)
                .initialise();
        mBottomNavBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case 0:
                        HomeFragment homeFragment = findFragment(HomeFragment.class);
                        if (homeFragment == null) {
                            start(HomeFragment.newInstance(), SupportFragment.SINGLETASK);
                        } else {
                            popTo(HomeFragment.class, false);
                        }
                        break;
                    case 1:
                        ProjectFragment projectFragment = findFragment(ProjectFragment.class);
                        if (projectFragment == null) {
                            popTo(HomeFragment.class, false, new TimerTask() {
                                @Override
                                public void run() {
                                    start(ProjectFragment.newInstance(), SupportFragment.SINGLETASK);
                                }
                            });
                        } else {
                            start(projectFragment, SupportFragment.SINGLETASK);
                        }
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }
}
