package org.ssutown.manna2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.ssutown.manna2.Fragment.FragmentSample;
import org.ssutown.manna2.FragmentSetting_PatternLock.PatternLockApproach;
import org.ssutown.manna2.FragmentSetting_PatternLock.PatternLockStart;
import org.ssutown.manna2.HomeFragment.profile;
import org.ssutown.manna2.MeetingRoom.User;
import org.ssutown.manna2.MeetingRoom.meeting;

import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends FragmentActivity {
    FragmentSample fragmentSample;
    public static long userID;
    public static String animal;
    public static String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_in_fragment);
        fragmentSample = new FragmentSample();
        getSupportFragmentManager().beginTransaction().add(R.id.frame, fragmentSample).show(fragmentSample).commit();

        login();

        getInvitation();

        SharedPreferences Kakao_Login = getSharedPreferences("Kakao_Login", MODE_PRIVATE);
        userID = Kakao_Login.getLong("KAKAO_ID", 0);


//        if (Kakao_Login.getBoolean("Kakao_Login", false)){
////            FirebaseDatabase database = FirebaseDatabase.getInstance();
////            DatabaseReference databaseReference = database.getReference();
//            profile = new profile("NEW USER","bear");
//            databaseReference.child("user_Info").child(String.valueOf(userID)).child("profile").setValue(profile);
//        }

        Log.d("ID", "onCreate: " + getUserID());


        if(getPreferences() == true)
        {
            Intent intent = new Intent(getApplicationContext(), PatternLockStart.class);
            startActivity(intent);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        SharedPreferences Kakao_Login = getSharedPreferences("Kakao_Login", MODE_PRIVATE);
        if (Kakao_Login.getBoolean("Kakao_Login", false)) {
            Log.d("KAKAO", "onActivityResult: " + String.valueOf(Kakao_Login.getLong("KAKAO_ID", 0)));
            userID = Kakao_Login.getLong("KAKAO_ID", 0);
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference databaseReference = database.getReference();
            databaseReference.child("user_Info").child(String.valueOf(userID)).child("profile").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot == null){
                        profile profile = new profile("NEW USER","bear");
                        databaseReference.child("user_Info").child(String.valueOf(userID)).child("profile").setValue(profile);
                        nickname = dataSnapshot.getValue(profile.class).getNickname();
                        animal = dataSnapshot.getValue(profile.class).getAnimal();
                    }else{
                        nickname = dataSnapshot.getValue(profile.class).getNickname();
                        animal = dataSnapshot.getValue(profile.class).getAnimal();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            Log.d("KAKAO", "onActivityResult: " + String.valueOf(Kakao_Login.getLong("KAKAO_ID", 0)));
            userID = Kakao_Login.getLong("KAKAO_ID", 0);
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference databaseReference = database.getReference();
            databaseReference.child("user_Info").child(String.valueOf(userID)).child("profile").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot == null){
                        profile profile = new profile("NEW USER","bear");
                        databaseReference.child("user_Info").child(String.valueOf(userID)).child("profile").setValue(profile);
                        nickname = dataSnapshot.getValue(profile.class).getNickname();
                        animal = dataSnapshot.getValue(profile.class).getAnimal();
                    }else{
                        nickname = dataSnapshot.getValue(profile.class).getNickname();
                        animal = dataSnapshot.getValue(profile.class).getAnimal();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public void login() {
        Intent Kakaotalk = new Intent(getApplicationContext(), SplashActivity.class);
        startActivityForResult(Kakaotalk, 0);
    }

    public long getUserID(){
        return userID;
    }

    public String getAnimal() {return animal;}

    public String getNickname(){return nickname;}

    public void getInvitation(){

        final String meeting_id;
        String meeting_name;

        if(getIntent() != null){
            Uri uri = getIntent().getData();
            if(uri != null){
                Log.d(TAG, "invitation : " + uri.getQueryParameter("meeting_info"));
                String[] m = uri.getQueryParameter("meeting_info").split(Pattern.quote(":"));
                meeting_id = m[0];
                meeting_name = m[1];

                AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
                alertdialog.setMessage(meeting_name+"으로 초대되었습니다\n 가입을 원하십니까?");
                alertdialog.setPositiveButton("가입", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = firebaseDatabase.getReference();

                        User user = new User(animal,nickname,String.valueOf(userID));
                        databaseReference.child("meeting_Info").child(meeting_id).child("Users").push().setValue(user);//미팅에인원추가
                        meeting meetingList = new meeting(meeting_id);
                        databaseReference.child("user_Info").child(String.valueOf(userID)).child("personalMeetingList").push().setValue(meetingList);
                    }
                });

                alertdialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = alertdialog.create();
                alert.setTitle("MANNA");
                alert.show();

            }
        }

    }

    private Boolean getPreferences(){
        SharedPreferences pref = getSharedPreferences("prefC", MODE_PRIVATE);
        return pref.getBoolean("on/off",false);
    }

}

