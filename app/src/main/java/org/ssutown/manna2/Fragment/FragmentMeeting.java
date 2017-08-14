package org.ssutown.manna2.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.ssutown.manna2.MeetingListview.ListViewAdapter;
import org.ssutown.manna2.R;
import org.ssutown.manna2.MeetingListview.ListViewItem;


public class FragmentMeeting extends Fragment {

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

        ListViewAdapter adapter;

        adapter = new ListViewAdapter();
        final ListView listview = (ListView)view.findViewById(R.id.listview1);
        listview.setAdapter(adapter);
        // 첫 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.meeting3),
                "Box", "Account Box Black 36dp") ;
        // 두 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.meeting3),
                "Circle", "Account Circle Black 36dp") ;
        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.meeting3),
                "Ind", "Assignment Ind Black 36dp") ;
        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.meeting3),
                "Ind", "Assignment Ind Black 36dp") ;
        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.meeting3),
                "Ind", "Assignment Ind Black 36dp") ;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;

                String titleStr = item.getTitle() ;
                String descStr = item.getDesc() ;
                Drawable iconDrawable = item.getIcon() ;

                // TODO : use item data.
            }
        }) ;



        return view;
    }


}
