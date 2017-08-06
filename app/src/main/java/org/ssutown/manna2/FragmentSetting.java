package org.ssutown.manna2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentSetting extends Fragment {
    TextView textView;

    public static FragmentSetting newInstance(String text){
        FragmentSetting fragmentSetting=new FragmentSetting();
        Bundle bundle=new Bundle();
        bundle.putString("text",text);
        fragmentSetting.setArguments(bundle);
        return fragmentSetting;
    }
    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_setting,container,false);
        textView= (TextView) view.findViewById(R.id.textView);
        textView.setText(getArguments().getString("text"));
        return view;
    }
}
