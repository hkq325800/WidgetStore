package shellljx.gmail.com.widget.KE;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

/**
 * Created by Administrator on 2015/6/8.
 */
public class KeyboardControlEditText extends AutoCompleteTextView {

    private boolean showKeyboard = true;
    public KeyboardControlEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onCheckIsTextEditor() {
        return true;
    }
}
