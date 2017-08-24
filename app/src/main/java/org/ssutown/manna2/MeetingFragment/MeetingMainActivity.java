package org.ssutown.manna2.MeetingFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.ssutown.manna2.MeetingRoom.User;
import org.ssutown.manna2.MeetingRoom.meeting_Info;
import org.ssutown.manna2.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.ssutown.manna2.R;

import java.util.ArrayList;

import static org.ssutown.manna2.MainActivity.animal;


public class MeetingMainActivity extends FragmentActivity {
    public static String meetingid;
    FragmentSample fragmentSample;

    public static ArrayList<String> MemberID;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    public static String startendDate = "";

    ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_in_fragment);
        fragmentSample=new FragmentSample();
        getSupportFragmentManager().beginTransaction().add(R.id.frame,fragmentSample).show(fragmentSample).commit();

        Intent i = getIntent();
        meetingid = i.getExtras().getString("meetingid");

        MemberID = new ArrayList<String>();


        databaseReference.child("meeting_Info").child(meetingid).child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    users.add(new User(ds.getValue(User.class).getAnimal(),ds.getValue(User.class).getNickname(),ds.getValue(User.class).getUserID()));
                    showArrayList();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        databaseReference.child("meeting_List").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    Log.i("startendday6", startendDate+"111");
                   if(meetingid.equals(ds.getValue(meeting_Info.class).getMeeting_id())){
                       Log.i("startendday2", startendDate+"111");
                       String temp1 = ds.getValue(meeting_Info.class).getStartYear()+
                               ds.getValue(meeting_Info.class).getStartMonth()+
                               ds.getValue(meeting_Info.class).getStartDay();
                       String temp2 = ds.getValue(meeting_Info.class).getEndYear()+
                               ds.getValue(meeting_Info.class).getEndMonth()+
                               ds.getValue(meeting_Info.class).getEndDay();
                       try{
                           startendDate = temp1+ diffOfDate(temp1, temp2);
                           Log.i("startendday3", startendDate+"111");

                       }
                       catch(java.lang.Exception e){
                       }

                   }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        Log.i("startendday4", startendDate+"111");

    }
    public static String diffOfDate(String begin, String end) throws Exception
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        Date beginDate = formatter.parse(begin);
        Date endDate = formatter.parse(end);

        long diff = endDate.getTime() - beginDate.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        String result = String.valueOf(diffDays);
        return result;

    }

    public void showArrayList(){
        for(int i = 0; i<users.size(); i++){
            Log.d("MeetingMain", "showArrayList: " + users.get(i).getAnimal());
            Log.d("MeetingMain", "showArrayList: " + users.get(i).getNickname());
            Log.d("MeetingMain", "showArrayList: " + users.get(i).getUserID());
        }
    }




}
