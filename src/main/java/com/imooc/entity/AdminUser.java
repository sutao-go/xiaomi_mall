package com.imooc.entity;

public class AdminUser {
    //用户名
    private String userName;
    //登陆账户
    private String loginName;
    //用户密码
    private String passWord;
    public String getUserName() {
        return userName;
    }
    public void setUserId(String userName) {
        this.userName = userName;
    }
    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    @Override
    public String toString() {
        return "adminUser{" +
                "userId='" + userName + '\'' +
                ", loginName='" + loginName + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
