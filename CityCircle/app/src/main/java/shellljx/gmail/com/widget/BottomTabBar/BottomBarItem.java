package shellljx.gmail.com.widget.BottomTabBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import shellljx.gmail.com.widget.R;


/**
 * Created by shell on 12/16/14.
 */
public class BottomBarItem extends LinearLayout {

    private static final String TAG = BottomBarItem.class.getName();

    //properties
    String mItemText;
    int mItemIconRes;

    //view
    ImageView mItemIconView;
    TextView mItemTextView;

    public BottomBarItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        // inflate from layout
        LayoutInflater.from(context).inflate(R.layout.buttom_bar_item, this, true);

        mItemIconView = (ImageView) findViewById(R.id.buttom_bar_item_icon);
        mItemTextView = (TextView) findViewById(R.id.buttom_bar_item_text);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BottomBarItem, 0, 0);
        try {
            //ButtomBarItem_itemIcon是父节点的名字和子节点的名字拼接而成的，切记
            mItemText = a.getString(R.styleable.BottomBarItem_itemText);
            mItemIconRes = a.getResourceId(R.styleable.BottomBarItem_itemIcon, 0);
        } finally {
            a.recycle();
        }

        mItemIconView.setImageResource(mItemIconRes);
        mItemTextView.setText(mItemText);
    }

    public String getItemText() {
        return mItemText;
    }

    public void setItemText(String itemText) {
        this.mItemText = itemText;
        mItemTextView.setText(itemText);
    }

    public int getItemIconRes() {
        return mItemIconRes;
    }

    public void setItemIconRes(int itemIconRes) {
        this.mItemIconRes = itemIconRes;
        mItemIconView.setBackgroundResource(itemIconRes);
        invalidate();
        requestLayout();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mItemIconView.setEnabled(enabled);
        mItemTextView.setEnabled(enabled);
    }
}
