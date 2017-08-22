package org.ssutown.manna2.Fragment;

import android.util.Log;

/**
 * Created by Jiyeon on 2017-05-22.
 */

public class CalendarItem {
    private String eventname;
    private String start;
    private String end;
    private long uniquekey;
    private String startyear;
    private String startmonth;
    private String startday;
    private String starthour;
    private String startminute;
    private String endyear;
    private String endmonth;
    private String endday;
    private String endhour;
    private String endminute;
//    private int[] endday;


    public CalendarItem(){

    }
    public CalendarItem(String name, String start, String end, long key, String a, String b, String c, String d, String e,
                        String f, String g, String h, String i, String j){
        this.eventname = name;
        this.start = start;
        this.end = end;
        this.uniquekey = key;
        this.startyear = a;
        this.startmonth = b;
        this.startday = c;
        this.starthour = d;
        this.startminute = e;
        this.endyear = f;
        this.endmonth = g;
        this.endday = h;
        this.endhour = i;
        this.endminute = j;


    }

    public String getEnd() {
        return end;
    }

    public String getStart() {
        return start;
    }

    public String getEventname() {
        return eventname;
    }
    public long getUniquekey() {
        return uniquekey;
    }
    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public void setStart(String start) {
        this.start = start;
    }


    public String getStartyear() {
        return startyear;
    }

    public String getStartday() {
        return startday;
    }

    public String getStarthour() {
        return starthour;
    }

    public String getStartminute() {
        return startminute;
    }

    public String getStartmonth() {
        return startmonth;
    }

    public void setStartday(String startday) {
        this.startday = startday;
    }

    public void setStarthour(String starthour) {
        this.starthour = starthour;
    }

    public void setStartminute(String startminute) {
        this.startminute = startminute;
    }

    public void setStartmonth(String startmonth) {
        this.startmonth = startmonth;
    }

    public void setStartyear(String startyear) {
        this.startyear = startyear;
    }

    public void setEndday(String endday) {
        this.endday = endday;
    }

    public void setEndhour(String endhour) {
        this.endhour = endhour;
    }

    public void setEndminute(String endminute) {
        this.endminute = endminute;
    }

    public void setEndmonth(String endmonth) {
        this.endmonth = endmonth;
    }

    public void setEndyear(String endyear) {
        this.endyear = endyear;
    }

    public String getEndday() {
        return endday;
    }

    public String getEndhour() {
        return endhour;
    }

    public String getEndminute() {
        return endminute;
    }

    public String getEndmonth() {
        return endmonth;
    }

    public String getEndyear() {
        return endyear;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setUniquekey(long uniquekey) {
        this.uniquekey = uniquekey;
    }

    public void tostring(){
        Log.i("calendarevent", "eventname;\n" +
                "    start  " + start+"\n"+
                "    end  " + end+"\n"+
                "    uniquekey  " +String.valueOf(uniquekey)+"\n"+
                "    startyear  " + startyear+"\n"+
                "    startmonth  " + startmonth+"\n"+
                "    startday  " +startday+"\n"+
                "    starthour  " +starthour+"\n"+
                "    startminute  " +startmonth+"\n"+
                "    endyear  " +endyear+"\n"+
                "    endmonth  " +endmonth+"\n"+
                "    endday  " +endday+"\n"+
                "    endhour  " +endhour+"\n"+
                "    endminute  "+endminute);
    }
}
