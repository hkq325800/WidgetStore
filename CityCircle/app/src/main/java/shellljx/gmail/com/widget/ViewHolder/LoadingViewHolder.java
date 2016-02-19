package shellljx.gmail.com.widget.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;

import shellljx.gmail.com.widget.R;

/**
 * Created by Administrator on 2015/5/16.
 */
public class LoadingViewHolder extends RecyclerView.ViewHolder {

    private TextView loadingtext;
    private ProgressBarCircularIndeterminate progressbar;

    public LoadingViewHolder(View itemView) {
        super(itemView);
        loadingtext = (TextView)itemView.findViewById(R.id.loading_text);
        progressbar = (ProgressBarCircularIndeterminate)itemView.findViewById(R.id.loading_progressbar);
    }
}
