package shellljx.gmail.com.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;

import shellljx.gmail.com.app.test.TestActivity;
import shellljx.gmail.com.widget.CommonToolbar.CommonToolbar;
import shellljx.gmail.com.widget.R;

/**
 * Created by Administrator on 2015/6/1.
 */
public class UsercenterActivity extends BaseActivity implements CommonToolbar.OnToolbarClickListener,View.OnClickListener {

    private CommonToolbar commonToolbar;
    private ImageView profile_image;
    private TextView loginStatus;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercenter);
        initView();
    }

    public void initView(){
        commonToolbar = (CommonToolbar)findViewById(R.id.toolbar);
        profile_image = (ImageView)findViewById(R.id.profile_image);
        loginStatus = (TextView)findViewById(R.id.login_status);
        logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(this);
        profile_image.setOnClickListener(this);
        commonToolbar.setOnToolbarClickListener(this);
    }

    public void inflatData(){
        AVUser currentUser = AVUser.getCurrentUser();
        if(currentUser!=null){
            profile_image.setImageResource(R.drawable.user_profile);
            loginStatus.setText(currentUser.getUsername());
        }
    }

    @Override
    public void onLeftBtnClicked() {
        finish();
    }

    @Override
    public void onRightBtnClicked() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        inflatData();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.profile_image){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.logout){
            Intent intent = new Intent(this, TestActivity.class);
            startActivity(intent);
//            AVUser.logOut();
//            if(AVUser.getCurrentUser()==null){
//                finish();
//            }
        }
    }
}
