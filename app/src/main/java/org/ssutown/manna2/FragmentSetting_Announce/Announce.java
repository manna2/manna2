package org.ssutown.manna2.FragmentSetting_Announce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import org.ssutown.manna2.R;

public class Announce extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Expanded",
//                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Collapsed",
//                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
//                Toast.makeText(
//                        getApplicationContext(),
//                        listDataHeader.get(groupPosition)
//                                + " : "
//                                + listDataChild.get(
//                                listDataHeader.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT)
//                        .show();
                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("ABOUT MANNA");
        listDataHeader.add("HOW TO USE");
        listDataHeader.add("COPYRIGHT OF MANNA");

        // Adding child data
        List<String> aboutMANNA = new ArrayList<String>();
        aboutMANNA.add("팀원들의 캘린더를 통합하여 미팅이 가능한 공백 날짜와 시간을 추출해주는 일정관리 APP");

        List<String> howToUse = new ArrayList<String>();
        howToUse.add("Outlook이나 Google 계정을 이용하여 자신의 캘린더를 앱 내의 캘린더와 동기화 시킨후"
                    +"미팅을 잡고 싶은 기간과 시간을 입력하여 방을 생성한 후 필터링을 적용시키면 팀원들이"
                    +"만날 수 있는 공백 일정과 시간을 시간적으로 볼 수 있다. "
                    +"미팅 날짜와 시간을 정하게 된다면 앱내의 캘린더를 물론 기존 계정 로그인이 되어있는 캘린더에도"
                    +"자동 동기화 시켜주며 공지사항에도 올라가 모든 팀원들이 확인할 수 있다.");

        List<String> copyRight = new ArrayList<String>();
        copyRight.add("Soongsil University"+"\r\n"+"2017 김혜민,윤나혜,한지연");

        listDataChild.put(listDataHeader.get(0), aboutMANNA); // Header, Child data
        listDataChild.put(listDataHeader.get(1), howToUse);
        listDataChild.put(listDataHeader.get(2), copyRight);
    }

}
