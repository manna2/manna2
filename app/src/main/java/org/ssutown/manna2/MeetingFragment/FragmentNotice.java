package org.ssutown.manna2.MeetingFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.ssutown.manna2.MeetingRoom.meeting_Info;
import org.ssutown.manna2.R;

public class FragmentNotice extends Fragment {

    public static FragmentNotice newInstance(String text){
        FragmentNotice fragmentNotice=new FragmentNotice();
        Bundle bundle=new Bundle();
        bundle.putString("text",text);
        fragmentNotice.setArguments(bundle);
        return fragmentNotice;
    }
    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_notice,container,false);

        final String meetingid = MeetingMainActivity.meetingid;

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();

        //start adding meetings
        databaseReference.child("meeting_List").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if((meetingid.equals(ds.getValue(meeting_Info.class).getMeeting_id()))){
                        TextView textview = (TextView)getActivity().findViewById(R.id.roomname);
                        textview.setText(ds.getValue(meeting_Info.class).getMeeting_name());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }



}
