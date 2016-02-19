package shellljx.gmail.com.widget.BottomTabBar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/5/6.
 */
public class BottomBar extends LinearLayout implements View.OnClickListener {

    private String TAG = BottomBar.class.getName();

    private List<BottomBarItem> buttomBarList = new ArrayList<BottomBarItem>();
    private int mSelectedItemIndex = -1;
    private int mlastSelectedItemIndex = 0;
    private OnItemSelectedListener onItemSelectedListener;

    public BottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        System.out.println("ButtomBar:的onSizeChanged方法调用了");
        init();
    }


    private void init(){
        buttomBarList.clear();
        for(int i=0;i<getChildCount();i++){
            View item = getChildAt(i);
            if(item instanceof BottomBarItem){
                buttomBarList.add((BottomBarItem)item);
            }
        }
        if(buttomBarList.size()>0){
            selectItem(0);
        }

        for(int i=0;i<buttomBarList.size();i++){
            BottomBarItem item = buttomBarList.get(i);
            item.setTag(i);
            item.setOnClickListener(this);
        }
    }

    public void selectItem(int index){
        Log.i(TAG,"Buttom Item size = "+buttomBarList.size());
        if(index == mSelectedItemIndex){
            return;
        }

        //enabled old item
        if(mSelectedItemIndex>-1){
            buttomBarList.get(mSelectedItemIndex).setEnabled(true);
        }

        //disable the item
        BottomBarItem item = buttomBarList.get(index);
        item.setEnabled(false);
        mSelectedItemIndex = index;

        if(onItemSelectedListener!=null){
            onItemSelectedListener.onItemSelected(item,mSelectedItemIndex);
            mlastSelectedItemIndex = mSelectedItemIndex;
        }
    }


    public interface OnItemSelectedListener{
        public void onItemSelected(BottomBarItem item, int indexfragment);
    }
    @Override
    public void onClick(View view) {
        int selectedItem = (int)view.getTag();
        selectItem(selectedItem);
    }

    public OnItemSelectedListener getOnItemSelectedListener(){
        return this.onItemSelectedListener;
    }
    public void setOnItemSelectedListener(OnItemSelectedListener listener){
        this.onItemSelectedListener = listener;
    }
}
