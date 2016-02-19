package shellljx.gmail.com.widget.Login;

import com.avos.avoscloud.AVException;

/**
 * Created by Administrator on 2015/6/5.
 */
public class LoginPresenterImp implements OnLoginFinishedListener {

    private LoginInteractor interactor;
    private LoginView loginView;

    public LoginPresenterImp(LoginView view){
        this.loginView = view;
        interactor = new InteractorImp();
    }

    public void login(String phone,String password){
        loginView.showProgressbar();
        interactor.login(phone,password,this);
    }
    @Override
    public void onPhoneError() {
        loginView.hideProgressbar();
        loginView.setPhoneError();
    }

    @Override
    public void onPasswordError() {
        loginView.hideProgressbar();
        loginView.setPasswordError();
    }

    @Override
    public void onLoginSuccess() {
        loginView.hideProgressbar();
        loginView.navigateIndex();
    }

    @Override
    public void onLoginFialed(AVException e) {
        loginView.hideProgressbar();
        //211用户不存在
        loginView.LoginFailed(e.getLocalizedMessage()+"-"+e.getCode());
    }
}
