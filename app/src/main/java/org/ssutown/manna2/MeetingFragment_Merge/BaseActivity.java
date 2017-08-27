package org.ssutown.manna2.MeetingFragment_Merge;

import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.ssutown.manna2.Fragment.CalendarItem;
import org.ssutown.manna2.MainActivity;
import org.ssutown.manna2.MeetingFragment.MeetingMainActivity;
import org.ssutown.manna2.MeetingRoom.User;
import org.ssutown.manna2.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * This is a base activity which contains week view and all the codes necessary to initialize the
 * week view.
 * Created by Raquib-ul-Alam Kanak on 1/3/2014.
 * Website: http://alamkanak.github.io
 */
public class BaseActivity extends AppCompatActivity implements WeekView.EventClickListener, MonthLoader.MonthChangeListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener {
    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;

    private int mWeekViewType = TYPE_WEEK_VIEW;
    private WeekView mWeekView;

    private ArrayList<User> memberID = MeetingMainActivity.users;
    private long userID = MainActivity.userID;
    private ArrayList<HashMap<String, String>> mSavedEvents;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    private ArrayList<MergeCalendar> mergeCalendars;

    List<WeekViewEvent> eventList = new ArrayList<WeekViewEvent>();

    protected ArrayList<String> aaa = new ArrayList<>();
    protected int test = 0;
    TextView filter;
    boolean complete = false;


    List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
    int t = 0;

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        for (int i = 0; i < eventList.size(); i++) {
            WeekViewEvent event = eventList.get(i);
            Log.e("onMonthChange For Loop", "Month : " + (event.getStartTime().MONTH+1) + " / Year " + event.getStartTime().YEAR );
            if ((event.getStartTime().get(Calendar.MONTH)+1) == newMonth  && event.getStartTime().get(Calendar.YEAR) == newYear) {
//                event.setColor(getResources().getColor(R.color.event_color_01));
                events.add(event);
            }

        }

        return events;
    }
