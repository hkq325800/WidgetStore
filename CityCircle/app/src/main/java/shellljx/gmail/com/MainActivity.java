package shellljx.gmail.com;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import shellljx.gmail.com.app.activity.BaseActivity;
import shellljx.gmail.com.app.activity.CreatePostActivity;
import shellljx.gmail.com.app.activity.LoginActivity;
import shellljx.gmail.com.app.adapter.MainPagerAdapter;
import shellljx.gmail.com.app.test.TestActivity;
import shellljx.gmail.com.app.util.NotificationHelper;
import shellljx.gmail.com.widget.ArcMenu.ArcMenu;
import shellljx.gmail.com.widget.BottomTabBar.BottomBar;
import shellljx.gmail.com.widget.BottomTabBar.BottomBarItem;
import shellljx.gmail.com.widget.R;


public class MainActivity extends BaseActivity implements BottomBar.OnItemSelectedListener, ArcMenu.OnMenuItemClickListener, View.OnClickListener, ViewPager.OnPageChangeListener {

    @Bind(R.id.arc_menu)
    ArcMenu arcMenu;
    @Bind(R.id.menu_content)
    FrameLayout menucoent;
    @Bind(R.id.menu_button)
    RelativeLayout menubutton;
    @Bind(R.id.buttom_bar)
    BottomBar bottomBar;
    @Bind(R.id.content)
    ViewPager viewPager;

    private IntentFilter intentFilter;
    private MainPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        setupView();
    }

    private void setupView() {
        intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        menubutton.setOnClickListener(this);
        bottomBar.setOnItemSelectedListener(this);
        arcMenu.setMenuItemClickListener(this);
        viewPager.setAdapter(mAdapter);
        //viewpager预加载后面的第几页
        viewPager.setOffscreenPageLimit(3);
        viewPager.setOnPageChangeListener(this);
    }

    public void init() {
        mAdapter = new MainPagerAdapter(getSupportFragmentManager());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.menu_button) {
            menuContentClicked();
        }
    }

    public void menuContentClicked() {
        menucoent.setVisibility(menucoent.getVisibility() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE);
        arcMenu.toggleMenu(300);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(300);
        rotateAnimation.setFillAfter(true);
        menubutton.startAnimation(rotateAnimation);
    }

    public void switchFragment(BottomBarItem item, int index) {
        viewPager.setCurrentItem(index);
    }

    @Override
    public void onItemSelected(BottomBarItem item, int indexfragment) {
        switchFragment(item, indexfragment);
    }

    @Override
    protected void onDestroy() {
        //unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onMenuItemClicked(View view, int pos) {
        if (view.getId() == R.id.child_button01) {
            if (AVUser.getCurrentUser() != null) {
                Intent intent = new Intent(this, CreatePostActivity.class);
                startActivity(intent);
            } else {
                NotificationHelper.toast(this, "请先登录");
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
            menuContentClicked();
        }
        if (view.getId() == R.id.child_button02) {
            Toast.makeText(this, "相册" + pos, Toast.LENGTH_SHORT).show();
        }
        if (view.getId() == R.id.child_button03) {
            Intent intent = new Intent(this, TestActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        bottomBar.selectItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
