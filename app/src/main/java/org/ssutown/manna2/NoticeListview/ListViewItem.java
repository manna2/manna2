package org.ssutown.manna2.NoticeListview;

import android.graphics.drawable.Drawable;

public class ListViewItem{
    //공지사항

    private String Username;
    private Drawable UserIcon ;
    private String contents ;
    private String noticeID ;

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setNoticeID(String noticeID) {
        this.noticeID = noticeID;
    }

    public void setUserIcon(Drawable userIcon) {
        UserIcon = userIcon;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public Drawable getUserIcon() {
        return UserIcon;
    }

    public String getContents() {
        return contents;
    }

    public String getNoticeID() {
        return noticeID;
    }

    public String getUsername() {
        return Username;
    }
}