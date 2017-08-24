package org.ssutown.manna2.MeetingRoom;

/**
 * Created by HyeMin on 2017. 5. 17..
 */

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class meeting_Info implements Serializable {

    private String meeting_name;
    private String meeting_id;

    private String startYear;
    private String startMonth;
    private String startDay;

    private String endYear;
    private String endMonth;
    private String endDay;
    private String min;

    public meeting_Info(){};

    public meeting_Info(String name, String id, String sY, String sM, String sD, String eY, String eM, String eD, String min){

        Random random = new Random();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
        Date date = new Date();
        String today = df.format(date);
        String ran = String.valueOf(random.nextInt()%9000+10000);

        meeting_id = today+ran;
        meeting_name = name;
        startYear = sY;
        startMonth = sM;
        startDay = sD;
        endYear = eY;
        endMonth = eM;
        endDay = eD;
        this.min = min;

    }

    public String getMeeting_id() {
        return meeting_id;
    }

    public String getMeeting_name() {return meeting_name;}

    public String getStartYear() {
        return startYear;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public String getStartDay(){
        return startDay;
    }

    public String getEndYear() {
        return endYear;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public String getEndDay(){
        return endDay;
    }

    public String getMin() {
        return min;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public void setMeeting_id(String meeting_id) {
        this.meeting_id = meeting_id;
    }

    public void setMeeting_name(String meeting_name) {
        this.meeting_name = meeting_name;
    }

    public void setMin(String min) {
        this.min = min;
    }

    /*    public String getFullStartDay(){
        return String.valueOf(startYear)+String.valueOf(startMonth)+String.valueOf(startDay);
    }

    public String getFullEndDay(){
        return String.valueOf(endYear)+String.valueOf(endMonth)+String.valueOf(endDay);
    }
*/
}
