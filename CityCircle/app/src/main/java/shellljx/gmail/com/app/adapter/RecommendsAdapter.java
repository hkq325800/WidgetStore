package shellljx.gmail.com.app.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import shellljx.gmail.com.app.model.Response.TopicHeader;
import shellljx.gmail.com.widget.R;

/**
 * Created by shell on 15-11-1.
 */
public class RecommendsAdapter extends PagerAdapter {

    private Context mContext;
    private TopicHeader header;
    private LayoutInflater mInflater;

    public RecommendsAdapter(Context context,TopicHeader data){
        this.mContext = context;
        this.header = data;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mInflater.inflate(R.layout.item_recommend_topic,container,false);
        ImageView topic_recommend = (ImageView) view.findViewById(R.id.iv_topic_recommend);
        Picasso.with(mContext).load(header.getmImages().get(position)).placeholder(R.drawable.icon).error(R.drawable.icon).into(topic_recommend);
        container.addView(view);
        return  view;
    }

    @Override
    public int getCount() {
        return header.getmImages().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
