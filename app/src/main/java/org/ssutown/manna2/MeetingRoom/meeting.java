package org.ssutown.manna2.MeetingRoom;

/**
 * Created by HyeMin on 2017. 8. 14..
 */

public class meeting {

    private String meetingID;

    public meeting(){}

    public meeting(String meetingID) {
        this.meetingID = meetingID;
    }

    public String getMeetingID(){
        return meetingID;
    }
}
