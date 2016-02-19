package shellljx.gmail.com.app.util;

import android.os.CountDownTimer;
import android.os.Handler;


/**
 * Created by Administrator on 2015/6/6.
 */
public class RegisterCodeTimer extends CountDownTimer {

    private static Handler mHandler;
    public static int IN_RUNNING=1001;
    public static int END_RUNNING = 1002;

    public RegisterCodeTimer(long millisInFuture,long interval,Handler handler){
        super(millisInFuture,interval);
        this.mHandler = handler;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if(mHandler!=null){
            mHandler.obtainMessage(IN_RUNNING,(millisUntilFinished/1000)+"秒后重发").sendToTarget();
        }
    }

    @Override
    public void onFinish() {
        mHandler.obtainMessage(END_RUNNING,"获取验证码").sendToTarget();
    }
}
