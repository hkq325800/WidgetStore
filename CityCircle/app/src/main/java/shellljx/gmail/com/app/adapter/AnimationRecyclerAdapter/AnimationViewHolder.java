package shellljx.gmail.com.app.adapter.AnimationRecyclerAdapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by shell on 15-10-17.
 */
public class AnimationViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> mViews;
    private View mConvertView;
    public AnimatorSet animatorSet;

    public AnimationViewHolder(View itemView) {
        super(itemView);
        this.mViews = new SparseArray<>();
        this.mConvertView = itemView;

        animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(itemView, "translationY", 100, 0),
                ObjectAnimator.ofFloat(itemView, "alpha", 0, 1.0f));
        animatorSet.setDuration(500);
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过控件ｉｄ来获得ｖｉｅｗ
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (null == view) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
}