//    @Override
//    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
//        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
//
//        Calendar startTime = Calendar.getInstance();
//        startTime.set(Calendar.HOUR_OF_DAY, 2);//2시
//        startTime.set(Calendar.MINUTE, 00);//30분
//        startTime.set(Calendar.MONTH, 5);//7월
//        startTime.set(Calendar.YEAR, 2017);//연도
//        startTime.set(Calendar.DATE, 17);
//        Calendar endTime = (Calendar) startTime.clone();
//        endTime.set(Calendar.HOUR_OF_DAY, 3);
//        endTime.set(Calendar.MINUTE, 00);
//        endTime.set(Calendar.MONTH, 5); // 7월
//        WeekViewEvent event = new WeekViewEvent(10, getEventTitle(startTime), startTime, endTime);
//        event.setColor(getResources().getColor(R.color.event_color_02));
//        events.add(event);
//
//        return events;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        mSavedEvents = new ArrayList<HashMap<String, String>>();
        mSavedEvents.clear();

        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);

        mWeekView.setNumberOfVisibleDays(6);
        mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
        mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

        // Set long press listener for empty view
        mWeekView.setEmptyViewLongPressListener(this);

        //필터설정

        filter = (TextView)findViewById(R.id.member);
        filter.setText(String.valueOf(memberID.size()));

        Button minus = (Button)findViewById(R.id.left);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = Integer.parseInt(filter.getText().toString());
                Log.d("plus", "onClick: " + temp);
                filter.setText(String.valueOf(temp-1));
            }
        });

        Button plus = (Button)findViewById(R.id.right);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = Integer.parseInt(filter.getText().toString());
                Log.d("plus", "onClick: " + temp);
                filter.setText(String.valueOf(temp+1));
            }
        });


        test = 3;

        aaa.add("gggg");
        aaa.add("bbbb");
        aaa.add("cccc");

        setupDateTimeInterpreter(false);
        getCalendar();
    }


    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     * @param shortDate True if the date values should be short.
     */
    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }

    public void getCalendar() {

        for (int i = 0; i < memberID.size(); i++) {
            final int a = i;
            databaseReference.child("user_Info").
                    child(memberID.get(i).getUserID()).child("calendar").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        HashMap<String, String> temp = new HashMap<String, String>();

                        if(ds.getValue(CalendarItem.class).getStarthour().equals("x") ){
                            if(ds.getValue(CalendarItem.class).getStartyear().equals(ds.getValue(CalendarItem.class).getEndyear()) &&
                                    ds.getValue(CalendarItem.class).getStartmonth().equals(ds.getValue(CalendarItem.class).getEndmonth()) &&
                                            ds.getValue(CalendarItem.class).getStartday().equals(ds.getValue(CalendarItem.class).getEndday())) {
                                temp.put(ds.getValue(CalendarItem.class).getStartyear()
                                                +ds.getValue(CalendarItem.class).getStartmonth()
                                                +ds.getValue(CalendarItem.class).getStartday(),
                                        "0");
                                mSavedEvents.add(temp);
                            }
                            else {
                                String start = ds.getValue(CalendarItem.class).getStartyear()+
                                        ds.getValue(CalendarItem.class).getStartmonth()+
                                        ds.getValue(CalendarItem.class).getStartday();
                                String end = ds.getValue(CalendarItem.class).getEndyear()+
                                        ds.getValue(CalendarItem.class).getEndmonth()+
                                        ds.getValue(CalendarItem.class).getEndday();
                                String result = "";
                                try {
                                    result = diffOfDate(start, end);
                                }catch(java.lang.Exception es){

                                }

                                temp.put(ds.getValue(CalendarItem.class).getStartyear()
                                                +ds.getValue(CalendarItem.class).getStartmonth()
                                                +ds.getValue(CalendarItem.class).getStartday(),
                                        result+"]" + ds.getValue(CalendarItem.class).getEndyear()
                                                + ds.getValue(CalendarItem.class).getEndmonth()
                                                + ds.getValue(CalendarItem.class).getEndday());
                                mSavedEvents.add(temp);
                            }
                        }else {
                                temp.put(ds.getValue(CalendarItem.class).getStartyear()
                                            + ds.getValue(CalendarItem.class).getStartmonth()
                                            +ds.getValue(CalendarItem.class).getStartday(),
                                    ds.getValue(CalendarItem.class).getStarthour()
                                            + ":" + ds.getValue(CalendarItem.class).getStartminute()
                                            + "-" + ds.getValue(CalendarItem.class).getEndhour()
                                            + ":" + ds.getValue(CalendarItem.class).getEndminute());
                            mSavedEvents.add(temp);
                        }

                    }
                    for(int i=0;i<mSavedEvents.size() ; i++){
                        Log.i("size", mSavedEvents.get(i).toString());
                    }
                    if(a == memberID.size()-1)
                        mergeCalendar();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
    }



    public static String diffOfDate(String begin, String end) throws Exception
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        Date beginDate = formatter.parse(begin);
        Date endDate = formatter.parse(end);

        long diff = endDate.getTime() - beginDate.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        String result = String.valueOf(diffDays);
        return result;
    }

    public void mergeCalendar() {

        Log.i("dkssud", String.valueOf(mSavedEvents.size()));
//        String a[] = {"year2017month07day11", "year2017month07day12"};
//        String a[] = {"year2017month06day20", "year2017month06day21","year2017month06day22","year2017month06day23","year2017month06day24"};
        // 여기는 나중에 날짜 받으면 수정해주면 될듯
        String startendDate = MeetingMainActivity.startendDate;
        Log.i("dkssud1", startendDate+"입니다");
        String startyear = startendDate.substring(0,4);
        String startmonth = startendDate.substring(4,6);
        String startday = startendDate.substring(6,8);
        String interval = startendDate.substring(8);

        startendDate = startendDate.substring(0,8);
        String currentDate = startendDate;
        Log.i("dkssud2",startyear+startmonth+startday+interval+"입니다");
        //시작 날짜 parse해주고, 며칠동안 있는지 따로 저장해주고
        //mergeCalendar class만들어서 작업

        mergeCalendars = new ArrayList<MergeCalendar>();
        mergeCalendars.clear();
        int[] count;
        int daysum = Integer.valueOf(interval);
        for (int i = 0; i <= daysum; i++) {
            count = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            for (int j = 0; j < mSavedEvents.size(); j++) {
                Log.i("mSavedEvents", mSavedEvents.get(j).toString());
                String saveCurrentday1 = mSavedEvents.get(j).toString().substring(1,9);
                //일정 시작일
                String saveCurrentday2 = mSavedEvents.get(j).toString().substring(10);
                //일정 끝나는 일 또는 시간

                if(saveCurrentday1.equals(currentDate)) {
                    //종일말고 시간 일정 먼저
                    if (saveCurrentday2.contains(":")) {
                        int starthour = Integer.valueOf(saveCurrentday2.substring(0, 2));
                        int endhour = Integer.valueOf(saveCurrentday2.substring(6, 8));
                        for (int k = starthour; k < endhour; k++) {
                            count[k]++;
                        }
                    } else {
                        for (int k = 0; k < 24; k++)
                            count[k]++;
                    }
                }
                else if(saveCurrentday2.contains("]")){

                    //며칠 걸리는 일정// 편하게 여행일정 !! 여행은 며칠동안 가따오니까!!

                    String eventinterval = saveCurrentday2.split("]")[0];
                    //며칠동안 일정이 지속되는지
                    if(!eventinterval.equals("1")){
                        //종일 일정이 아닐경우 여행일정
                        Log.i("dkssud3", currentDate);
                        //currentdate는 현재 일
                        String finalevent = saveCurrentday2.split("]")[1].substring(0,8);
                        //finalevent는 일정의 마지막 일

                        if(Integer.valueOf(saveCurrentday1) < Integer.valueOf(currentDate) &&
                                Integer.valueOf(currentDate) < Integer.valueOf(finalevent)){
                            for (int k = 0; k < 24; k++)
                                count[k]++;
                        }
                    }

                }

                }
            for(int a=0;a<24;a++) {
                Log.i("count", String.valueOf(count[a])+"이다");
            }

            MergeCalendar mergecal = new MergeCalendar(count, currentDate);
            mergeCalendars.add(mergecal);
            Log.i("forhyemin1", String.valueOf(mergeCalendars.size()));

            //시간 하루 늘려주는 함수
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", java.util.Locale.getDefault());
                    Date date1 = dateFormat.parse(currentDate);
                    date1.setDate(date1.getDate() + 1);
                    Log.i("date1", String.valueOf(date1));
                    currentDate = dateFormat.format(date1);
                    Log.i("date1", currentDate);

                }
                catch (java.text.ParseException e) {

                }

            }

            //hyemin
        eventList.clear();

