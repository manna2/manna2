package org.ssutown.manna2.MeetingFragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.ssutown.manna2.R;
import org.ssutown.manna2.MeetingRoom.meeting_Info;

public class Editmeeting extends AppCompatActivity {

    EditText meetingName;
    DatePicker startDay;
    DatePicker endDay;
    Button addMeeting;
    EditText min;

    String nickname;
    String animal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmeeting);

        Intent intent = getIntent();
        final meeting_Info meeting_info = (meeting_Info) intent.getExtras().getSerializable("info");
        Button edit = (Button)findViewById(R.id.EDIT);

        meetingName = (EditText) findViewById(R.id.meeting_name);
        startDay = (DatePicker)findViewById(R.id.startDay);
        endDay = (DatePicker)findViewById(R.id.endDay);
        addMeeting = (Button)findViewById(R.id.EDIT);
        min = (EditText)findViewById(R.id.min);

        meetingName.setText(meeting_info.getMeeting_name());
        startDay.updateDate(Integer.parseInt(meeting_info.getStartYear()),Integer.parseInt(meeting_info.getStartMonth())-1,Integer.parseInt(meeting_info.getStartDay()));
        endDay.updateDate(Integer.parseInt(meeting_info.getEndYear()),Integer.parseInt(meeting_info.getEndMonth())-1,Integer.parseInt(meeting_info.getEndDay()));
        min.setText(meeting_info.getMin());


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference databaseReference = database.getReference();
                databaseReference.child("meeting_List").addValueEventListener(new ValueEventListener() {
                    String key;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            if(ds.getValue(meeting_Info.class).getMeeting_id().equals(meeting_info.getMeeting_id())){
                                key = ds.getKey();
                                Log.d("edit", "onDataChange: " + key);

                                String StartMonth;
                                String StartDay;
                                String EndMonth;
                                String EndDay;

                                meeting_info.setMeeting_name(meetingName.getText().toString());

                                if(startDay.getMonth() <10){
                                    StartMonth="0"+String.valueOf(startDay.getMonth()+1);
                                }
                                else{
                                    StartMonth=String.valueOf(startDay.getMonth()+1);
                                }
                                if(startDay.getDayOfMonth() < 10){
                                    StartDay = "0"+String.valueOf(startDay.getDayOfMonth());
                                }
                                else{
                                    StartDay = String.valueOf(startDay.getDayOfMonth());
                                }
                                if(endDay.getMonth() <10){
                                    EndMonth="0"+String.valueOf(endDay.getMonth()+1);
                                }
                                else{
                                    EndMonth=String.valueOf(endDay.getMonth()+1);
                                }
                                if(endDay.getDayOfMonth() < 10){
                                    EndDay = "0"+String.valueOf(endDay.getDayOfMonth());
                                }
                                else{
                                    EndDay = String.valueOf(endDay.getDayOfMonth());
                                }

                                meeting_info.setStartYear(String.valueOf(startDay.getYear()));
                                meeting_info.setStartMonth(StartMonth);
                                meeting_info.setStartDay(StartDay);

                                meeting_info.setEndDay(String.valueOf(endDay.getYear()));
                                meeting_info.setEndDay(EndMonth);
                                meeting_info.setEndDay(EndDay);

                                meeting_info.setMin(min.getText().toString());

                            }

                        }
                        Log.d("howmany", "onDataChange: ");
//                        databaseReference.child("meeting_List").child(key).setValue(null);
                        databaseReference.child("meeting_List").child(key).setValue(meeting_info);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
