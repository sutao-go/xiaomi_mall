package com.imooc.entity;

public class AdminUser {
    //用户名
    private String userName;
    //用户密码
    private String passWord;
    public String getUserName() {
        return userName;
    }
    public void setUserId(String userName) {
        this.userName = userName;
    }
    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    @Override
    public String toString() {
        return "{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
