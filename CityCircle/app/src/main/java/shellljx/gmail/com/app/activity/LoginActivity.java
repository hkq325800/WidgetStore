package shellljx.gmail.com.app.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;

import shellljx.gmail.com.widget.Login.LoginPresenterImp;
import shellljx.gmail.com.widget.Login.LoginView;
import shellljx.gmail.com.widget.R;
import shellljx.gmail.com.widget.Dialog.CusProgressDialog;

/**
 * Created by Administrator on 2015/6/1.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener,LoginView{

    private EditText phoneInput;
    private EditText passwordInput;
    private ButtonRectangle loginButton;
    private TextView resetPassword;
    private TextView register;

    private LoginPresenterImp presenter;
    private CusProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        setupView();
    }

    public void init(){
        presenter = new LoginPresenterImp(this);
        dialog = new CusProgressDialog();
    }

    public void setupView(){
        phoneInput = (EditText)findViewById(R.id.phoneedit);
        passwordInput = (EditText)findViewById(R.id.passwordedit);
        loginButton = (ButtonRectangle)findViewById(R.id.login_button);
        resetPassword = (TextView)findViewById(R.id.resetpassword_button);
        register = (TextView)findViewById(R.id.signup_button);
        loginButton.setOnClickListener(this);
        register.setOnClickListener(this);
        resetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.signup_button){
            Intent intent = new Intent(this,RegisterActivity.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.resetpassword_button){

        }
        if(v.getId()==R.id.login_button){
            String phone = phoneInput.getText().toString();
            String password = passwordInput.getText().toString();
            presenter.login(phone,password);
        }
    }

    @Override
    public void hideProgressbar() {
        dialog.dismiss();
    }

    @Override
    public void showProgressbar() {
        dialog.setMessage("登入中...");
        dialog.show(getSupportFragmentManager(),"loginDialog");
    }

    @Override
    public void setPhoneError() {
        phoneInput.setError("手机号不能为空!");
    }

    @Override
    public void setPasswordError() {
        passwordInput.setError("密码不能为空!");
    }

    @Override
    public void LoginFailed(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("警告")
                .setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("知道了",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void navigateIndex() {
        hideProgressbar();
        finish();
    }
}
