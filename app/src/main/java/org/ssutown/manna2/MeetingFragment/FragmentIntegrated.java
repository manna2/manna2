package org.ssutown.manna2.MeetingFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.ssutown.manna2.MeetingFragment_Merge.BaseActivity;
import org.ssutown.manna2.R;

public class FragmentIntegrated extends Fragment {

    public static FragmentIntegrated newInstance(String text){
        FragmentIntegrated fragmentIntegrated=new FragmentIntegrated();
        Bundle bundle=new Bundle();
        bundle.putString("text",text);
        fragmentIntegrated.setArguments(bundle);
        return fragmentIntegrated;
    }
    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_integrated,container,false);
//
//        Intent intent = new Intent(getActivity(), BaseActivity.class);
//        startActivity(intent);

        return view;
    }



}
