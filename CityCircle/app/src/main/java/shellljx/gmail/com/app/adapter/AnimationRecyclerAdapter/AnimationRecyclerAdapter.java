package shellljx.gmail.com.app.adapter.AnimationRecyclerAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2015/5/15.
 */
public class AnimationRecyclerAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {

    private int mAnimatedPosition = -1;

    public int getAnimatedPosition() {
        return mAnimatedPosition;
    }

    public void setAnimatedPosition(int animatedPosition) {
        this.mAnimatedPosition = animatedPosition;
    }

    public void reset() {
        mAnimatedPosition = -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AnimationViewHolder) {
            AnimationViewHolder vh = (AnimationViewHolder) holder;

            //当列表向上滑动的时候position是出现在下边界的item，
            // 当列表向下滑动的时候，position是出现在上边界的item
            if (mAnimatedPosition < position) {
                vh.animatorSet.start();
                mAnimatedPosition = position;
            } else {
                mAnimatedPosition = position;
            }
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
