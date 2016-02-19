package shellljx.gmail.com.app.service;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SignUpCallback;

/**
 * Created by Administrator on 2015/6/2.
 */
public class AVService {

    public static void signUp(String username,String password,String phone,SignUpCallback signUpCallback){
        AVUser user = new AVUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setMobilePhoneNumber(phone);
        user.signUpInBackground(signUpCallback);
    }

    public static void logOut(){
        AVUser.logOut();
    }

    public static void signIn(String phone,String password,LogInCallback logInCallback){
        AVUser.loginByMobilePhoneNumberInBackground(phone, password, logInCallback);
    }

    //请求发送验证码进行手机验证
    public static void getPhoneVerifyCodeInBackground(String phone,RequestMobileCodeCallback codeCallback){
        AVUser.requestMobilePhoneVerifyInBackground(phone, codeCallback);
    }

    //验证手机号
    public static void PhoneVerifyInBackground(String code,AVMobilePhoneVerifyCallback verifyCallback){
        AVUser.verifyMobilePhoneInBackground(code,verifyCallback);
    }
}
