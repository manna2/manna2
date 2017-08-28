package org.ssutown.manna2.FragmentSetting_Announce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import org.ssutown.manna2.R;

/**
 * Created by yunnahae on 2017. 8. 28..
 */

public class Announce extends AppCompatActivity {

    static final String[] LIST_MENU = {"What is MANNA APP?", "How to use", "Copy right of MANNA"} ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_announce) ;

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU) ;

        ListView listview = (ListView) findViewById(R.id.listview1) ;
        listview.setAdapter(adapter) ;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                String strText = (String) parent.getItemAtPosition(position) ;

                if(position == 0)
                {

                }
                else if(position == 1)
                {

                }
                else if(position == 2)
                {

                }
            }
        }) ;

    }

}
