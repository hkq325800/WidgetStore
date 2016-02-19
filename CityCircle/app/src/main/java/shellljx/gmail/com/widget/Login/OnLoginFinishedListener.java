package shellljx.gmail.com.widget.Login;

import com.avos.avoscloud.AVException;

/**
 * Created by Administrator on 2015/6/5.
 */
public interface OnLoginFinishedListener {

    public void onPhoneError();
    public void onPasswordError();

    public void onLoginSuccess();
    public void onLoginFialed(AVException e);
}
