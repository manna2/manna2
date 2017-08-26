package org.ssutown.manna2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.ssutown.manna2.Fragment.FragmentSample;
import org.ssutown.manna2.HomeFragment.profile;

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
        
        SharedPreferences Kakao_Login = getSharedPreferences("Kakao_Login", MODE_PRIVATE);
        userID = Kakao_Login.getLong("KAKAO_ID", 0);

//        if (Kakao_Login.getBoolean("Kakao_Login", false)){
////            FirebaseDatabase database = FirebaseDatabase.getInstance();
////            DatabaseReference databaseReference = database.getReference();
//            profile = new profile("NEW USER","bear");
//            databaseReference.child("user_Info").child(String.valueOf(userID)).child("profile").setValue(profile);
//        }

        Log.d("ID", "onCreate: " + getUserID());

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

}

