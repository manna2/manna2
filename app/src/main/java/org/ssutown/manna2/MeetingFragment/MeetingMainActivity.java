package org.ssutown.manna2.MeetingFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.ssutown.manna2.R;

import java.util.ArrayList;

public class MeetingMainActivity extends FragmentActivity {
    public static String meetingid;
    FragmentSample fragmentSample;
    ArrayList<Users> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_in_fragment);
        fragmentSample=new FragmentSample();
        getSupportFragmentManager().beginTransaction().add(R.id.frame,fragmentSample).show(fragmentSample).commit();

        Intent i = getIntent();
        meetingid = i.getExtras().getString("meetingid");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();

    }




}
