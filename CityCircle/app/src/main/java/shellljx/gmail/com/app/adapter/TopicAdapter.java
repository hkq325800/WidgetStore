package shellljx.gmail.com.app.adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import shellljx.gmail.com.app.adapter.AnimationRecyclerAdapter.AnimationViewHolder;
import shellljx.gmail.com.app.model.Response.Topic;
import shellljx.gmail.com.app.model.Response.TopicHeader;
import shellljx.gmail.com.app.util.Constants;
import shellljx.gmail.com.widget.R;

/**
 * Created by shell on 15-10-18.
 */
public class TopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private TopicHeader topicHeader;
    private int mHeaderLayoutId;
    private int mLayoutId;
    private LayoutInflater mInflate;
    private int mLastPosition = -1;
    private ExpandViewHolder mLastHolder = null;
    private HeaderViewHolder mHeaderHolder = null;
    private boolean isRecyced;
    private int mAnimatedPosition = -1;
    private List<Topic> mDatas;

    public TopicAdapter(Context context, RecyclerView listview, List<Topic> data, TopicHeader header, int layoutId, int headerLayoutId) {
        this.mContext = context;
        this.topicHeader = header;
        this.mDatas = data;
        this.mHeaderLayoutId = headerLayoutId;
        this.mLayoutId = layoutId;
        this.mInflate = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case Constants.EXTRA.LIST_LOADING:
                View loading = mInflate.inflate(R.layout.loading_first_time, parent, false);
                return new LoadingViewHolder(loading);
            case Constants.EXTRA.LIST_ITEM:
                View item = mInflate.inflate(mLayoutId, parent, false);
                return new ExpandViewHolder(item);
            case Constants.EXTRA.LIST_HEADER:
                View header = mInflate.inflate(mHeaderLayoutId, parent, false);
                mHeaderHolder = new HeaderViewHolder(header,mContext,topicHeader);
                return mHeaderHolder;
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ExpandViewHolder) {
            final ExpandViewHolder eHolder = (ExpandViewHolder) holder;
            //当列表向上滑动的时候position是出现在下边界的item，
            // 当列表向下滑动的时候，position是出现在上边界的item
            if (mAnimatedPosition < position) {
                eHolder.animatorSet.start();
                mAnimatedPosition = position;
            } else {
                mAnimatedPosition = position;
            }
            eHolder.buttonExp.setTag(position);
            eHolder.buttonExp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    eHolder.runAnimator();
                    LinearLayout btn = (LinearLayout) view;
                    int clickPos = ((Integer) btn.getTag()).intValue();
                    if (clickPos != mLastPosition) {
                        eHolder.runAnimator(true);
                        mLastPosition = clickPos;
                        if (mLastHolder != null) {
                            if (!isRecyced)
                                mLastHolder.runAnimator();
                        }
                        mLastHolder = eHolder;
                        isRecyced = false;
                    } else {
                        mLastHolder = null;
                        mLastPosition = -1;
                    }
                }
            });
            if (((Integer) eHolder.buttonExp.getTag()).intValue() == mLastPosition) {
                eHolder.openExpand();
            } else {
                eHolder.closeExpand();
            }
        } else if (holder instanceof HeaderViewHolder) {
            //header布局
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (null == mDatas.get(position)) {
            if (position == 0)
                return Constants.EXTRA.LIST_HEADER; //当第一个item为空，返回header
            return Constants.EXTRA.LIST_LOADING;    //当最后一个item为空，返回加载中
        }
        return Constants.EXTRA.LIST_ITEM;           //返回正常item
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (mLastHolder != null) {
            if (holder == mLastHolder) {
                isRecyced = !isRecyced;
            }
        }
    }

    public void setViewPagerAutoScroll(boolean scroll){
        if (mHeaderHolder!=null){
            if (!scroll)
                mHeaderHolder.vp_header.stopAutoScroll();
            mHeaderHolder.vp_header.startAutoScroll();
        }
    }

    public static class ExpandViewHolder extends AnimationViewHolder {

        public TextView buttonA, buttonB;
        public LinearLayout buttonExp;
        public final LinearLayout ll;
        private boolean status = false;

        public ExpandViewHolder(View itemView) {
            super(itemView);
            buttonA = (TextView) itemView.findViewById(R.id.bt_a);
            buttonB = (TextView) itemView.findViewById(R.id.bt_b);
            buttonExp = (LinearLayout) itemView.findViewById(R.id.ll_expand_btn);
            ll = (LinearLayout) itemView.findViewById(R.id.expandable);
        }

        public void runAnimator(boolean isExpand) {
            ValueAnimator valueAnimator;
            if (isExpand) {
                status = true;
                valueAnimator = ValueAnimator.ofInt(0, 140);
            } else {
                status = false;
                valueAnimator = ValueAnimator.ofInt(140, 0);
            }
            valueAnimator.setDuration(150);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    Integer value = (Integer) animation.getAnimatedValue();
                    ll.getLayoutParams().height = value.intValue();
                    ll.requestLayout();
                }
            });
            valueAnimator.start();
        }

        public void runAnimator() {
            if (status) {
                runAnimator(false);
            } else {
                runAnimator(true);
            }
        }

        public void closeExpand() {
            ll.getLayoutParams().height = 0;
            ll.requestLayout();
        }

        public void openExpand() {
            ll.getLayoutParams().height = 140;
            ll.requestLayout();
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public AutoScrollViewPager vp_header;
        public CirclePageIndicator indicator;
        private Context mContext;

        public HeaderViewHolder(View itemView,Context context,TopicHeader data) {
            super(itemView);
            mContext = context;
            vp_header = (AutoScrollViewPager) itemView.findViewById(R.id.vp_header);
            indicator = (CirclePageIndicator) itemView.findViewById(R.id.indicator);
            vp_header.setAdapter(new RecommendsAdapter(mContext,data));
            indicator.setViewPager(vp_header);
            vp_header.setInterval(3000);
            vp_header.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);
            vp_header.startAutoScroll();
        }
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }

}
