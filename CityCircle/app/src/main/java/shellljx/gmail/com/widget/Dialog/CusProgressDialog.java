package shellljx.gmail.com.widget.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import shellljx.gmail.com.widget.R;

/**
 * Created by Administrator on 2015/6/5.
 */
public class CusProgressDialog extends DialogFragment {

    private String message="请等待...";

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_progress,null);
        ((TextView)view.findViewById(R.id.progresstext)).setText(message);
        builder.setView(view);
        return builder.create();
    }
}
