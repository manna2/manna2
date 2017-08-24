package org.ssutown.manna2.MeetingFragment;

/**
 * Created by HyeMin on 2017. 8. 22..
 */

public class Users {
    String animal;
    String nickname;
    String userid;

    public Users(){}

    public Users(String animal, String nickname, String userid){
        this.animal = animal;
        this.nickname = nickname;
        this.userid = userid;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAnimal() {
        return animal;
    }

    public String getUserid() {
        return userid;
    }
}
