package shellljx.gmail.com;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextThemeWrapper;

import shellljx.gmail.com.app.activity.BaseActivity;
import shellljx.gmail.com.app.util.NetUtils;
import shellljx.gmail.com.widget.R;

/**
 * Created by Administrator on 2015/5/25.
 */
public class LoadActivity extends BaseActivity {

    private Intent mIntent;
    private LoadTask loadTask;
    private Context context;

    //当oncreate后面有第二个 参数的时候，布局是显示不出来的
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_load);
        //提醒用户现在非wifi网络，是否继续
        if(PreferenceManager.getDefaultSharedPreferences(context).getBoolean("only_wifi",true)&&
                !NetUtils.getInstance(context).isWifiConnected()){
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context,R.style.dialogstyle))
                    .setTitle("友情提示").setMessage("您当前是非WIFI网络,是否继续?");
            builder.setCancelable(false);
            builder.setPositiveButton("继续",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    loadTask = new LoadTask();
                    loadTask.execute();
                }
            });
            builder.setNegativeButton("退出",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   finish();
                }
            });
            builder.create().show();
        }else{
            loadTask = new LoadTask();
            loadTask.execute();
        }
    }

    private class LoadTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            boolean ready = true;
            while(ready){
                try {
                    Thread.sleep(2000);
                    ready = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mIntent = new Intent(LoadActivity.this,MainActivity.class);
            startActivity(mIntent);
            finish();
        }
    }
}
