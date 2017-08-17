package org.ssutown.manna2.NoticeListview;

public class ListViewItem{
    //공지사항

    private String Username;
    private String UserIcon ;
    private String contents ;
    private String noticeID ;

    public ListViewItem(String a, String b, String c, String d){
        Username = a;
        UserIcon = b;
        contents = c;
        noticeID = d;
    }
    public ListViewItem(){}

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setNoticeID(String noticeID) {
        this.noticeID = noticeID;
    }

    public void setUserIcon(String userIcon) {
        UserIcon = userIcon;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUserIcon() {
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