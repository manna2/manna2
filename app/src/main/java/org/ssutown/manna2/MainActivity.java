package org.ssutown.manna2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import org.ssutown.manna2.Fragment.FragmentSample;

import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends FragmentActivity {
    FragmentSample fragmentSample;
    long userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_in_fragment);
        fragmentSample = new FragmentSample();
        getSupportFragmentManager().beginTransaction().add(R.id.frame, fragmentSample).show(fragmentSample).commit();
        
        login();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        SharedPreferences Kakao_Login = getSharedPreferences("Kakao_Login", MODE_PRIVATE);
        if (Kakao_Login.getBoolean("Kakao_Login", false)) {
            Log.d("KAKAO", "onActivityResult: " + String.valueOf(Kakao_Login.getLong("KAKAO_ID", 0)));
            userID = Kakao_Login.getLong("KAKAO_ID", 0);
        } else {
            Log.d("KAKAO", "onActivityResult: " + String.valueOf(Kakao_Login.getLong("KAKAO_ID", 0)));
            userID = Kakao_Login.getLong("KAKAO_ID", 0);
        }
    }

    public void login() {
        Intent Kakaotalk = new Intent(getApplicationContext(), SplashActivity.class);
        startActivityForResult(Kakaotalk, 0);
    }

    public long getUserID(){
        return userID;
    }

}

