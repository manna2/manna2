package org.ssutown.manna2.MaterialCalendar_Person;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.ssutown.manna2.Fragment.CalendarItem;
import org.ssutown.manna2.Fragment.FragmentHome;
import org.ssutown.manna2.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Maximilian on 9/1/14.
 */
public class MaterialCalendarFragment extends Fragment implements View.OnClickListener, GridView.OnItemClickListener{
    // Variables
    //Views
    ImageView mPrevious;
    ImageView mNext;
    ImageView mPlus;
    TextView mMonthName;
    GridView mCalendar;

    // Calendar Adapter
    private MaterialCalendarAdapter mMaterialCalendarAdapter;

    // Saved Events Adapter
    protected static CalendarEventsAdapter mSavedEventsAdapter;
    protected static ListView mSavedEventsListView;

    protected static ArrayList<String> mSavedDay;
    protected static ArrayList<CalendarItem> mSavedEvents;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference calendardb = database.getReference();

    protected long userID = FragmentHome.userID;

    protected static int mNumEventsOnDay = 0;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.person_calendar, container, false);
        if (rootView != null) {
            // Get Calendar info
            // Get Calendar info
            MaterialCalendar.getInitialCalendarInfo();
            getSavedEventsForCurrentMonth();

            // Previous ImageView
            mPrevious = (ImageView) rootView.findViewById(R.id.material_calendar_previous);
            if (mPrevious != null) {
                mPrevious.setOnClickListener(this);
            }

            // Month name TextView
            mMonthName = (TextView) rootView.findViewById(R.id.material_calendar_month_name);
            if (mMonthName != null) {
                Calendar cal = Calendar.getInstance();
                if (cal != null) {
                mMonthName.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG,
                        Locale.getDefault()) + " " + cal.get(Calendar.YEAR));
                }
            }

            // Next ImageView
            mNext = (ImageView) rootView.findViewById(R.id.material_calendar_next);
            if (mNext != null) {
                mNext.setOnClickListener(this);
            }

            // GridView for custom Calendar
            mCalendar = (GridView) rootView.findViewById(R.id.material_calendar_gridView);
            if (mCalendar != null) {
                mCalendar.setOnItemClickListener(this);
                mMaterialCalendarAdapter = new MaterialCalendarAdapter(getActivity());
                mCalendar.setAdapter(mMaterialCalendarAdapter);


                // Set current day to be auto selected when first opened
                if (MaterialCalendar.mCurrentDay != -1 && MaterialCalendar.mFirstDay != -1){
                    int startingPosition = 6 + MaterialCalendar.mFirstDay;
                    int currentDayPosition = startingPosition + MaterialCalendar.mCurrentDay;

                    Log.d("INITSITION", String.valueOf(currentDayPosition));

                    mCalendar.setItemChecked(currentDayPosition, true);

                    if (mMaterialCalendarAdapter != null) {
                        mMaterialCalendarAdapter.notifyDataSetChanged();
                    }
                }
            }

            mPlus = (ImageView)rootView.findViewById(R.id.material_calendar_add);
            if(mPlus != null) {
                mPlus.setOnClickListener(this);
            }

            // ListView for saved events in calendar
            mSavedEventsListView = (ListView) rootView.findViewById(R.id.saved_events_listView);
        }


        mSavedEvents = new ArrayList<CalendarItem>();
        mSavedDay = new ArrayList<String>();

        calendardb.child("user_Info").child(String.valueOf(userID)).child("calendar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mSavedEvents.clear();
                mSavedDay.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    mSavedEvents.add(ds.getValue(CalendarItem.class));
                    //들어감
                }
                for(int i=0;i<mSavedEvents.size();i++) {
                    String a = "year" + mSavedEvents.get(i).getStartyear() + "month" + mSavedEvents.get(i).getStartmonth() +
                            "day" + mSavedEvents.get(i).getStartday();
                    //들어감
                    mSavedDay.add(a);
                }
                mMaterialCalendarAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mSavedEventsListView != null) {
            mSavedEventsAdapter = new CalendarEventsAdapter(); //원래 getActivity()
            mSavedEventsListView.setAdapter(mSavedEventsAdapter);
            mSavedEventsListView.setOnItemClickListener(this);
            Log.d("EVENTS_ADAPTER", "set adapter");

            // Show current day saved events on load
            int today = MaterialCalendar.mCurrentDay + 6 + MaterialCalendar.mFirstDay;
            showSavedEventsListView(today);
        }
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.material_calendar_previous:
                    MaterialCalendar.previousOnClick(mPrevious, mMonthName, mCalendar, mMaterialCalendarAdapter);
                    break;

                case R.id.material_calendar_next:
                    MaterialCalendar.nextOnClick(mNext, mMonthName, mCalendar, mMaterialCalendarAdapter);
                    break;

                case R.id.material_calendar_add:
                    //일정추가
                    Intent intent = new Intent(getActivity(),AddAppointment_Person.class);
                    startActivity(intent);

                default:
                    break;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Toast toast = Toast.makeText(getActivity(),"hi",Toast.LENGTH_SHORT);
        toast.show();

        switch (parent.getId()) {
            case R.id.material_calendar_gridView:
                MaterialCalendar.selectCalendarDay(mMaterialCalendarAdapter, position);

                // Reset event list
                mNumEventsOnDay = -1;

                showSavedEventsListView(position);
                break;

            default:
                break;
        }
    }

    // Saved Events
    protected static void getSavedEventsForCurrentMonth() {

    }

    protected static void showSavedEventsListView(int position) {
        Boolean savedEventsOnThisDay = false;
        int selectedDate = -1;
        int selectedMonth = -1;
        int selectedYear = -1;
        String a = "";

        if (MaterialCalendar.mFirstDay != -1 && mSavedDay != null && mSavedDay.size
                () > 0) {
            selectedDate = position - (6 + MaterialCalendar.mFirstDay);
            selectedMonth = MaterialCalendar.mMonth + 1;
            selectedYear = MaterialCalendar.mYear;

            if ((selectedMonth < 10) && (selectedDate < 10)) {
                a = "year" + selectedYear + "month0" + selectedMonth + "day0" + selectedDate;

            } else if ((selectedMonth < 10)) {
                a = "year" + selectedYear + "month0" + selectedMonth + "day" + selectedDate;
            } else if (selectedDate < 10) {
                a = "year" + selectedYear + "month" + selectedMonth + "day0" + selectedDate;
            } else {
                a = "year" + selectedYear + "month" + selectedMonth + "day" + selectedDate;
            }

            for (int i = 0; i < mSavedDay.size(); i++) {
                if (a.equals(mSavedDay.get(i))) {
                    savedEventsOnThisDay = true;
                }
            }
        }

        if (savedEventsOnThisDay) {
            if (mSavedDay != null && mSavedDay.size() > 0) {
                for (int i = 0; i < mSavedDay.size(); i++) {
                    String x = mSavedDay.get(i);
                    if (x.equals(a)) {
                        Log.d("NUM_EVENT_ON_DAY", String.valueOf(mNumEventsOnDay));
                    }
                }
            }
        } else {
            mNumEventsOnDay = -1;
        }

        if (mSavedEventsAdapter != null && mSavedEventsListView != null) {
            mSavedEventsAdapter.clear();
            for(int i=0;i<mSavedDay.size(); i++){
                if(mSavedDay.get(i).equals(a)){
                    mSavedEventsAdapter.addItem(mSavedEvents.get(i).getEventname(), String.valueOf(mSavedEvents.get(i).getStart())+String.valueOf(mSavedEvents.get(i).getEnd()));
                }
            }
            mSavedEventsAdapter.notifyDataSetChanged();

            // Scrolls back to top of ListView before refresh
            mSavedEventsListView.setSelection(0);
        }
    }
}


