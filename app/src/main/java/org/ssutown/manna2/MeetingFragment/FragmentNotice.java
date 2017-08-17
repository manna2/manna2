package org.ssutown.manna2.MeetingFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.ssutown.manna2.MeetingListview.ListViewAdapter;
import org.ssutown.manna2.MeetingRoom.meeting;
import org.ssutown.manna2.MeetingRoom.meeting_Info;
import org.ssutown.manna2.R;

import java.util.ArrayList;

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

        final String meetingid = ((MeetingMainActivity)getActivity()).getMeetingid();


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();

        //공지사항 이름 띄우기
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

        final ListViewAdapter adapter;

        adapter = new ListViewAdapter();
        final ListView listview = (ListView)view.findViewById(R.id.listview2);
        listview.setAdapter(adapter);

        Button addNotice = (Button)view.findViewById(R.id.addnotice);
//        final long userID = ((MainActivity)getActivity()).getUserID();

//        final ArrayList<String> meetinglist = new ArrayList<>();
        final ArrayList<String> noticelist = new ArrayList<>();

        //공지사항 추가버튼 --> dialog


        final Context context = this.getActivity();

        addNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);

                alert.setTitle("공지사항 추가");
                alert.setMessage("내용을 입력하세요");

                final EditText noticecontexts = new EditText(context);
                alert.setView(noticecontexts);

                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String contexts = noticecontexts.getText().toString();
                        addnotice(user)

                    }
                });
                alert.setNegativeButton("no",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                alert.show();
            }
        });


        //add notice 후

        //공지사항 추가가
        databaseReference.child("meeting_Info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                noticelist.clear();
                for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                    noticelist.add(ds.getValue(meeting.class).getMeetingID());


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        return view;
    }

    public void addnotice(String username, String contexts){

    }


}
