package org.ssutown.manna2.MeetingFragment_Merge;

import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
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

    private ArrayList<String> memberID = MeetingMainActivity.MemberID;
    private long userID = MainActivity.userID;
    private ArrayList<HashMap<String, String>> mSavedEvents;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();


    protected ArrayList<String> aaa = new ArrayList<>();
    protected int test = 0;


    List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
    int t = 0;

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        // Populate the week view with some events.
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 1);
        endTime.set(Calendar.MONTH, newMonth - 1);
        WeekViewEvent event = new WeekViewEvent(1, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);


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
                    child(memberID.get(i)).child("calendar").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        HashMap<String, String> temp = new HashMap<String, String>();

                        if(ds.getValue(CalendarItem.class).getStarthour().equals("x")){
                            if(ds.getValue(CalendarItem.class).getStartyear().equals(ds.getValue(CalendarItem.class).getEndyear()) &&
                                    ds.getValue(CalendarItem.class).getStartmonth().equals(ds.getValue(CalendarItem.class).getEndmonth()) &&
                                            ds.getValue(CalendarItem.class).getStartday().equals(ds.getValue(CalendarItem.class).getEndday())) {
                                temp.put("year" + ds.getValue(CalendarItem.class).getStartyear()
                                                + "month" + ds.getValue(CalendarItem.class).getStartmonth()
                                                + "day" + ds.getValue(CalendarItem.class).getStartday(),
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

                                temp.put("year" + ds.getValue(CalendarItem.class).getStartyear()
                                                + "month" + ds.getValue(CalendarItem.class).getStartmonth()
                                                + "day" + ds.getValue(CalendarItem.class).getStartday(),
                                        result+"+year" + ds.getValue(CalendarItem.class).getEndyear()
                                                + "month" + ds.getValue(CalendarItem.class).getEndmonth()
                                                + "day" + ds.getValue(CalendarItem.class).getEndday());
                                mSavedEvents.add(temp);
                            }
                        }else {
                            temp.put("year" + ds.getValue(CalendarItem.class).getStartyear()
                                            + "month" + ds.getValue(CalendarItem.class).getStartmonth()
                                            + "day" + ds.getValue(CalendarItem.class).getStartday(),
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
        String a[] = {"year2017month06day20", "year2017month06day21","year2017month06day22","year2017month06day23","year2017month06day24"};
        // 여기는 나중에 날짜 받으면 수정해주면 될듯
        String startendDate = MeetingMainActivity.startendDate;
        Log.i("dkssud", startendDate+"입니다");


        //시작 날짜 parse해주고, 며칠동안 있는지 따로 저장해주고
        //mergeCalendar class만들어서 작업

//        mergeCalendars = new ArrayList<MergeCalendar>();
//        mergeCalendars.clear();
//        int[] count;
//        int daysum = 5;
//        for (int i = 0; i < 5; i++) {
//            count = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
////            count = new int[24];
//            Log.i("infori", String.valueOf(i));
//            Log.i("infori", String.valueOf(mSavedEvents.size()));
//            for (int j = 0; j < mSavedEvents.size(); j++) {
//                Log.i("inforj", String.valueOf(mSavedEvents.size()));
//                //여기서 string잘못뽑아서 에러날수도 있어
//                String yearmonthday[] = mSavedEvents.get(j).toString().split("=");
//                yearmonthday[0] = yearmonthday[0].replace("{", "");
//                Log.i("mSavedEvents", yearmonthday[0]);
//                Log.i("a[]", a[i]);
//
//                if (yearmonthday[0].equals(a[i])) {
//                    //종일 이벤트는 처리 안해줌
//                    String dayParsetime[] = mSavedEvents.get(j).toString().split("=");
//                    String startParseend[] = dayParsetime[1].split("-");
//                    String starttime[] = startParseend[0].split(":");
//                    String endtime[] = startParseend[1].split(":");
//                    int starthour = Integer.valueOf(starttime[0]);
//                    endtime[0] = endtime[0].replace("}", "");
//                    int endhour = Integer.valueOf(endtime[0]);
//                    Log.i("forhyemin", "i: " + String.valueOf(i) + "j :" + String.valueOf(j) +
//                            "starthour: " + String.valueOf(starthour) + "endhour: " + String.valueOf(endhour));
//                    for (int k = starthour; k < endhour; k++) {
//                        count[k]++;
//                    }
////                    MergeCalendar mergecal = new MergeCalendar(count, a[i]);
////                    mergeCalendars.add(mergecal);
////                    Log.i("forhyemin1", String.valueOf(mergeCalendars.size()));
//
//                }
//            }
//
//            MergeCalendar mergecal = new MergeCalendar(count, a[i]);
//            mergeCalendars.add(mergecal);
//            Log.i("forhyemin1", String.valueOf(mergeCalendars.size()));
//
//        }
//
//        eventList.clear();
////        Calendar startTime = Calendar.getInstance();
////        startTime.set(Calendar.MONTH, month-1);//7월
////        startTime.set(Calendar.YEAR, 2017);//연도
////        startTime.set(Calendar.DATE, 17);
////        startTime.set(Calendar.HOUR_OF_DAY, 0);//2시
////        startTime.set(Calendar.MINUTE, 00);
////        Calendar endTime = (Calendar) startTime.clone();
////        endTime.set(Calendar.HOUR_OF_DAY, 1);
////
////        endTime.set(Calendar.MINUTE, 00);
////        endTime.set(Calendar.MONTH, month-1); // 7월
////        WeekViewEvent event = new WeekViewEvent(10, " ", startTime, endTime);
////        event.setColor(getResources().getColor(R.color.event_color_03));
////        eventList.add(event);
////
////        startTime = Calendar.getInstance();
////        startTime.set(Calendar.MONTH, month-1);//7월
////        startTime.set(Calendar.YEAR, 2017);//연도
////        startTime.set(Calendar.DATE, 17);
////        startTime.set(Calendar.HOUR_OF_DAY, 1);//2시
////        startTime.set(Calendar.MINUTE, 00);
////        endTime = (Calendar) startTime.clone();
////        endTime.set(Calendar.HOUR_OF_DAY, 2);
////        endTime.set(Calendar.MINUTE, 00);
////        endTime.set(Calendar.MONTH, month-1); // 7월
////        event = new WeekViewEvent(10, " ", startTime, endTime);
////        event.setColor(getResources().getColor(R.color.event_color_02));
////        eventList.add(event);
//
//        int filter = 2;
//        Calendar startTime = Calendar.getInstance();
//        Calendar endTime;
//
//        for(int i =0; i < mergeCalendars.size(); i++){
//            int tmpcount[] = mergeCalendars.get(i).getCount();
//            String date = mergeCalendars.get(i).getDate();
//            int year = Integer.parseInt(date.substring(4,8));
//            int month = Integer.parseInt(date.substring(13,15));
//            int day = Integer.parseInt(date.substring(18,20));
//
//            Log.d("aaa", "getEventList: " + year + month + day);
//            Log.d("aaa", "userinfo Size: " + userinfo.size());
//
//            for(int k = 0; k<tmpcount.length; k++){
//                int available = userinfo.size() - tmpcount[k];
//                int tmp = k+1;
//                Log.d("aaa", "getEventList: " + k + " " + tmp);
//                if(available >= filter){
//                    startTime = Calendar.getInstance();
//                    startTime.set(Calendar.MONTH, month-1);//7월
//                    startTime.set(Calendar.YEAR, year);//연도
//                    startTime.set(Calendar.DATE, day);
//                    startTime.set(Calendar.HOUR_OF_DAY, k);//2시
//                    startTime.set(Calendar.MINUTE, 00);
//                    endTime = (Calendar) startTime.clone();
//                    endTime.set(Calendar.HOUR_OF_DAY, tmp);
//                    endTime.set(Calendar.MINUTE, 00);
//                    endTime.set(Calendar.MONTH, month-1); // 7월
//                    WeekViewEvent event = new WeekViewEvent(10, " ", startTime, endTime);
//                    event.setColor(getResources().getColor(R.color.event_color_03));
//                    eventList.add(event);
//                } else if(available < filter){
//                    if(available == 0){
//                        startTime = Calendar.getInstance();
//                        startTime.set(Calendar.MONTH, month-1);//7월
//                        startTime.set(Calendar.YEAR, year);//연도
//                        startTime.set(Calendar.DATE, day);
//                        startTime.set(Calendar.HOUR_OF_DAY, k);//2시
//                        startTime.set(Calendar.MINUTE, 00);
//                        endTime = (Calendar) startTime.clone();
//                        endTime.set(Calendar.HOUR_OF_DAY, tmp);
//                        endTime.set(Calendar.MINUTE, 00);
//                        endTime.set(Calendar.MONTH, month-1); // 7월
//                        WeekViewEvent event = new WeekViewEvent(10, " ", startTime, endTime);
//                        event.setColor(getResources().getColor(R.color.event_color_02));
//                        eventList.add(event);
//                    }else{
//                        startTime = Calendar.getInstance();
//                        startTime.set(Calendar.MONTH, month-1);//7월
//                        startTime.set(Calendar.YEAR, year);//연도
//                        startTime.set(Calendar.DATE, day);
//                        startTime.set(Calendar.HOUR_OF_DAY, k);//2시
//                        startTime.set(Calendar.MINUTE, 00);
//                        endTime = (Calendar) startTime.clone();
//                        endTime.set(Calendar.HOUR_OF_DAY, tmp);
//                        endTime.set(Calendar.MINUTE, 00);
//                        endTime.set(Calendar.MONTH, month-1); // 7월
//                        WeekViewEvent event = new WeekViewEvent(10, " ", startTime, endTime);
//                        event.setColor(getResources().getColor(R.color.event_color_04));
//                        eventList.add(event);
//                    }
//                }
//
//            }
//
//            mWeekView.notifyDatasetChanged();
//        }
//
//        complete = true;
//
//        Log.d("aa", "onCreate: complete" + complete);
//
//        int dkssud1[] = mergeCalendars.get(0).getCount();
//        int dkssud2[] = mergeCalendars.get(1).getCount();
//
//        Log.i("forhyemin", mergeCalendars.get(0).getDate());
//        for (int i = 0; i < 24; i++) {
//            Log.i("forhyemin 1번째 i : ", String.valueOf(i) + "count : " + String.valueOf(dkssud1[i]));
//        }
//        Log.i("forhyemin", mergeCalendars.get(1).getDate());
//        for (int i = 0; i < 24; i++) {
//            Log.i("forhyemin 2번째 i : ", String.valueOf(i) + "count : " + String.valueOf(dkssud2[i]));
//        }

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
