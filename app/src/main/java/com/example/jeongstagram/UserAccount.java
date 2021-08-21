package com.example.jeongstagram;

public class UserAccount {
    public String name;
    public String email;
    public String uid;
    public String introduce;

    public UserAccount(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail(){
        return email;
    }
    public  void setEmail(String email){
        this.email = email;
    }

    public String getUid(){
        return uid;
    }
    public  void setUid(String uid){
        this.uid = uid;
    }
    public String getIntroduce(){
        return introduce;
    }
    public  void setIntroduce(String introduce){
        this.introduce = introduce;
    }


    public UserAccount(String name, String email, String uid, String introduce) {
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.introduce = introduce;
    }

}
