package org.ssutown.manna2.MeetingFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.ssutown.manna2.R;

import java.util.ArrayList;

public class MeetingMainActivity extends FragmentActivity {
    public static String meetingid;
    FragmentSample fragmentSample;
    public static ArrayList<String> MemberID;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

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
                MemberID.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    MemberID.add(ds.getValue().toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }


}
