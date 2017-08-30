package org.ssutown.manna2.FragmentSetting_PatternLock;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.content.Intent;

import org.ssutown.manna2.R;

public class Security_Lock extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_lock);

        CheckBox checkbox = (CheckBox) findViewById(R.id.checkbox);
        final Button changePattern = (Button)findViewById(R.id.change);

        checkbox.setChecked(true);

        checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (buttonView.getId() == R.id.checkbox) {
                    if (isChecked) {
//                        Toast.makeText(getApplicationContext(), "눌림", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(),PatternLock.class);
                        startActivity(intent);

                        savePreferences(true);

                        finish();


                    } else {
//                            Toast.makeText(getApplicationContext(), "안눌림", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "암호잠금이 해제되었습니다", Toast.LENGTH_SHORT).show();

                        savePreferences(false);

                        changePattern.setEnabled(false);

                    }
                }
            }
        });

        View.OnClickListener listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(),PatternLock.class);
                startActivity(intent);

                finish();
            }
        };

        changePattern.setOnClickListener(listener);
    }

    private Boolean getPreferences(){
        SharedPreferences pref = getSharedPreferences("prefC", MODE_PRIVATE);
        return pref.getBoolean("on/off",false);
    }

    private void savePreferences(Boolean check){
        SharedPreferences pref = getSharedPreferences("prefC", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("on/off", check);
        editor.commit();
    }

}
