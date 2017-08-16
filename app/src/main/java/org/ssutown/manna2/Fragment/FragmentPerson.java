package org.ssutown.manna2.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ssutown.manna2.MaterialCalendar_Person.MaterialCalendarFragment;
import org.ssutown.manna2.R;


public class FragmentPerson extends Fragment {

    public static FragmentPerson newInstance(String text){
        FragmentPerson fragmentPerson=new FragmentPerson();
        Bundle bundle=new Bundle();
        bundle.putString("text",text);
        fragmentPerson.setArguments(bundle);
        return fragmentPerson;
    }
    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_person,container,false);
        if (savedInstanceState == null) {
            getActivity().getFragmentManager().beginTransaction().add(R.id.main_container1, new MaterialCalendarFragment()).commit();
        }
        return view;
    }
}
