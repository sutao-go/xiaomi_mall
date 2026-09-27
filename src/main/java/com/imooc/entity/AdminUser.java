package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableName;


/** 对应数据库表 adminuser */
@TableName("adminuser")
public class AdminUser {
    //用户名
    private String userName;
    //用户密码
    private String passWord;
    //用户重新设置之后的密码
    private String serialNumber;
    //身份状态
    private String status;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AdminUser{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
