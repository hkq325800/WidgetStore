package shellljx.gmail.com.widget.Login;

/**
 * Created by Administrator on 2015/6/5.
 */
public interface LoginInteractor {

    public void login(String phone,String password,OnLoginFinishedListener listener);
}
