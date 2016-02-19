package shellljx.gmail.com.widget.Register;

import com.avos.avoscloud.AVException;


/**
 * Created by Administrator on 2015/6/5.
 */
public class RegisterPresenterImp implements OnRegisterFinishedListener {

    private RegisterView registerView;
    private RegisterInteractor registerInteractor;

    public RegisterPresenterImp(RegisterView view){
        this.registerView = view;
        this.registerInteractor = new InteractorImp();
    }

    @Override
    public void onUsernameError() {
        registerView.setUsernameError();
        registerView.hideProgressbar();
    }

    @Override
    public void onPasswordError() {
        registerView.setPasswordError();
        registerView.hideProgressbar();
    }


    @Override
    public void onPhoneError() {
        registerView.setPhoneError();
        registerView.hideProgressbar();
    }

    @Override
    public void onRegisterSuccess() {
        registerView.navigateToVer();
    }

    @Override
    public void onRegisterFaild(AVException excption) {
        int code = excption.getCode();
        String message="注册失败!";
        if(code==202){
            message = "该用户名已经被注册了,请换一个!";
        }
        else if(code==214){
            message = "该手机号已经被注册,请换一个!";
        }
        else if(code==127){
            message = "手机号输入格式不正确!";
        }
        else if(excption.getMessage().contains("UnknownHostException")){
            message = "网络已经断开,请检查网络连接!";
        }

        registerView.registerFaild(message);
        registerView.hideProgressbar();
    }

    public void register(String username,String password,String phone){
        registerView.showProgressbar();
        registerInteractor.register(username,password,phone,this);
    }
}
