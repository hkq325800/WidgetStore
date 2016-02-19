package shellljx.gmail.com.app.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import shellljx.gmail.com.app.fragment.BaseFragment;
import shellljx.gmail.com.widget.R;


/**
 * Created by Administrator on 2015/5/8.
 */
public class ThirdFragment extends BaseFragment {

    public ThirdFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_third,container,false);
        return view;
    }
}
