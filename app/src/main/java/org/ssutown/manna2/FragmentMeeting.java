package org.ssutown.manna2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentMeeting extends Fragment {
    TextView textView;

    public static FragmentMeeting newInstance(String text){
        FragmentMeeting fragmentMeeting=new FragmentMeeting();
        Bundle bundle=new Bundle();
        bundle.putString("text",text);
        fragmentMeeting.setArguments(bundle);
        return fragmentMeeting;
    }
    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_meeting,container,false);
        textView= (TextView) view.findViewById(R.id.textView);
        textView.setText(getArguments().getString("text"));
        return view;
    }
}
