package shellljx.gmail.com.widget.Register;

/**
 * Created by Administrator on 2015/6/5.
 */
public interface RegisterView {

    public void hideProgressbar();
    public void showProgressbar();
    public void setPhoneError();
    public void setUsernameError();
    public void setPasswordError();
    public void navigateToLogin();
    public void navigateToVer();
    public void registerFaild(String message);
}
