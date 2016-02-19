package shellljx.gmail.com.widget.Register;

import android.text.TextUtils;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;

import shellljx.gmail.com.app.service.AVService;

/**
 * Created by Administrator on 2015/6/5.
 */
public class InteractorImp implements RegisterInteractor {
    @Override
    public void register(String username, String password, String phone, final OnRegisterFinishedListener listener) {

        boolean error = false;
        if(TextUtils.isEmpty(username)){
            listener.onUsernameError();
            error = true;
        }
        if(TextUtils.isEmpty(password)){
            listener.onPasswordError();
            error = true;
        }
        if(TextUtils.isEmpty(phone)){
            listener.onPhoneError();
            error = true;
        }

        if(!error){
            AVService.signUp(username, password, phone, new SignUpCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        listener.onRegisterSuccess();
                    } else {
                        listener.onRegisterFaild(e);
                    }
                }
            });
//            AVUser user = new AVUser();
//            user.setUsername(username);
//            user.setPassword(password);
//            user.put("mobilePhoneNumber", phone);
//
//            user.signUpInBackground(new SignUpCallback() {
//                @Override
//                public void done(AVException e) {
//                    if(e==null){
//                        listener.onRegisterSuccess();
//                    }else{
//                        listener.onRegisterFaild(e);
//                    }
//                }
//            });
        }
    }
}
