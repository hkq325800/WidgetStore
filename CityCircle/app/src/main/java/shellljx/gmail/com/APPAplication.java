package shellljx.gmail.com;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;

import com.avos.avoscloud.AVOSCloud;

import shellljx.gmail.com.app.util.Config;

/**
 * Created by Administrator on 2015/6/2.
 */
public class APPAplication extends Application {

    //singleton
    private static APPAplication globalContext = null;

    //image size
    private Activity activity = null;
    private Activity currentRunningActivity = null;

    private Handler handler = new Handler();

    public static APPAplication getInstance(){
        return globalContext;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        globalContext = this;
        AVOSCloud.useAVCloudCN();
        AVOSCloud.initialize(this, Config.APP_ID,Config.APP_KEY);
    }

    public Handler getUIHandler(){
        return handler;
    }

    public Activity getActivity(){
        return activity;
    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }
}