//        int fil = Integer.parseInt(filter.getText().toString());

        int fil = 0;
        Calendar startTime = Calendar.getInstance();
        Calendar endTime;

        //날짜랑 카운트 받아서 하는곳
        for(int i =0; i < mergeCalendars.size(); i++){
            int tmpcount[] = mergeCalendars.get(i).getCount();
            String date = mergeCalendars.get(i).getDate();

            int year = Integer.parseInt(date.substring(0,4));
            int month = Integer.parseInt(date.substring(4,6));
            int day = Integer.parseInt(date.substring(6,8));

            int min = Integer.parseInt(MeetingMainActivity.info.getMin());
            Log.d("hyeminndate", "mergeCalendar: " + year + month + day);

            Log.d("aaa", "userinfo Size: " + memberID.size());

            for(int k = 0; k<tmpcount.length; k++){
                int available = memberID.size() - tmpcount[k];
                int tmp = k+1;
                Log.d("aaa", "getEventList: " + k + " " + tmp);
                if(available >= fil){
                    startTime = Calendar.getInstance();
                    startTime.set(Calendar.MONTH, month-1);//7월
                    startTime.set(Calendar.YEAR, year);//연도
                    startTime.set(Calendar.DATE, day);
                    startTime.set(Calendar.HOUR_OF_DAY, k);//2시
                    startTime.set(Calendar.MINUTE, 00);
                    endTime = (Calendar) startTime.clone();
                    endTime.set(Calendar.HOUR_OF_DAY, tmp);
                    endTime.set(Calendar.MINUTE, 00);
                    endTime.set(Calendar.MONTH, month-1); // 7월
                    WeekViewEvent event = new WeekViewEvent(10, " ", startTime, endTime);
                    event.setColor(getResources().getColor(R.color.event_color_03));
                    eventList.add(event);
                } else if(fil > available && fil<min) {
                    startTime = Calendar.getInstance();
                    startTime.set(Calendar.MONTH, month - 1);//7월
                    startTime.set(Calendar.YEAR, year);//연도
                    startTime.set(Calendar.DATE, day);
                    startTime.set(Calendar.HOUR_OF_DAY, k);//2시
                    startTime.set(Calendar.MINUTE, 00);
                    endTime = (Calendar) startTime.clone();
                    endTime.set(Calendar.HOUR_OF_DAY, tmp);
                    endTime.set(Calendar.MINUTE, 00);
                    endTime.set(Calendar.MONTH, month - 1); // 7월
                    WeekViewEvent event = new WeekViewEvent(10, " ", startTime, endTime);
                    event.setColor(getResources().getColor(R.color.event_color_04));
                    eventList.add(event);
                } else if( available < min){
                    startTime = Calendar.getInstance();
                    startTime.set(Calendar.MONTH, month-1);//7월
                    startTime.set(Calendar.YEAR, year);//연도
                    startTime.set(Calendar.DATE, day);
                    startTime.set(Calendar.HOUR_OF_DAY, k);//2시
                    startTime.set(Calendar.MINUTE, 00);
                    endTime = (Calendar) startTime.clone();
                    endTime.set(Calendar.HOUR_OF_DAY, tmp);
                    endTime.set(Calendar.MINUTE, 00);
                    endTime.set(Calendar.MONTH, month-1); // 7월
                    WeekViewEvent event = new WeekViewEvent(10, " ", startTime, endTime);
                    event.setColor(getResources().getColor(R.color.event_color_02));
                    eventList.add(event);
                }

            }

            mWeekView.notifyDatasetChanged();
        }

        complete = true;


    }

    protected String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(this, "Clicked " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(this, "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {
        Toast.makeText(this, "Empty view long pressed: " + getEventTitle(time), Toast.LENGTH_SHORT).show();
    }

    public WeekView getWeekView() {
        return mWeekView;
    }
}
