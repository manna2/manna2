package org.ssutown.manna2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentSchedule extends Fragment {
    TextView textView;

    public static FragmentSchedule newInstance(String text){
        FragmentSchedule fragmentSchedule=new FragmentSchedule();
        Bundle bundle=new Bundle();
        bundle.putString("text",text);
        fragmentSchedule.setArguments(bundle);
        return fragmentSchedule;
    }
    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_schedule,container,false);
        textView= (TextView) view.findViewById(R.id.textView);
        textView.setText(getArguments().getString("text"));
        return view;
    }
}
