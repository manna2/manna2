package org.ssutown.manna2.HomeFragment;

/**
 * Created by HyeMin on 2017. 8. 17..
 */

public class profile {

    String nickname;
    String animal;

    public profile(){}

    public profile(String nickname, String animal){
        this.nickname = nickname;
        this.animal = animal;
    }

    public String getAnimal() {
        return animal;
    }

    public String getNickname() {
        return nickname;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
