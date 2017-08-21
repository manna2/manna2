package org.ssutown.manna2.Fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.ssutown.manna2.MainActivity;
import org.ssutown.manna2.MeetingFragment.MeetingMainActivity;
import org.ssutown.manna2.MeetingListview.ListViewAdapter;
import org.ssutown.manna2.MeetingListview.ListViewItem;
import org.ssutown.manna2.MeetingRoom.Add_Meeting;
import org.ssutown.manna2.MeetingRoom.meeting;
import org.ssutown.manna2.MeetingRoom.meeting_Info;
import org.ssutown.manna2.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;



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

        final ListViewAdapter adapter;

        adapter = new ListViewAdapter();
        final ListView listview = (ListView)view.findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        ImageButton addMeeting = (ImageButton)view.findViewById(R.id.addmeeting);
        final long userID = ((MainActivity)getActivity()).getUserID();
        Log.d("userID", "meeting user ID: " + userID);

        final ArrayList<String> meetinglist = new ArrayList<>();

        //미팅추가버튼
        addMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Add_Meeting.class);
                i.putExtra("user_id",userID);
                startActivity(i);
            }
        });

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();

        //start adding meetings
        databaseReference.child("user_Info").child(String.valueOf(userID)).child("personalMeetingList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                meetinglist.clear();
                for (final DataSnapshot user : dataSnapshot.getChildren()) {
                    Log.d(TAG, "userlist onDataChange: " + user.getValue(meeting.class).getMeetingID());
                    meetinglist.add(user.getValue(meeting.class).getMeetingID());
                    Log.d(TAG, "meetinglist: " + meetinglist.toString());

                    databaseReference.child("meeting_List").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            adapter.clear();
                            for(DataSnapshot ds : dataSnapshot.getChildren()){
                                for(int i = 0 ; i<meetinglist.size();i++){
                                    if((meetinglist.get(i).toString().equals(ds.getValue(meeting_Info.class).getMeeting_id()))){
                                        Log.d(TAG, "meeting Info _ onDataChange: " + ds.getValue(meeting_Info.class).getMeeting_id());
                                        adapter.addItem(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.meeting3),ds.getValue(meeting_Info.class).getMeeting_name(),ds.getValue(meeting_Info.class).getMeeting_id());
                                    }adapter.notifyDataSetChanged();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


//        databaseReference.child("meeting_List").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                adapter.clear();
//                for(DataSnapshot ds : dataSnapshot.getChildren()){
//                    for(int i = 0 ; i<meetinglist.size();i++){
//                        if((meetinglist.get(i).toString().equals(ds.getValue(meeting_Info.class).getMeeting_id()))){
//                            Log.d(TAG, "meeting Info _ onDataChange: " + ds.getValue(meeting_Info.class).getMeeting_id());
//                            adapter.addItem(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.meeting3),ds.getValue(meeting_Info.class).getMeeting_name(),"Account Box Black 36dp");
//                        }adapter.notifyDataSetChanged();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        //End adding meetings

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;
                String titleStr = item.getTitle();
                String meetingid = item.getMeetingID();
                Drawable iconDrawable = item.getIcon();

                Intent intent = new Intent(getActivity(), MeetingMainActivity.class);
                intent.putExtra("meetingid",meetingid);
                startActivity(intent);

            }
        }) ;


        return view;
    }


}