package shellljx.gmail.com.widget.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;

import shellljx.gmail.com.widget.R;

/**
 * Created by Administrator on 2015/5/16.
 */
public class LoadingFirstViewHolder extends RecyclerView.ViewHolder {

    private ProgressBarCircularIndeterminate progressbar;
    public LoadingFirstViewHolder(View itemView) {
        super(itemView);
        progressbar = (ProgressBarCircularIndeterminate)itemView.findViewById(R.id.loading_progressbar);
    }
}
