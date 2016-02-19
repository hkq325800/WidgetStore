package shellljx.gmail.com.widget.Register;

import com.avos.avoscloud.AVException;

/**
 * Created by Administrator on 2015/6/5.
 */
public interface OnRegisterFinishedListener {

    public void onUsernameError();
    public void onPasswordError();
    public void onPhoneError();
    public void onRegisterSuccess();
    public void onRegisterFaild(AVException excption);
}
