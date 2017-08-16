package org.ssutown.manna2.MeetingFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heima.tabview.library.TabView;
import com.heima.tabview.library.TabViewChild;

import org.ssutown.manna2.MeetingFragment_Merge.BaseActivity;
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
        Log.i("meetroom1", "dfwehrd");
        List<TabViewChild> tabViewChildList=new ArrayList<>();
        TabViewChild tabViewChild01=new TabViewChild(R.drawable.notice_sel,R.drawable.notice_unsel,"공지사항",  FragmentNotice.newInstance("공지사항"));
        TabViewChild tabViewChild02=new TabViewChild(R.drawable.integrated_sel,R.drawable.integrated_unsel,"일정 통합",  FragmentIntegrated.newInstance("일정통합"));
        TabViewChild tabViewChild03=new TabViewChild(R.drawable.setting_sel,R.drawable.setting_unsel,"설정",  FragmentMeetingSetting.newInstance("설정"));
        tabViewChildList.add(tabViewChild01);
        tabViewChildList.add(tabViewChild02);
        tabViewChildList.add(tabViewChild03);
        //end add data
        tabView.setTabViewDefaultPosition(0);
        tabView.setTabViewChild(tabViewChildList,getChildFragmentManager());
        tabView.setOnTabChildClickListener(new TabView.OnTabChildClickListener() {
            @Override
            public void onTabChildClick(int  position, ImageView currentImageIcon, TextView currentTextView) {
                if(position==1) {
                    Intent intent = new Intent(getActivity(), BaseActivity.class);
                    startActivity(intent);
                }
                // Toast.makeText(getApplicationContext(),"position:"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
