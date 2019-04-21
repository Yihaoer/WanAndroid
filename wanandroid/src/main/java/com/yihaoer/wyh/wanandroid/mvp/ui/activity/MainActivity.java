package com.yihaoer.wyh.wanandroid.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.jess.arms.di.component.AppComponent;
import com.yihaoer.wyh.wanandroid.R;
import com.yihaoer.wyh.wanandroid.app.base.SupportActivity;
import com.yihaoer.wyh.wanandroid.app.base.SupportFragment;
import com.yihaoer.wyh.wanandroid.di.component.DaggerMainComponent;
import com.yihaoer.wyh.wanandroid.di.module.MainModule;
import com.yihaoer.wyh.wanandroid.mvp.contract.MainContract;
import com.yihaoer.wyh.wanandroid.mvp.presenter.MainPresenter;
import com.yihaoer.wyh.wanandroid.mvp.ui.fragment.ProjectFragment;
import com.yihaoer.wyh.wanandroid.mvp.ui.fragment.main.GuideFragment;
import com.yihaoer.wyh.wanandroid.mvp.ui.fragment.main.HierarchyFragment;
import com.yihaoer.wyh.wanandroid.mvp.ui.fragment.main.HomeFragment;

import javax.annotation.Resource;

import butterknife.BindView;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

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
    @BindView(R.id.toolbar_left_rl)
    RelativeLayout mToolbarLeftRl;
    @BindView(R.id.toolbar_menu_iv)
    ImageView mToolbarMenuImage;
    @BindView(R.id.toolbar_back_iv)
    ImageView mToolbarBackImage;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.bottom_nav_bar)
    BottomNavigationBar mBottomNavBar;

    private SupportFragment[] fragments = new SupportFragment[4];
    private int[] tabs = {R.string.home_page, R.string.project_page, R.string.guide_page, R.string.hierarchy_page};
    private int prePosition = 0; //上一次点击的底部bar的position

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
        initFragmentation();
        initToolbar();
        initNavigationView();
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
        //        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
        //
        //        }
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        mToolbarBackImage.setVisibility(View.GONE);
        mToolbarMenuImage.setVisibility(View.VISIBLE);
        mToolbarTitle.setText(R.string.home_page);
        //        setSupportActionBar(mToolbar);//必须在setNavigationOnClickListener前调用，点击事件才有效
        //        mToolbar.setNavigationIcon(R.mipmap.ic_toolbar_left_menu);
        //        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                mDrawerLayout.openDrawer(GravityCompat.START);
        //            }
        //        });
        mToolbarLeftRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            //            loadRootFragment(R.id.content_fl, HomeFragment.newInstance(), true, false);
            fragments[0] = HomeFragment.newInstance();
            fragments[1] = ProjectFragment.newInstance();
            fragments[2] = GuideFragment.newInstance();
            fragments[3] = HierarchyFragment.newInstance();
            loadMultipleRootFragment(R.id.content_fl, 0,
                    fragments);
        } else {
            fragments[0] = homeFragment;
            fragments[1] = findFragment(ProjectFragment.class);
            fragments[2] = findFragment(GuideFragment.class);
            fragments[3] = findFragment(HierarchyFragment.class);
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
        mBottomNavBar.addItem(new BottomNavigationItem(R.mipmap.ic_bottom_bar_home_active, tabs[0]).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_bottom_bar_home_inactive)))
                .addItem(new BottomNavigationItem(R.mipmap.ic_bottom_bar_project_active, tabs[1]).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_bottom_bar_project_inactive)))
                .addItem(new BottomNavigationItem(R.mipmap.ic_bottom_bar_guidance_active, tabs[2]).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_bottom_bar_guidance_inactive)))
                .addItem(new BottomNavigationItem(R.mipmap.ic_bottom_bar_hierarchy_active, tabs[3]).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_bottom_bar_hierarchy_inactive)))
                .setActiveColor(R.color.color_566ffe)
                .setInActiveColor(R.color.color_707070)
                .setFirstSelectedPosition(0)
                .initialise();
        mBottomNavBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                showHideFragment(fragments[position], fragments[prePosition]);
                mToolbarTitle.setText(tabs[position]);
                prePosition = position; //记录上一次点击的底部bar的position
                //                switch (position) {
                //                    case 0:
                //                        mToolbarTitle.setText(R.string.home_page);
                //                        HomeFragment homeFragment = findFragment(HomeFragment.class);
                //                        if (homeFragment == null) {
                //                            start(HomeFragment.newInstance(),SupportFragment.SINGLETASK);
                //                        } else {
                ////                            popTo(HomeFragment.class, false, new TimerTask() {
                ////                                @Override
                ////                                public void run() {
                //                                    start(homeFragment,SupportFragment.SINGLETOP);
                ////                                }
                ////                            });
                ////                            popTo(HomeFragment.class,true);
                ////                            startWithPop(homeFragment);
                ////                                startWithPopTo(homeFragment,HomeFragment.class,false);
                //                        }
                //                        break;
                //                    case 1:
                //                        mToolbarTitle.setText(R.string.project_page);
                ////                        ProjectArticleFragment fragment = findFragment(ProjectArticleFragment.class);
                ////                        if (fragment == null){
                ////                            Log.i("asdsad", "fragment is alive = false");
                ////                        }else{
                ////                            Log.i("asdsad", "fragment is alive = true");
                ////                        }
                //                        ProjectFragment projectFragment = findFragment(ProjectFragment.class);
                //                        if (projectFragment == null) {
                //                            popTo(HomeFragment.class, false, new TimerTask() {
                //                                @Override
                //                                public void run() {
                //                                    start(ProjectFragment.newInstance());
                //                                }
                //                            });
                //                        } else {
                ////                            start(projectFragment,ISupportFragment.SINGLETASK);
                //                            popTo(ProjectFragment.class,false);
                //                        }
                //                        break;
                //                    case 2:
                //                        mToolbarTitle.setText(R.string.guide_page);
                //                        break;
                //                    case 3:
                //                        mToolbarTitle.setText(R.string.hierarchy_page);
                //                        break;
                //                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }
}
