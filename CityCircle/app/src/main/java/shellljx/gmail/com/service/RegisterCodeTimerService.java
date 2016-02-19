package shellljx.gmail.com.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import shellljx.gmail.com.app.util.RegisterCodeTimer;

/**
 * Created by Administrator on 2015/6/6.
 */
public class RegisterCodeTimerService extends Service {

    private static Handler mHandler;
    private static RegisterCodeTimer timer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        timer = new RegisterCodeTimer(60000,1000,mHandler);
        timer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static void setHandler(Handler handler){
        mHandler = handler;
    }
}
