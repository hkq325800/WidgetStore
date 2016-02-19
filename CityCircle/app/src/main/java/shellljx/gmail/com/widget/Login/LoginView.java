package shellljx.gmail.com.widget.Login;

/**
 * Created by Administrator on 2015/6/5.
 */
public interface LoginView {

    public void hideProgressbar();
    public void showProgressbar();

    public void setPhoneError();
    public void setPasswordError();

    public void LoginFailed(String message);
    public void navigateIndex();
}
