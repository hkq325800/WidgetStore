package shellljx.gmail.com.widget.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import com.gc.materialdesign.views.ButtonRectangle;

import shellljx.gmail.com.app.util.RegisterCodeTimer;
import shellljx.gmail.com.service.RegisterCodeTimerService;
import shellljx.gmail.com.widget.Dialog.DialogListener.PhoneVerListener;
import shellljx.gmail.com.widget.R;

/**
 * Created by Administrator on 2015/6/6.
 */
public class PhoneVerDialog extends DialogFragment implements View.OnClickListener{

    private EditText verCodeedit;
    private TextView vercodeBtn;
    private String verCode;
    private ButtonRectangle verButton;
    private Intent mIntent;
    private PhoneVerListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mIntent = new Intent(getActivity(), RegisterCodeTimerService.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_vercode,null);
        vercodeBtn =(TextView)view.findViewById(R.id.vercodebutton);
        verCodeedit = (EditText)view.findViewById(R.id.vercodeedit);
        verButton = (ButtonRectangle)view.findViewById(R.id.verbutton);
        verButton.setOnClickListener(this);
        vercodeBtn.setOnClickListener(this);
        builder.setView(view);
        Dialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.DialogInOutCenterAnimation);
        return dialog;
    }

    public void setPhoneVerListener(PhoneVerListener listener){
        this.listener = listener;
    }

    public Handler getHandler(){
        return this.codeHandler;
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.verbutton){
            if(listener!=null)
                listener.onVerButtonClicked(verCodeedit.getText().toString());
        }

        if(v.getId()==R.id.vercodebutton){
            getActivity().startService(mIntent);
        }
    }

    private Handler codeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what== RegisterCodeTimer.IN_RUNNING){
                if(vercodeBtn.isEnabled()){
                    vercodeBtn.setEnabled(false);
                }
                vercodeBtn.setText(msg.obj.toString());
            }else if(msg.what==RegisterCodeTimer.END_RUNNING){
                vercodeBtn.setEnabled(true);
                vercodeBtn.setText(msg.obj.toString());
            }
        }
    };
}
