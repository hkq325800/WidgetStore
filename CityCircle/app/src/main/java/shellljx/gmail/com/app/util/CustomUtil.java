package shellljx.gmail.com.app.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by shell on 15-9-12.
 */
public class CustomUtil {

    /**
     * 创建弹窗
     */
    public static AlertDialog createDialog(Context context,int layout,int animation,boolean showSoftInput,int gravity){
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.setContentView(layout);
        Window window = dialog.getWindow();
        if (showSoftInput) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = gravity;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        window.setWindowAnimations(animation);
        return dialog;
    }

    /**
     * 创建底部弹出dialog
     */
    public static AlertDialog createBottomDialog(Context context, int layout, int animation, boolean showSoftInput) {
        return createDialog(context, layout, animation, showSoftInput, Gravity.BOTTOM);
    }

    /**
     * 创建中间弹出dialog
     */
    public static AlertDialog createCenterDialog(Context context, int layout, int animation, boolean showSoftInput) {
        return createDialog(context, layout, animation, showSoftInput, Gravity.CENTER);
    }
    /**
     * 创建顶部弹出dialog
     */
    public static AlertDialog createTopDialog(Context context, int layout, int animation, boolean showSoftInput) {
        return createDialog(context, layout, animation, showSoftInput, Gravity.TOP);
    }
}
