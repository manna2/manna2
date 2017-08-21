package org.ssutown.manna2.HomeFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.ssutown.manna2.MainActivity;
import org.ssutown.manna2.R;

public class ChangeProfile extends AppCompatActivity {

    String animal = "default";
    String nickname = "user";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    String userid = "0";
    private static final int PROFILE_CHANGED = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        ImageButton bear = (ImageButton)findViewById(R.id.bear);
        ImageButton cat = (ImageButton)findViewById(R.id.cat);
        ImageButton cheetah = (ImageButton)findViewById(R.id.cheetha);
        ImageButton cow = (ImageButton)findViewById(R.id.cow);
        ImageButton fox = (ImageButton)findViewById(R.id.fox);
        ImageButton hedgehog = (ImageButton)findViewById(R.id.hedgehog);
        ImageButton tiger = (ImageButton)findViewById(R.id.tiger);
        ImageButton wolf = (ImageButton)findViewById(R.id.wolf);
        ImageButton pig = (ImageButton)findViewById(R.id.pig);

        bear.setOnClickListener(mClickListener);
        cat.setOnClickListener(mClickListener);
        cheetah.setOnClickListener(mClickListener);
        cow.setOnClickListener(mClickListener);
        fox.setOnClickListener(mClickListener);
        hedgehog.setOnClickListener(mClickListener);
        tiger.setOnClickListener(mClickListener);
        wolf.setOnClickListener(mClickListener);
        pig.setOnClickListener(mClickListener);

        Intent intent = getIntent();
        userid = intent.getExtras().getString("userid");
        Log.d("chagneprofile", "userid change profile: " + userid);
        final EditText getnickname = (EditText)findViewById(R.id.nickname);

        final ImageButton finish = (ImageButton)findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile profile = new profile(getnickname.getText().toString(),animal);
                databaseReference.child("user_Info").child(userid).child("profile").setValue(profile);

                Intent returnIntent = new Intent();
                setResult(PROFILE_CHANGED, returnIntent);

                finish();
            }
        });
    }

    ImageButton.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bear:
                    animal = "bear";
                    Log.d("animal", "animal onClick: " + animal);
                    break;
                case R.id.cat:
                    animal = "cat";
                    Log.d("animal", "animal onClick: " + animal);
                    break;
                case R.id.cheetha:
                    animal = "cheetha";
                    break;
                case R.id.cow:
                    animal = "cow";
                    break;
                case R.id.fox:
                    animal = "fox";
                    break;
                case R.id.hedgehog:
                    animal = "hedgehog";
                    break;
                case R.id.tiger:
                    animal = "tiger";
                    break;
                case R.id.wolf:
                    animal = "wolf";
                    break;
                case R.id.pig:
                    animal = "pig";
                    break;
            }
        }
    };

}
