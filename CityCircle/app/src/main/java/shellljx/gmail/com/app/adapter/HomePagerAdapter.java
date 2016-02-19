package shellljx.gmail.com.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import shellljx.gmail.com.app.fragment.home.ArticleFragment;
import shellljx.gmail.com.app.fragment.home.TopicFragment;

/**
 * Created by lijinxiang on 10/11/15.
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    private final static int COUNT = 2;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ArticleFragment();
            case 1:
                return new TopicFragment();
            default:
                return new ArticleFragment();
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }
}
