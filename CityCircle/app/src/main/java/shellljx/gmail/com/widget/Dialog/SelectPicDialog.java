package shellljx.gmail.com.widget.Dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Window;

import shellljx.gmail.com.widget.R;

/**
 * Created by Administrator on 2015/6/10.
 */
public class SelectPicDialog extends DialogFragment {

    public static SelectPicDialog newInstance(){
        return new SelectPicDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] items ={getString(R.string.take_camera),getString(R.string.select_pic)};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.select)).setItems(items,(DialogInterface.OnClickListener)getActivity());
        Dialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.DialogInOutCenterAnimation);
        return dialog;
    }
}
