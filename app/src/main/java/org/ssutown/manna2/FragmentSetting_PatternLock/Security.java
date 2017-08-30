package org.ssutown.manna2.FragmentSetting_PatternLock;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.content.Intent;

import org.ssutown.manna2.R;

public class Security extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        CheckBox checkbox = (CheckBox) findViewById(R.id.checkbox);

        checkbox.setChecked(false); //우선 초기에 checkbox는 false로 설

        checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (buttonView.getId() == R.id.checkbox) {
                    if (isChecked) {
//                        Toast.makeText(getApplicationContext(), "눌림", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(),PatternLock.class);
                        startActivity(intent);

                    } else {
//                            Toast.makeText(getApplicationContext(), "안눌림", Toast.LENGTH_SHORT).show();


                    }
                }
            }
        });
    }
}

