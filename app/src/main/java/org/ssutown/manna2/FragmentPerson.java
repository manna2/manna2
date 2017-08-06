package org.ssutown.manna2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentPerson extends Fragment {
    TextView textView;

    public static FragmentPerson newInstance(String text){
        FragmentPerson fragmentPerson=new FragmentPerson();
        Bundle bundle=new Bundle();
        bundle.putString("text",text);
        fragmentPerson.setArguments(bundle);
        return fragmentPerson;
    }
    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_person,container,false);
        textView= (TextView) view.findViewById(R.id.textView);
        textView.setText(getArguments().getString("text"));
        return view;
    }
}
