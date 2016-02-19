package shellljx.gmail.com.widget.Login;

import android.text.TextUtils;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;

import shellljx.gmail.com.app.service.AVService;

/**
 * Created by Administrator on 2015/6/5.
 */
public class InteractorImp implements LoginInteractor {
    @Override
    public void login(String phone, String password, final OnLoginFinishedListener listener) {
        boolean error = false;
        if(TextUtils.isEmpty(phone)){
            listener.onPhoneError();
            error = true;
        }
        if(TextUtils.isEmpty(password)){
            listener.onPasswordError();
            error = true;
        }

        if(!error){
            AVService.signIn(phone,password,new LogInCallback() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if(avUser!=null){
                        listener.onLoginSuccess();
                    }else{
                        listener.onLoginFialed(e);
                    }
                }
            });
        }
    }
}
