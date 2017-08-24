package org.ssutown.manna2.MeetingRoom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.ssutown.manna2.R;

import java.util.ArrayList;

public class Add_Meeting extends AppCompatActivity {

//    userInfo userInfo;

    long meetingRoomID;
    meeting_Info meeting_info;
    ArrayList<String> meetinglist = new ArrayList<>();

    EditText meetingName;
    DatePicker startDay;
    DatePicker endDay;
    Button addMeeting;
    EditText min;

    long userID;
    String animal;
    String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__meeting);

        meetingName = (EditText) findViewById(R.id.meeting_name);
        startDay = (DatePicker)findViewById(R.id.startDay);
        endDay = (DatePicker)findViewById(R.id.endDay);
        addMeeting = (Button)findViewById(R.id.add);
        min = (EditText)findViewById(R.id.min);

        Intent i = getIntent();
        userID = i.getExtras().getLong("user_id");
        animal = i.getExtras().getString("animal");
        nickname = i.getExtras().getString("nickname");

        Log.d("AddMeeting", userID + " " + animal + " " + nickname);


        addMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meeting_info = new meeting_Info(meetingName.getText().toString(),"0",startDay.getYear(),startDay.getMonth(),startDay.getDayOfMonth(),endDay.getYear(),endDay.getMonth(),endDay.getDayOfMonth(),min.getText().toString());
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference();

                meeting meeting = new meeting(meeting_info.getMeeting_id());
                databaseReference.child("user_Info").child(String.valueOf(userID)).child("personalMeetingList").push().setValue(meeting);

                String key = databaseReference.child("meeting_List").push().getKey();
                databaseReference.child("meeting_List").child(key).setValue(meeting_info);

                User user = new User(animal,nickname,String.valueOf(userID));
                databaseReference.child("meeting_Info").child(meeting_info.getMeeting_id()).child("Users").push().setValue(user);

                finish();
            }
        });
    }
}
