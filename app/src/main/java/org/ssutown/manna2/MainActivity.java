package org.ssutown.manna2;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {
    FragmentSample fragmentSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_in_fragment);
        fragmentSample=new FragmentSample();
        getSupportFragmentManager().beginTransaction().add(R.id.frame,fragmentSample).show(fragmentSample).commit();
    }
}
