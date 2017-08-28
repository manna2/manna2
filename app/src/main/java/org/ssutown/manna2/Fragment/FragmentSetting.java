package org.ssutown.manna2.Fragment;

import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import org.ssutown.manna2.FragmentSetting_Announce.Announce;
import org.ssutown.manna2.R;


public class FragmentSetting extends Fragment {

    TextView textView;

    static final String[] LIST_MENU = {"암호잠금", "공지사항"} ;

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

        ArrayAdapter Adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU) ;

        ListView listview = (ListView) view.findViewById(R.id.listview1) ;
        listview.setAdapter(Adapter) ;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                // get TextView's Text.
                String strText = (String) parent.getItemAtPosition(position) ;

                if(position == 0)
                {

                }
                else if(position == 1)
                {
                    Intent intent = new Intent(getActivity(), Announce.class);
                    startActivity(intent);
                }
            }
        }) ;

        return view;
    }
}
