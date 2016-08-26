package com.zzz.pojo;

import java.io.Serializable;

/**
 * Created by dell_2 on 2016/8/3.
 */
public class User implements Serializable {

    private String userName;
    private String password;

    private Integer id;

    private double payNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getPayNum() {
        return payNum;
    }

    public void setPayNum(double payNum) {
        this.payNum = payNum;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", payNum=" + payNum +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
