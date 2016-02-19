package shellljx.gmail.com.app.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;

import java.util.Timer;
import java.util.TimerTask;

import shellljx.gmail.com.widget.R;

/**
 * Created by Administrator on 2015/6/2.
 */
public class BaseActivity extends ActionBarActivity {

    private Context mContext;
    private String userId;
    private boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getApplicationContext();
        userId=null;
        AVUser user = AVUser.getCurrentUser();
        if(user!=null){
            userId=user.getObjectId();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (isExit==false){
                isExit = true;
                Timer timer = new Timer();
                Toast.makeText(this,"再按一次退出系统",Toast.LENGTH_SHORT).show();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                      isExit = false;
                    }
                },2000);
            }else {
                finish();
                System.exit(0);
            }
        }
        return false;
    }

    public String getUserId(){
        return userId;
    }

    public void showError(String error){
        showError(error,mContext);
    }

    public void showError(String error,Context context){
        new AlertDialog.Builder(context).setTitle(
                context.getResources().getString(R.string.dialogTitle)
        ).setMessage(error).setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
}
