package org.ssutown.manna2.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.ssutown.manna2.R;


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

        view.findViewById(R.id.outlook).setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), OutlookTest.class);
                        startActivity(intent);
                    }
                }
        );

        return view;
    }
}
