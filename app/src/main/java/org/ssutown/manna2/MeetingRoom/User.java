package org.ssutown.manna2.MeetingRoom;

/**
 * Created by HyeMin on 2017. 5. 28..
 */

public class User {
    private String animal;
    private String nickname;
    private String userID;

    public User(){}

    public User(String id){
        userID = id;
    }

    public User(String animal, String nickname, String id){
        this.animal = animal;
        this.nickname = nickname;
        this.userID = id;
    }

    public String getAnimal() {
        return animal;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUserID() {
        return userID;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
