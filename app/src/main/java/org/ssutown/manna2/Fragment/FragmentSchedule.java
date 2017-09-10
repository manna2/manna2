package org.ssutown.manna2.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ssutown.manna2.MaterialCalendar_Schedule.MaterialCalendarFragment;
import org.ssutown.manna2.R;


public class  FragmentSchedule extends Fragment {
    TextView textView;

    public static FragmentSchedule newInstance(String text){
        FragmentSchedule fragmentSchedule=new FragmentSchedule();
        Bundle bundle=new Bundle();
        bundle.putString("text",text);
        fragmentSchedule.setArguments(bundle);
        return fragmentSchedule;
    }
    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_schedule,container,false);
        if (savedInstanceState == null) {
            getActivity().getFragmentManager().beginTransaction().add(R.id.main_container2, new MaterialCalendarFragment()).commit();
        }
        return view;
    }
}
