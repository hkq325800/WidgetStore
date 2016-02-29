package com.kerchin.yellownote.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.kerchin.yellownote.R;
import com.kerchin.yellownote.adapter.MyFragmentPagerAdapter;
import com.kerchin.yellownote.fragment.FolderFragment;
import com.kerchin.yellownote.fragment.NoteFragment;
import com.kerchin.yellownote.global.MyApplication;

import java.util.ArrayList;

import static com.kerchin.yellownote.utilities.NormalUtils.getModVersion;
import static com.kerchin.yellownote.utilities.NormalUtils.isIntentAvailable;
import static com.kerchin.yellownote.utilities.NormalUtils.openMiuiPermissionActivity;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static Long mExitTime = (long) 0;//退出时间
    public SearchView mSearchView;
    private FloatingActionButton btnAdd;
    public MenuItem btnSearch, btnSort, btnDelete;
    private ViewPager viewPager;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NoteFragment noteFragment;
    private FolderFragment folderFragment;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected void setContentView() {
        isExisTitle = false;
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initializeClick() {

    }


    @Override
    protected void initializeView() {
        btnAdd = (FloatingActionButton) findViewById(R.id.fab);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        viewPager = (ViewPager) findViewById(R.id.mViewPager);
    }

    @Override
    protected void initializeData() {
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        noteFragment = NoteFragment.newInstance(null);
        folderFragment = FolderFragment.newInstance(null);
        fragments.add(noteFragment);
        fragments.add(folderFragment);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(
                getSupportFragmentManager(), fragments);
        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position,
                                       float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                MyApplication.thisPosition = position;
                String name = position == 0 ? "笔记" : "笔记本";
                toolbar.setTitle(name);
                if (position == 0) {
                    //delete初始化
                    noteFragment.deleteViewHide();
                    noteFragment.onResume();//
                    btnSort.setVisible(true);
                    btnAdd.setOnClickListener(noteFragment.getAddClickListener());
                    toolbar.setOnMenuItemClickListener(noteFragment.getToolbarItemClickListener());
                    mSearchView.setOnQueryTextListener(noteFragment.getQueryTextListener());
                } else if (position == 1) {
                    btnDelete.setVisible(false);
                    btnSort.setVisible(false);
                    btnAdd.setOnClickListener(folderFragment.getAddClickListener());
                    toolbar.setOnMenuItemClickListener(folderFragment.getToolbarItemClickListener());
                    mSearchView.setOnQueryTextListener(folderFragment.getQueryTextListener());
                    if (FolderFragment.isChanged4folder) {
                        folderFragment.dataGot();
                        FolderFragment.isChanged4folder = false;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("笔记");
        if (!MyApplication.isLogin()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public MenuItem getBtnSearch() {
        return btnSearch;
    }

    public MenuItem getBtnSort() {
        return btnSort;
    }

    public MenuItem getBtnDelete() {
        return btnDelete;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        btnSearch = toolbar.getMenu().getItem(0);
        btnSort = toolbar.getMenu().getItem(1);
        btnDelete = toolbar.getMenu().getItem(2);
        mSearchView = (SearchView) MenuItemCompat.getActionView(btnSearch);
        btnDelete.setVisible(false);

        toolbar.setOnMenuItemClickListener(noteFragment.getToolbarItemClickListener());
        btnAdd.setOnClickListener(noteFragment.getAddClickListener());
        mSearchView.setOnQueryTextListener(noteFragment.getQueryTextListener());

        //mSearchView.setIconified(true);//取消方法
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_note) {
            viewPager.setCurrentItem(0);
        } else if (id == R.id.nav_folder) {
            viewPager.setCurrentItem(1);
        } else if (id == R.id.nav_logout) {
            MyApplication.logout();
            Intent intent = new Intent();
            intent.setClass(this, LoginActivity.class);
            intent.putExtra("logoutFlag", true);//使得欢迎界面不显示
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_setting) {
            openMiuiPermissionActivity(this);
        }

        if (id != R.id.nav_logout)
            drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (MyApplication.thisPosition == 0) {
                if (noteFragment.isDelete) {
                    noteFragment.deleteViewHide();
                    return true;
                } else {
                    if ((System.currentTimeMillis() - mExitTime) > 2000) {//间隔超过2s
                        Toast.makeText(this, "再点击一次退出应用", Toast.LENGTH_SHORT).show();
                        mExitTime = System.currentTimeMillis();
                    } else {
                        finish();
                    }
                    return true;
                }
            } else {
                if ((System.currentTimeMillis() - mExitTime) > 2000) {//间隔超过2s
                    Toast.makeText(this, "再点击一次退出应用", Toast.LENGTH_SHORT).show();
                    mExitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
