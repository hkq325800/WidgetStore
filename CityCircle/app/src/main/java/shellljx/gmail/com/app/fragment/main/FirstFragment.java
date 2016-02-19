package shellljx.gmail.com.app.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.avos.avoscloud.AVUser;

import de.hdodenhof.circleimageview.CircleImageView;
import shellljx.gmail.com.app.activity.UsercenterActivity;
import shellljx.gmail.com.app.adapter.AnimationRecyclerAdapter.AnimationRecyclerAdapter;
import shellljx.gmail.com.app.fragment.BaseFragment;
import shellljx.gmail.com.widget.R;
import shellljx.gmail.com.widget.model.RecyclerViewItemArray;

/**
 * Created by Administrator on 2015/5/8.
 */
@Deprecated
public class FirstFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout toolbar;
    private RecyclerView postlist;
    private RecyclerViewItemArray itemArray;
    private CircleImageView profile_image;

    public FirstFragment(){

    }


    public void inflatData(){
        AVUser currentUser = AVUser.getCurrentUser();
        if(currentUser!=null){
            profile_image.setImageResource(R.drawable.user_profile);
        }else {
            profile_image.setImageResource(R.drawable.user_profile_image_default);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.profile_image){
            Intent intent = new Intent(getActivity(), UsercenterActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        inflatData();
    }



}
