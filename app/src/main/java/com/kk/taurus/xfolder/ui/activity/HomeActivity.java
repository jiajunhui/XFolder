package com.kk.taurus.xfolder.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.kk.taurus.xfolder.R;

/**
 * Created by Taurus on 2017/6/6.
 */

public class HomeActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        afterSetContentView();
        initData();
    }

    protected void afterSetContentView() {
        mToolBar = (Toolbar) findViewById(R.id.id_toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.id_navigationView);
    }

    public void initData() {
        //设置显示Toolbar
        setSupportActionBar(mToolBar);
        // 设置Drawerlayout开关指示器，即Toolbar最左边的那个icon
        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        //给NavigationView填充顶部区域
        mNavigationView.inflateHeaderView(R.layout.layout_home_nav_header);
        //给NavigationView填充菜单
        mNavigationView.inflateMenu(R.menu.menu_home_nav);

        setNavigationViewListener();
    }

    private void setNavigationViewListener() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.remote_manager:

                        break;
                    case R.id.app_manager:

                        break;
                }
                // ment item点击后选中，并关闭Drawerlayout
                item.setChecked(true);
                mDrawerLayout.closeDrawers();

                return true;
            }
        });
    }
}
