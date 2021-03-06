package org.ssutown.manna2.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heima.tabview.library.TabView;
import com.heima.tabview.library.TabViewChild;

import org.ssutown.manna2.R;

import java.util.ArrayList;
import java.util.List;


public class FragmentSample extends Fragment {
    TabView tabView;
    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sample,container,false);
        tabView= (TabView) view.findViewById(R.id.tabView);
        init();
        return view;
    }
    private void init(){
        //start add data
        List<TabViewChild> tabViewChildList=new ArrayList<>();
        TabViewChild tabViewChild01=new TabViewChild(R.drawable.tab05_sel,R.drawable.tab05_unsel,"일정",  FragmentPerson.newInstance("일정"));
        TabViewChild tabViewChild02=new TabViewChild(R.drawable.meeting_sel,R.drawable.meeting_unsel,"미팅방",  FragmentMeeting.newInstance("미팅방"));
        TabViewChild tabViewChild03=new TabViewChild(R.drawable.home_sel,R.drawable.home_unsel,"홈",  FragmentHome.newInstance("홈"));
        TabViewChild tabViewChild04=new TabViewChild(R.drawable.calendar_sel,R.drawable.calendar_unsel,"스케쥴", FragmentSchedule.newInstance("스케쥴"));
        TabViewChild tabViewChild05=new TabViewChild(R.drawable.setting_sel,R.drawable.setting_unsel,"설정",  FragmentSetting.newInstance("설정"));
        tabViewChildList.add(tabViewChild01);
        tabViewChildList.add(tabViewChild02);
        tabViewChildList.add(tabViewChild03);
        tabViewChildList.add(tabViewChild04);
        tabViewChildList.add(tabViewChild05);
        //end add data
        tabView.setTabViewDefaultPosition(2);
        tabView.setTabViewChild(tabViewChildList,getChildFragmentManager());
        tabView.setOnTabChildClickListener(new TabView.OnTabChildClickListener() {
            @Override
            public void onTabChildClick(int  position, ImageView currentImageIcon, TextView currentTextView) {
                // Toast.makeText(getApplicationContext(),"position:"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
