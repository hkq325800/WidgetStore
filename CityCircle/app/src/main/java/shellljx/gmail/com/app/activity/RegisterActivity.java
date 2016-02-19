package shellljx.gmail.com.app.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.gc.materialdesign.views.ButtonRectangle;

import shellljx.gmail.com.app.service.AVService;
import shellljx.gmail.com.service.RegisterCodeTimerService;
import shellljx.gmail.com.widget.R;
import shellljx.gmail.com.widget.Register.RegisterPresenterImp;
import shellljx.gmail.com.widget.Register.RegisterView;
import shellljx.gmail.com.widget.Dialog.CusProgressDialog;
import shellljx.gmail.com.widget.Dialog.PhoneVerDialog;
import shellljx.gmail.com.widget.Dialog.DialogListener.PhoneVerListener;

/**
 * Created by Administrator on 2015/6/2.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener,RegisterView,PhoneVerListener {

    private EditText usernameInp,passwordInp,phoneInp;
    private ButtonRectangle rectangleBtn;
    private CusProgressDialog dialog;
    private PhoneVerDialog phoneVerDialog;

    private Intent mIntent;

    private RegisterPresenterImp presenterImp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        setupView();
    }

    public void init(){
        presenterImp = new RegisterPresenterImp(this);
        phoneVerDialog = new PhoneVerDialog();
        phoneVerDialog.setPhoneVerListener(this);
        phoneVerDialog.setCancelable(false);
        RegisterCodeTimerService.setHandler(phoneVerDialog.getHandler());
        mIntent = new Intent(this, RegisterCodeTimerService.class);
        dialog = new CusProgressDialog();
        dialog.setCancelable(true);
    }

    public void setupView(){
        usernameInp = (EditText)findViewById(R.id.nickedit);
        phoneInp = (EditText)findViewById(R.id.phoneedit);
        passwordInp = (EditText)findViewById(R.id.passwordedit);
        rectangleBtn = (ButtonRectangle)findViewById(R.id.register_button);
        rectangleBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.register_button){
            String username = usernameInp.getText().toString();
            String phone = phoneInp.getText().toString();
            String password = passwordInp.getText().toString();
            presenterImp.register(username,password,phone);
        }

/*        if(v.getId()==R.id.vercodebutton){
            vercodeBtn.setEnabled(false);
            startService(mIntent);
            String phone = phoneInp.getText().toString();
            if(phone!=null){
                AVService.getPhoneVerifyCodeInBackground(phone, new RequestMobileCodeCallback() {
                    @Override
                    public void done(AVException e) {
                        if(e==null){
                            Toast.makeText(RegisterActivity.this,"没有报错",Toast.LENGTH_LONG).show();
                        }else Toast.makeText(RegisterActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        }*/
    }

    @Override
    public void hideProgressbar() {
        dialog.dismiss();
    }

    @Override
    public void showProgressbar() {
        dialog.setMessage(getResources().getString(R.string.registing));
        dialog.show(getSupportFragmentManager(), "registerDialog");

    }

    @Override
    public void setPhoneError() {
        phoneInp.setError(getResources().getString(R.string.phoneerror));
    }

    @Override
    public void setUsernameError() {
        usernameInp.setError(getResources().getString(R.string.usernameerror));
    }

    @Override
    public void setPasswordError() {
        passwordInp.setError(getResources().getString(R.string.passworderror));
    }


    @Override
    public void navigateToLogin() {
        hideProgressbar();
        finish();
    }

    @Override
    public void navigateToVer() {
        startService(mIntent);
        phoneVerDialog.show(getSupportFragmentManager(), "phoneverDialog");
        if (dialog.isVisible())
            dialog.dismiss();
    }

    @Override
    public void registerFaild(String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.warning))
                .setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.confim), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(mIntent);
    }

    @Override
    public void onVerButtonClicked(String code) {
        phoneVerDialog.dismiss();
        AVService.PhoneVerifyInBackground(code, new AVMobilePhoneVerifyCallback() {
            @Override
            public void done(AVException e) {
                if(e==null){
                    navigateToLogin();
                }else{
                    Toast.makeText(RegisterActivity.this,e.getLocalizedMessage()+e.getCode(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
