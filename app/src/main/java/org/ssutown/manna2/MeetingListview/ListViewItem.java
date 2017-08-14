package org.ssutown.manna2.MeetingListview;

import android.graphics.drawable.Drawable;

public class ListViewItem{
    private Drawable iconDrawable ;
    private String titleStr ;
    private String meetingID ;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setMeetingID(String desc) {
        meetingID = desc ;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getMeetingID() {
        return this.meetingID ;
    }
}